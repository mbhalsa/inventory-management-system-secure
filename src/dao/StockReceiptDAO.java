package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StockReceiptDAO {

    public boolean createStockReceipt(int orderId, String receivedBy) {

        String sql = "INSERT INTO stock_receipts (order_id, received_by) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            statement.setString(2, receivedBy);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error creating stock receipt: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> getAllReceipts() {

        ArrayList<String> receipts = new ArrayList<>();

        String sql = "SELECT * FROM stock_receipts";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                String row =
                        "Receipt ID: " + resultSet.getInt("receipt_id") +
                                " | Order ID: " + resultSet.getInt("order_id") +
                                " | Received By: " + resultSet.getString("received_by") +
                                " | Date: " + resultSet.getTimestamp("received_date");

                receipts.add(row);
            }

        } catch (Exception e) {
            System.out.println("Error loading stock receipts: " + e.getMessage());
        }

        return receipts;
    }

    public boolean deleteReceipt(int receiptId) {

        String sql = "DELETE FROM stock_receipts WHERE receipt_id=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, receiptId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error deleting receipt: " + e.getMessage());
            return false;
        }
    }
}