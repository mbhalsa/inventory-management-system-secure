package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.SessionManager;
import service.StockReceiptService;

import java.util.ArrayList;

public class StockReceiptPage {

    private Stage stage;
    private StockReceiptService stockReceiptService;
    private ListView<String> receiptListView;
    private TextField receiptIdField;
    private TextField orderIdField;
    private TextField receivedByField;
    private Label messageLabel;

    public StockReceiptPage(Stage stage) {
        this.stage = stage;
        this.stockReceiptService = new StockReceiptService();
    }

    public void show() {
        Label titleLabel = new Label("Stock Receipt Management");

        receiptIdField = new TextField();
        receiptIdField.setPromptText("Receipt ID");

        orderIdField = new TextField();
        orderIdField.setPromptText("Order ID");

        receivedByField = new TextField();
        receivedByField.setPromptText("Received By");

        Button createButton = new Button("Create Receipt");
        Button deleteButton = new Button("Delete Receipt");
        Button refreshButton = new Button("Refresh Receipts");
        Button backButton = new Button("Back");

        receiptListView = new ListView<>();
        messageLabel = new Label();

        createButton.setOnAction(e -> createReceipt());
        deleteButton.setOnAction(e -> deleteReceipt());
        refreshButton.setOnAction(e -> refreshReceipts());

        backButton.setOnAction(e -> {
            DashboardPage dashboardPage = new DashboardPage(stage);
            dashboardPage.show();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                titleLabel,
                receiptIdField,
                orderIdField,
                receivedByField,
                createButton,
                deleteButton,
                refreshButton,
                backButton,
                receiptListView,
                messageLabel
        );

        refreshReceipts();

        Scene scene = new Scene(layout, 560, 560);
        stage.setTitle("Stock Receipts");
        stage.setScene(scene);
        stage.show();
    }

    private void createReceipt() {
        if (!SessionManager.isStoreManager()) {
            messageLabel.setText("Only Store Manager can create stock receipts.");
            return;
        }

        try {
            String orderIdText = orderIdField.getText().trim();
            String receivedBy = receivedByField.getText().trim();

            if (orderIdText.isEmpty() || receivedBy.isEmpty()) {
                messageLabel.setText("Please enter order ID and received by.");
                return;
            }

            int orderId = Integer.parseInt(orderIdText);
            boolean result = stockReceiptService.createStockReceipt(orderId, receivedBy);

            if (result) {
                messageLabel.setText("Stock receipt created successfully.");
                clearFields();
                refreshReceipts();
            } else {
                messageLabel.setText("Failed to create stock receipt.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Order ID must be a number.");
        }
    }

    private void deleteReceipt() {
        if (!SessionManager.isStoreManager()) {
            messageLabel.setText("Only Store Manager can delete stock receipts.");
            return;
        }

        try {
            String receiptIdText = receiptIdField.getText().trim();

            if (receiptIdText.isEmpty()) {
                messageLabel.setText("Please enter receipt ID.");
                return;
            }

            int receiptId = Integer.parseInt(receiptIdText);
            boolean result = stockReceiptService.deleteReceipt(receiptId);

            if (result) {
                messageLabel.setText("Stock receipt deleted successfully.");
                clearFields();
                refreshReceipts();
            } else {
                messageLabel.setText("Failed to delete stock receipt.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Receipt ID must be a number.");
        }
    }

    private void refreshReceipts() {
        receiptListView.getItems().clear();

        ArrayList<String> receipts = stockReceiptService.getAllReceipts();

        for (String receipt : receipts) {
            receiptListView.getItems().add(receipt);
        }
    }

    private void clearFields() {
        receiptIdField.clear();
        orderIdField.clear();
        receivedByField.clear();
    }
}