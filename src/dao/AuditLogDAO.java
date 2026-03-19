package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AuditLogDAO {

    public boolean addLog(String action, String performedBy, String details) {
        String sql = "INSERT INTO audit_logs (action, performed_by, details) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, action);
            statement.setString(2, performedBy);
            statement.setString(3, details);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding audit log: " + e.getMessage());
            return false;
        }
    }
}