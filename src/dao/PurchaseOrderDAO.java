package dao;

import database.DBConnection;
import model.PurchaseOrder;
import model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PurchaseOrderDAO {

    public boolean createPurchaseOrder(PurchaseOrder order) {

        String sql = "INSERT INTO purchase_orders (supplier_id, order_date, status) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, order.getSupplier().getSupplierId());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setString(3, order.getStatus());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error creating purchase order: " + e.getMessage());
            return false;
        }
    }

    public boolean isDuplicateOrder(PurchaseOrder order) {
        String sql = "SELECT order_id FROM purchase_orders WHERE supplier_id = ? AND DATE(order_date) = ? AND status = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, order.getSupplier().getSupplierId());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setString(3, order.getStatus());

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (Exception e) {
            System.out.println("Error checking duplicate purchase order: " + e.getMessage());
            return false;
        }
    }



    public ArrayList<PurchaseOrder> getAllOrders() {

        ArrayList<PurchaseOrder> orders = new ArrayList<>();

        String sql = "SELECT * FROM purchase_orders";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Supplier supplier = new Supplier();
                supplier.setSupplierId(resultSet.getInt("supplier_id"));

                PurchaseOrder order = new PurchaseOrder(
                        resultSet.getInt("order_id"),
                        resultSet.getDate("order_date"),
                        resultSet.getString("status"),
                        supplier
                );

                orders.add(order);
            }

        } catch (Exception e) {
            System.out.println("Error loading purchase orders: " + e.getMessage());
        }

        return orders;
    }

    public boolean updateOrderStatus(int orderId, String status) {

        String sql = "UPDATE purchase_orders SET status=? WHERE order_id=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, orderId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating order status: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteOrder(int orderId) {

        String sql = "DELETE FROM purchase_orders WHERE order_id=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
            return false;
        }
    }
}