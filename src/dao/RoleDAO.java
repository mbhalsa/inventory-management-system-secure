package dao;

import database.DBConnection;
import model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RoleDAO {

    public ArrayList<Role> getAllRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                roles.add(new Role(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error loading roles: " + e.getMessage());
        }

        return roles;
    }

    public Role getRoleById(int roleId) {
        String sql = "SELECT * FROM roles WHERE role_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roleId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Role(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name")
                );
            }

        } catch (Exception e) {
            System.out.println("Error getting role: " + e.getMessage());
        }

        return null;
    }
}
