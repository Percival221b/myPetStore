package org.example.petstore.persistence.impl;

import org.example.petstore.domain.Cart;
import org.example.petstore.domain.CartItem;
import org.example.petstore.domain.Item;
import org.example.petstore.persistence.CartDao;
import org.example.petstore.persistence.DBUtil;
import org.example.petstore.persistence.ItemDao;

import java.sql.*;
import java.util.Map;

public class CartDaoImpl implements CartDao {
    @Override
    public int createCart(String username) {
        String sql = "INSERT INTO cart (username) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Cart getCartByUsername(String username) {
        Cart cart = new Cart();
        String sql = "SELECT c.cart_id, ci.item_id, ci.quantity, ci.in_stock FROM cart c " +
                "LEFT JOIN cart_item ci ON c.cart_id = ci.cart_id WHERE c.username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cart.setCartId(rs.getInt("cart_id"));
                String itemId = rs.getString("item_id");
                if (itemId != null) {
                    ItemDao itemDao = new ItemDaoImpl();
                    Item item = itemDao.getItem(itemId);
                    cart.addItem(item, true);
                    cart.setQuantityByItemId(itemId, rs.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }


    @Override
    public void addItemToCart(int cartId, Item item, boolean inStock) {
        String sql = "INSERT INTO cart_item (cart_id, item_id, quantity, in_stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setString(2, item.getItemId());
            ps.setInt(3, 1);
            ps.setBoolean(4, inStock);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItemQuantity(String username, String itemId, int quantity) {
        String sql = "UPDATE cart_item ci " +
                "JOIN cart c ON ci.cart_id = c.cart_id " +
                "SET ci.quantity = ? " +
                "WHERE c.username = ? AND ci.item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setString(2, username);
            ps.setString(3, itemId);
            ps.executeUpdate();
            System.out.println("Updated quantity of " + itemId + " to " + quantity + " for " + username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeItemFromCart(String username, String itemId) {
        String sql = "DELETE ci FROM cart_item ci " +
                "JOIN cart c ON ci.cart_id = c.cart_id " +
                "WHERE c.username = ? AND ci.item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, itemId);
            ps.executeUpdate();
            System.out.println("Removed item " + itemId + " from cart of " + username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void clearCart(int cartId) {
        String sql = "DELETE FROM cart_item WHERE cart_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
