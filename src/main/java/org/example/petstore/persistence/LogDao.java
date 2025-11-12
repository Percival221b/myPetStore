package org.example.petstore.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDao {

    public static void addLog(String username, String actionType, String itemId, String description) {
        String sql = "INSERT INTO user_log (username, action_type, item_id, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, actionType);
            ps.setString(3, itemId);
            ps.setString(4, description);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
