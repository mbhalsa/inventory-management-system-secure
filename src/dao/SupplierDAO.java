package dao;

import database.DBConnection;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SupplierDAO {

    public boolean addSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (supplier_name, contact_info, address) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getContactInfo());
            statement.setString(3, supplier.getAddress());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error adding supplier: " + e.getMessage());
            return false;
        }
    }

    public boolean updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET supplier_name = ?, contact_info = ?, address = ? WHERE supplier_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getContactInfo());
            statement.setString(3, supplier.getAddress());
            statement.setInt(4, supplier.getSupplierId());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error updating supplier: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSupplier(int supplierId) {
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, supplierId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error deleting supplier: " + e.getMessage());
            return false;
        }
    }

    public Supplier getSupplierById(int supplierId) {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, supplierId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Supplier(
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("supplier_name"),
                        resultSet.getString("contact_info"),
                        resultSet.getString("address")
                );
            }
        } catch (Exception e) {
            System.out.println("Error getting supplier: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY supplier_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("supplier_name"),
                        resultSet.getString("contact_info"),
                        resultSet.getString("address")
                );
                suppliers.add(supplier);
            }
        } catch (Exception e) {
            System.out.println("Error loading suppliers: " + e.getMessage());
        }

        return suppliers;
    }
}