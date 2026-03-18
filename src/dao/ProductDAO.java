package dao;

import database.DBConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductDAO {

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (product_name, category, price, quantity_in_stock, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getProductName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantityInStock());
            statement.setString(5, product.getDescription());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET product_name = ?, category = ?, price = ?, quantity_in_stock = ?, description = ? WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getProductName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantityInStock());
            statement.setString(5, product.getDescription());
            statement.setInt(6, product.getProductId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }

    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getString("description")
                );
            }

        } catch (Exception e) {
            System.out.println("Error getting product: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getString("description")
                );
                products.add(product);
            }

        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }

        return products;
    }
}