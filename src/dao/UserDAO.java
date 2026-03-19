package dao;

import database.DBConnection;
import model.Role;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean registerUser(User user, int roleId) {
        String sql = "INSERT INTO users (full_name, email, password, is_verified, role_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isVerified());
            statement.setInt(5, roleId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT u.user_id, u.full_name, u.email, u.password, u.is_verified, r.role_id, r.role_name " +
                "FROM users u LEFT JOIN roles r ON u.role_id = r.role_id WHERE u.email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Role role = null;

                int roleId = resultSet.getInt("role_id");
                if (!resultSet.wasNull()) {
                    role = new Role(roleId, resultSet.getString("role_name"));
                }

                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("is_verified"),
                        role
                );
            }

        } catch (Exception e) {
            System.out.println("Error getting user by email: " + e.getMessage());
        }

        return null;
    }

    public boolean updatePassword(String email, String hashedPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, hashedPassword);
            statement.setString(2, email);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating password: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyEmail(String email) {
        String sql = "UPDATE users SET is_verified = TRUE WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error verifying email: " + e.getMessage());
            return false;
        }
    }

    public boolean assignRole(String email, int roleId) {
        String sql = "UPDATE users SET role_id = ? WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roleId);
            statement.setString(2, email);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error assigning role: " + e.getMessage());
            return false;
        }
    }

    public boolean emailExists(String email) {
        String sql = "SELECT user_id FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {
            System.out.println("Error checking email: " + e.getMessage());
            return false;
        }
    }
}
