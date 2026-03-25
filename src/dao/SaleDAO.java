package dao;

import database.DBConnection;
import model.Sale;
import model.SaleItem;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SaleDAO {

    public boolean recordSale(Sale sale) {
        String saleSql = "INSERT INTO sales (total_amount, user_id) VALUES (?, ?)";
        String itemSql = "INSERT INTO sale_items (sale_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        String stockSql = "UPDATE products SET quantity_in_stock = quantity_in_stock - ? WHERE product_id = ?";
        String inventorySql = "UPDATE inventory SET quantity = quantity - ? WHERE product_id = ?";

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();

            if (connection == null) {
                return false;
            }

            connection.setAutoCommit(false);

            PreparedStatement saleStatement = connection.prepareStatement(saleSql, Statement.RETURN_GENERATED_KEYS);
            saleStatement.setDouble(1, sale.getTotalAmount());

            if (sale.getUser() != null) {
                saleStatement.setInt(2, sale.getUser().getUserId());
            } else {
                saleStatement.setNull(2, java.sql.Types.INTEGER);
            }

            int saleResult = saleStatement.executeUpdate();

            if (saleResult == 0) {
                connection.rollback();
                return false;
            }

            ResultSet generatedKeys = saleStatement.getGeneratedKeys();
            int saleId = 0;

            if (generatedKeys.next()) {
                saleId = generatedKeys.getInt(1);
            } else {
                connection.rollback();
                return false;
            }

            PreparedStatement itemStatement = connection.prepareStatement(itemSql);
            PreparedStatement stockStatement = connection.prepareStatement(stockSql);
            PreparedStatement inventoryStatement = connection.prepareStatement(inventorySql);

            for (SaleItem item : sale.getItems()) {
                itemStatement.setInt(1, saleId);
                itemStatement.setInt(2, item.getProduct().getProductId());
                itemStatement.setInt(3, item.getQuantity());
                itemStatement.setDouble(4, item.getUnitPrice());
                itemStatement.addBatch();

                stockStatement.setInt(1, item.getQuantity());
                stockStatement.setInt(2, item.getProduct().getProductId());
                stockStatement.addBatch();

                inventoryStatement.setInt(1, item.getQuantity());
                inventoryStatement.setInt(2, item.getProduct().getProductId());
                inventoryStatement.addBatch();

            }

            itemStatement.executeBatch();
            stockStatement.executeBatch();
            inventoryStatement.executeBatch();

            connection.commit();
            return true;

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }

            System.out.println("Error recording sale: " + e.getMessage());
            return false;

        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (Exception e) {
                System.out.println("Connection close failed: " + e.getMessage());
            }
        }
    }

    public ArrayList<Sale> getAllSales() {
        ArrayList<Sale> sales = new ArrayList<>();

        String sql = "SELECT * FROM sales ORDER BY sale_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));

                Sale sale = new Sale(
                        resultSet.getInt("sale_id"),
                        resultSet.getTimestamp("sale_date"),
                        resultSet.getDouble("total_amount"),
                        user
                );

                sales.add(sale);
            }

        } catch (Exception e) {
            System.out.println("Error loading sales: " + e.getMessage());
        }

        return sales;
    }
}