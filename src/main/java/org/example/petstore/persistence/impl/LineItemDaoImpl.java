package org.example.petstore.persistence.impl;

import org.example.petstore.domain.LineItem;
import org.example.petstore.domain.Item;
import org.example.petstore.persistence.DBUtil;
import org.example.petstore.persistence.ItemDao;
import org.example.petstore.persistence.LineItemDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoImpl implements LineItemDao {
    ItemDao itemDao = new ItemDaoImpl();

    @Override
    public List<LineItem> getLineItemsByOrderId(int orderId) {
        List<LineItem> lineItems = new ArrayList<>();
        String sql = """
        SELECT LINENUM, ITEMID, QUANTITY, UNITPRICE
        FROM LINEITEM
        WHERE ORDERID = ?
    """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置 ORDERID 参数
            ps.setInt(1, orderId);

            // 执行查询
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // 每次迭代，映射一个 LineItem 对象
                    LineItem lineItem = new LineItem();
                    lineItem.setOrderId(orderId);
                    lineItem.setLineNumber(rs.getInt("LINENUM"));
                    lineItem.setItemId(rs.getString("ITEMID"));
                    lineItem.setQuantity(rs.getInt("QUANTITY"));
                    lineItem.setUnitPrice(rs.getBigDecimal("UNITPRICE"));

                    Item item = itemDao.getItem(lineItem.getItemId());
                    lineItem.setItem(item);

                    // 计算 total
                    lineItem.calculateTotal();

                    // 将该 LineItem 对象添加到 List 中
                    lineItems.add(lineItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 返回结果
        return lineItems;
    }


    @Override
    public void insertLineItem(LineItem lineItem) {
        // SQL 插入语句
        String sql = """
        INSERT INTO LINEITEM (ORDERID, LINENUM, ITEMID, QUANTITY, UNITPRICE)
        VALUES (?, ?, ?, ?, ?)
    """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置 PreparedStatement 参数
            ps.setInt(1, lineItem.getOrderId());         // 设置 ORDERID
            ps.setInt(2, lineItem.getLineNumber());      // 设置 LINENUM
            ps.setString(3, lineItem.getItemId());       // 设置 ITEMID
            ps.setInt(4, lineItem.getQuantity());        // 设置 QUANTITY
            ps.setBigDecimal(5, lineItem.getUnitPrice()); // 设置 UNITPRICE

            // 执行插入操作
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
