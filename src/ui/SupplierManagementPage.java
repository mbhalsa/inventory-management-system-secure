package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Supplier;
import service.SessionManager;
import service.SupplierService;

import java.util.ArrayList;

public class SupplierManagementPage {

    private Stage stage;
    private SupplierService supplierService;
    private ListView<String> supplierListView;
    private TextField idField;
    private TextField nameField;
    private TextField contactField;
    private TextField addressField;
    private Label messageLabel;

    public SupplierManagementPage(Stage stage) {
        this.stage = stage;
        this.supplierService = new SupplierService();
    }

    public void show() {
        Label titleLabel = new Label("Supplier Management");

        idField = new TextField();
        idField.setPromptText("Supplier ID");

        nameField = new TextField();
        nameField.setPromptText("Supplier Name");

        contactField = new TextField();
        contactField.setPromptText("Contact Info");

        addressField = new TextField();
        addressField.setPromptText("Address");

        Button loadButton = new Button("Load Supplier");
        Button addButton = new Button("Add Supplier");
        Button updateButton = new Button("Update Supplier");
        Button deleteButton = new Button("Delete Supplier");
        Button refreshButton = new Button("Refresh Suppliers");
        Button backButton = new Button("Back");

        supplierListView = new ListView<>();
        messageLabel = new Label();

        loadButton.setOnAction(e -> loadSupplier());
        addButton.setOnAction(e -> addSupplier());
        updateButton.setOnAction(e -> updateSupplier());
        deleteButton.setOnAction(e -> deleteSupplier());
        refreshButton.setOnAction(e -> refreshSuppliers());

        backButton.setOnAction(e -> {
            DashboardPage dashboardPage = new DashboardPage(stage);
            dashboardPage.show();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                titleLabel,
                idField,
                nameField,
                contactField,
                addressField,
                loadButton,
                addButton,
                updateButton,
                deleteButton,
                refreshButton,
                backButton,
                supplierListView,
                messageLabel
        );

        refreshSuppliers();

        Scene scene = new Scene(layout, 520, 650);
        stage.setTitle("Supplier Management");
        stage.setScene(scene);
        stage.show();
    }

    private void loadSupplier() {
        try {
            String idText = idField.getText().trim();

            if (idText.isEmpty()) {
                messageLabel.setText("Please enter supplier ID.");
                return;
            }

            int supplierId = Integer.parseInt(idText);
            Supplier supplier = supplierService.getSupplierById(supplierId);

            if (supplier == null) {
                messageLabel.setText("Supplier not found.");
                return;
            }

            nameField.setText(supplier.getSupplierName());
            contactField.setText(supplier.getContactInfo());
            addressField.setText(supplier.getAddress());
            messageLabel.setText("Supplier loaded successfully.");

        } catch (NumberFormatException ex) {
            messageLabel.setText("Supplier ID must be a number.");
        }
    }

    private void addSupplier() {
        if (!SessionManager.isStoreManager()) {
            messageLabel.setText("Only Store Manager can add suppliers.");
            return;
        }

        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
            messageLabel.setText("Please fill in all supplier fields.");
            return;
        }

        Supplier supplier = new Supplier(0, name, contact, address);
        boolean result = supplierService.addSupplier(supplier);

        if (result) {
            messageLabel.setText("Supplier added successfully.");
            clearFields();
            refreshSuppliers();
        } else {
            messageLabel.setText("Failed to add supplier.");
        }
    }

    private void updateSupplier() {
        if (!SessionManager.isStoreManager()) {
            messageLabel.setText("Only Store Manager can update suppliers.");
            return;
        }

        try {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();
            String address = addressField.getText().trim();

            if (idText.isEmpty() || name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            int supplierId = Integer.parseInt(idText);

            Supplier supplier = new Supplier(supplierId, name, contact, address);
            boolean result = supplierService.updateSupplier(supplier);

            if (result) {
                messageLabel.setText("Supplier updated successfully.");
                clearFields();
                refreshSuppliers();
            } else {
                messageLabel.setText("Failed to update supplier.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Supplier ID must be a number.");
        }
    }

    private void deleteSupplier() {
        if (!SessionManager.isStoreManager()) {
            messageLabel.setText("Only Store Manager can delete suppliers.");
            return;
        }

        try {
            String idText = idField.getText().trim();

            if (idText.isEmpty()) {
                messageLabel.setText("Please enter supplier ID.");
                return;
            }

            int supplierId = Integer.parseInt(idText);
            boolean result = supplierService.deleteSupplier(supplierId);

            if (result) {
                messageLabel.setText("Supplier deleted successfully.");
                clearFields();
                refreshSuppliers();
            } else {
                messageLabel.setText("Failed to delete supplier.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Supplier ID must be a number.");
        }
    }

    private void refreshSuppliers() {
        supplierListView.getItems().clear();

        ArrayList<Supplier> suppliers = supplierService.getAllSuppliers();

        for (Supplier supplier : suppliers) {
            String row = "ID: " + supplier.getSupplierId()
                    + " | Name: " + supplier.getSupplierName()
                    + " | Contact: " + supplier.getContactInfo()
                    + " | Address: " + supplier.getAddress();

            supplierListView.getItems().add(row);
        }
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        contactField.clear();
        addressField.clear();
    }
}