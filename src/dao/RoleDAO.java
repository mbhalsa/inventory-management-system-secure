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
        String sql = "SELECT * FROM roles ORDER BY role_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name")
                );
                roles.add(role);
            }

        } catch (Exception e) {
            System.out.println("Error loading roles: " + e.getMessage());
        }

        return roles;
    }

    public Role getRoleByName(String roleName) {
        String sql = "SELECT * FROM roles WHERE role_name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, roleName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Role(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name")
                );
            }

        } catch (Exception e) {
            System.out.println("Error getting role by name: " + e.getMessage());
        }

        return null;
    }
}
