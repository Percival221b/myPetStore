package org.example.petstore.persistence.impl;

import org.example.petstore.domain.Cart;
import org.example.petstore.domain.Item;
import org.example.petstore.persistence.CartDao;
import org.example.petstore.persistence.DBUtil;

import java.sql.*;

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

    @Override
    public Cart getCartByUsername(String username) {
        Cart cart = new Cart();
        String sql = "SELECT c.id, ci.item_id, ci.quantity, ci.in_stock FROM cart c " +
                "LEFT JOIN cart_item ci ON c.id = ci.cart_id WHERE c.username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cart.setCartId(rs.getInt("id"));
                String itemId = rs.getString("item_id");
                if (itemId != null) {
                    Item item = new Item();
                    item.setItemId(itemId);
                    cart.addItem(item, rs.getBoolean("in_stock"));
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
    public void updateItemQuantity(int cartId, String itemId, int quantity) {
        String sql = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.setString(3, itemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeItemFromCart(int cartId, String itemId) {
        String sql = "DELETE FROM cart_item WHERE cart_id = ? AND item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setString(2, itemId);
            ps.executeUpdate();
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
