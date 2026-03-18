package dao;

import database.DBConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InventoryDAO {

    public ArrayList<Product> getInventoryProducts() {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT p.product_id, p.product_name, p.category, p.price, i.quantity, p.description " +
                "FROM products p JOIN inventory i ON p.product_id = i.product_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("description")
                );
                products.add(product);
            }

        } catch (Exception e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }

        return products;
    }

    public boolean insertInventoryRecord(int productId, int quantity) {
        String sql = "INSERT INTO inventory (product_id, quantity) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);
            statement.setInt(2, quantity);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error inserting inventory record: " + e.getMessage());
            return false;
        }
    }

    public boolean updateInventoryQuantity(int productId, int quantity) {
        String sql = "UPDATE inventory SET quantity = ? WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quantity);
            statement.setInt(2, productId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating inventory quantity: " + e.getMessage());
            return false;
        }
    }

    public int getProductQuantity(int productId) {
        String sql = "SELECT quantity FROM inventory WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("quantity");
            }

        } catch (Exception e) {
            System.out.println("Error retrieving product quantity: " + e.getMessage());
        }

        return 0;
    }
}