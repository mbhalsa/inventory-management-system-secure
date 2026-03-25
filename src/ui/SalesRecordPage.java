package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Sale;
import model.User;
import service.SaleService;
import service.SessionManager;

import java.util.ArrayList;
import java.util.Date;

public class SalesRecordPage {

    private Stage stage;
    private SaleService saleService;
    private ListView<String> salesListView;
    private TextField totalAmountField;
    private Label messageLabel;

    public SalesRecordPage(Stage stage) {
        this.stage = stage;
        this.saleService = new SaleService();
    }

    public void show() {

        Label titleLabel = new Label("Sales Recording");

        totalAmountField = new TextField();
        totalAmountField.setPromptText("Total Amount");

        Button recordSaleButton = new Button("Record Sale");
        Button refreshButton = new Button("Refresh Sales");
        Button backButton = new Button("Back");

        salesListView = new ListView<>();
        messageLabel = new Label();

        recordSaleButton.setOnAction(e -> recordSale());
        refreshButton.setOnAction(e -> refreshSales());

        backButton.setOnAction(e -> {
            DashboardPage dashboardPage = new DashboardPage(stage);
            dashboardPage.show();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        layout.getChildren().addAll(
                titleLabel,
                totalAmountField,
                recordSaleButton,
                refreshButton,
                backButton,
                salesListView,
                messageLabel
        );

        refreshSales();

        Scene scene = new Scene(layout, 520, 500);
        stage.setTitle("Sales Records");
        stage.setScene(scene);
        stage.show();
    }

    private void recordSale() {

        try {
            String amountText = totalAmountField.getText().trim();

            if (amountText.isEmpty()) {
                messageLabel.setText("Please enter total amount.");
                return;
            }

            double totalAmount = Double.parseDouble(amountText);

            User user = new User();
            user.setUserId(SessionManager.getCurrentUserId());

            Sale sale = new Sale(0, new Date(), totalAmount, user);

            boolean result = saleService.recordSale(sale);

            if (result) {
                messageLabel.setText("Sale recorded successfully.");
                totalAmountField.clear();
                refreshSales();
            } else {
                messageLabel.setText("Failed to record sale.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Total amount must be a number.");
        }
    }

    private void refreshSales() {

        salesListView.getItems().clear();

        ArrayList<Sale> sales = saleService.getAllSales();

        for (Sale sale : sales) {

            String row =
                    "Sale ID: " + sale.getSaleId() +
                            " | Date: " + sale.getSaleDate() +
                            " | Total: " + sale.getTotalAmount() +
                            " | User ID: " + sale.getUser().getUserId();

            salesListView.getItems().add(row);
        }
    }
}