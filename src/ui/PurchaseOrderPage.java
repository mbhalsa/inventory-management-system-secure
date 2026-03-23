package ui;

import dao.SupplierDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PurchaseOrder;
import model.Supplier;
import service.PurchaseOrderService;
import service.SessionManager;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseOrderPage {

    private Stage stage;
    private PurchaseOrderService purchaseOrderService;
    private SupplierDAO supplierDAO;
    private ListView<String> orderListView;
    private TextField orderIdField;
    private TextField supplierIdField;
    private TextField statusField;
    private Label messageLabel;

    public PurchaseOrderPage(Stage stage) {
        this.stage = stage;
        this.purchaseOrderService = new PurchaseOrderService();
        this.supplierDAO = new SupplierDAO();
    }

    public void show() {
        Label titleLabel = new Label("Purchase Order Management");

        orderIdField = new TextField();
        orderIdField.setPromptText("Order ID");

        supplierIdField = new TextField();
        supplierIdField.setPromptText("Supplier ID");

        statusField = new TextField();
        statusField.setPromptText("Status");

        Button createButton = new Button("Create Order");
        Button updateStatusButton = new Button("Update Status");
        Button deleteButton = new Button("Delete Order");
        Button refreshButton = new Button("Refresh Orders");
        Button backButton = new Button("Back");

        orderListView = new ListView<>();
        messageLabel = new Label();

        createButton.setOnAction(e -> createOrder());
        updateStatusButton.setOnAction(e -> updateOrderStatus());
        deleteButton.setOnAction(e -> deleteOrder());
        refreshButton.setOnAction(e -> refreshOrders());

        backButton.setOnAction(e -> {
            DashboardPage dashboardPage = new DashboardPage(stage);
            dashboardPage.show();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                titleLabel,
                orderIdField,
                supplierIdField,
                statusField,
                createButton,
                updateStatusButton,
                deleteButton,
                refreshButton,
                backButton,
                orderListView,
                messageLabel
        );

        refreshOrders();

        Scene scene = new Scene(layout, 560, 600);
        stage.setTitle("Purchase Orders");
        stage.setScene(scene);
        stage.show();
    }

    private void createOrder() {
        if (!SessionManager.isStoreManager()) {
            messageLabel.setText("Only Store Manager can create purchase orders.");
            return;
        }

        try {
            String supplierIdText = supplierIdField.getText().trim();
            String status = statusField.getText().trim();

            if (supplierIdText.isEmpty() || status.isEmpty()) {
                messageLabel.setText("Please enter supplier ID and status.");
                return;
            }

            int supplierId = Integer.parseInt(supplierIdText);
            Supplier supplier = supplierDAO.getSupplierById(supplierId);

            if (supplier == null) {
                messageLabel.setText("Supplier not found.");
                return;
            }

            PurchaseOrder order = new PurchaseOrder(0, new Date(), status, supplier);
            boolean result = purchaseOrderService.createPurchaseOrder(order);

            if (result) {
                messageLabel.setText("Purchase order created successfully.");
                clearFields();
                refreshOrders();
            } else {
                messageLabel.setText("Failed to create purchase order.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Supplier ID must be a number.");
        }
    }

    private void updateOrderStatus() {
        if (!SessionManager.isStoreManager()) {
            messageLabel.setText("Only Store Manager can update purchase orders.");
            return;
        }

        try {
            String orderIdText = orderIdField.getText().trim();
            String status = statusField.getText().trim();

            if (orderIdText.isEmpty() || status.isEmpty()) {
                messageLabel.setText("Please enter order ID and new status.");
                return;
            }

            int orderId = Integer.parseInt(orderIdText);
            boolean result = purchaseOrderService.updateOrderStatus(orderId, status);

            if (result) {
                messageLabel.setText("Order status updated successfully.");
                clearFields();
                refreshOrders();
            } else {
                messageLabel.setText("Failed to update order status.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Order ID must be a number.");
        }
    }

    private void deleteOrder() {
        if (!SessionManager.isStoreManager()) {
            messageLabel.setText("Only Store Manager can delete purchase orders.");
            return;
        }

        try {
            String orderIdText = orderIdField.getText().trim();

            if (orderIdText.isEmpty()) {
                messageLabel.setText("Please enter order ID.");
                return;
            }

            int orderId = Integer.parseInt(orderIdText);
            boolean result = purchaseOrderService.deleteOrder(orderId);

            if (result) {
                messageLabel.setText("Purchase order deleted successfully.");
                clearFields();
                refreshOrders();
            } else {
                messageLabel.setText("Failed to delete purchase order.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Order ID must be a number.");
        }
    }

    private void refreshOrders() {
        orderListView.getItems().clear();

        ArrayList<PurchaseOrder> orders = purchaseOrderService.getAllOrders();

        for (PurchaseOrder order : orders) {
            String row = "Order ID: " + order.getOrderId()
                    + " | Supplier ID: " + order.getSupplier().getSupplierId()
                    + " | Date: " + order.getOrderDate()
                    + " | Status: " + order.getStatus();

            orderListView.getItems().add(row);
        }
    }

    private void clearFields() {
        orderIdField.clear();
        supplierIdField.clear();
        statusField.clear();
    }
}