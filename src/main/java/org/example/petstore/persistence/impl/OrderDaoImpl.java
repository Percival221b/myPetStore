package org.example.petstore.persistence.impl;

import org.example.petstore.domain.Order;
import org.example.petstore.persistence.DBUtil;
import org.example.petstore.persistence.OrderDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        String sql = """
                SELECT
                    BILLADDR1 AS billAddress1,
                    BILLADDR2 AS billAddress2,
                    BILLCITY,
                    BILLCOUNTRY,
                    BILLSTATE,
                    BILLTOFIRSTNAME,
                    BILLTOLASTNAME,
                    BILLZIP,
                    SHIPADDR1 AS shipAddress1,
                    SHIPADDR2 AS shipAddress2,
                    SHIPCITY,
                    SHIPCOUNTRY,
                    SHIPSTATE,
                    SHIPTOFIRSTNAME,
                    SHIPTOLASTNAME,
                    SHIPZIP,
                    CARDTYPE,
                    COURIER,
                    CREDITCARD,
                    EXPRDATE AS expiryDate,
                    LOCALE,
                    ORDERDATE,
                    ORDERS.ORDERID,
                    TOTALPRICE,
                    USERID AS username,
                    STATUS
                FROM ORDERS, ORDERSTATUS
                WHERE ORDERS.USERID = ? AND ORDERS.ORDERID = ORDERSTATUS.ORDERID
                ORDER BY ORDERDATE
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getOrder(int orderId) {
        String sql = """
                SELECT
                    BILLADDR1 AS billAddress1,
                    BILLADDR2 AS billAddress2,
                    BILLCITY,
                    BILLCOUNTRY,
                    BILLSTATE,
                    BILLTOFIRSTNAME,
                    BILLTOLASTNAME,
                    BILLZIP,
                    SHIPADDR1 AS shipAddress1,
                    SHIPADDR2 AS shipAddress2,
                    SHIPCITY,
                    SHIPCOUNTRY,
                    SHIPSTATE,
                    SHIPTOFIRSTNAME,
                    SHIPTOLASTNAME,
                    SHIPZIP,
                    CARDTYPE,
                    COURIER,
                    CREDITCARD,
                    EXPRDATE AS expiryDate,
                    LOCALE,
                    ORDERDATE,
                    ORDERS.ORDERID,
                    TOTALPRICE,
                    USERID AS username,
                    STATUS
                FROM ORDERS, ORDERSTATUS
                WHERE ORDERS.ORDERID = ? AND ORDERS.ORDERID = ORDERSTATUS.ORDERID
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void insertOrder(Order order) {
        String sql = """
            INSERT INTO ORDERS (USERID, ORDERDATE, SHIPADDR1, SHIPADDR2, SHIPCITY, SHIPSTATE,
                SHIPZIP, SHIPCOUNTRY, BILLADDR1, BILLADDR2, BILLCITY, BILLSTATE, BILLZIP, BILLCOUNTRY,
                COURIER, TOTALPRICE, BILLTOFIRSTNAME, BILLTOLASTNAME, SHIPTOFIRSTNAME, SHIPTOLASTNAME,
                CREDITCARD, EXPRDATE, CARDTYPE, LOCALE)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, order.getUsername());
            ps.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            ps.setString(3, order.getShipAddress1());
            ps.setString(4, order.getShipAddress2());
            ps.setString(5, order.getShipCity());
            ps.setString(6, order.getShipState());
            ps.setString(7, order.getShipZip());
            ps.setString(8, order.getShipCountry());
            ps.setString(9, order.getBillAddress1());
            ps.setString(10, order.getBillAddress2());
            ps.setString(11, order.getBillCity());
            ps.setString(12, order.getBillState());
            ps.setString(13, order.getBillZip());
            ps.setString(14, order.getBillCountry());
            ps.setString(15, order.getCourier());
            ps.setBigDecimal(16, order.getTotalPrice());
            ps.setString(17, order.getBillToFirstName());
            ps.setString(18, order.getBillToLastName());
            ps.setString(19, order.getShipToFirstName());
            ps.setString(20, order.getShipToLastName());
            ps.setString(21, order.getCreditCard());
            ps.setString(22, order.getExpiryDate());
            ps.setString(23, order.getCardType());
            ps.setString(24, order.getLocale());

            // 执行插入并获取生成的订单ID
            ps.executeUpdate();

            // 获取自动生成的订单ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    order.setOrderId(orderId);  // 设置到订单对象中
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insertOrderStatus(Order order) {
        String sql = """
            INSERT INTO ORDERSTATUS (ORDERID, LINENUM, TIMESTAMP, STATUS)
            VALUES (?, ?, ?, ?)
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getOrderId());  // 使用刚刚插入的订单ID
            ps.setInt(2, 1);  // 假设第一次状态的 `LINENUM` 是 1
            ps.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
            ps.setString(4, order.getStatus());  // 默认状态可以设置为 "CREATED"
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Order mapRow(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("ORDERID"));
        order.setUsername(rs.getString("username"));
        order.setOrderDate(rs.getTimestamp("ORDERDATE"));
        order.setBillAddress1(rs.getString("billAddress1"));
        order.setBillAddress2(rs.getString("billAddress2"));
        order.setBillCity(rs.getString("BILLCITY"));
        order.setBillState(rs.getString("BILLSTATE"));
        order.setBillZip(rs.getString("BILLZIP"));
        order.setBillCountry(rs.getString("BILLCOUNTRY"));
        order.setShipAddress1(rs.getString("shipAddress1"));
        order.setShipAddress2(rs.getString("shipAddress2"));
        order.setShipCity(rs.getString("SHIPCITY"));
        order.setShipState(rs.getString("SHIPSTATE"));
        order.setShipZip(rs.getString("SHIPZIP"));
        order.setShipCountry(rs.getString("SHIPCOUNTRY"));
        order.setCardType(rs.getString("CARDTYPE"));
        order.setCourier(rs.getString("COURIER"));
        order.setCreditCard(rs.getString("CREDITCARD"));
        order.setExpiryDate(rs.getString("expiryDate"));
        order.setLocale(rs.getString("LOCALE"));
        order.setTotalPrice(rs.getBigDecimal("TOTALPRICE"));
        order.setStatus(rs.getString("STATUS"));
        order.setBillToFirstName(rs.getString("BILLTOFIRSTNAME"));
        order.setBillToLastName(rs.getString("BILLTOLASTNAME"));
        order.setShipToFirstName(rs.getString("SHIPTOFIRSTNAME"));
        order.setShipToLastName(rs.getString("SHIPTOLASTNAME"));
        return order;
    }

}
