package ui;
import dao.ProductDAO;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import model.Sale;
import model.SaleItem;

import model.User;
import service.SaleService;
import service.SessionManager;
import service.ValidationService;

import java.util.ArrayList;
import java.util.Date;

public class SalesRecordPage {

    private Stage stage;
    private SaleService saleService;
    private ProductDAO productDAO;
    private ListView<String> salesListView;
    private TextField productIdField;
    private TextField quantityField;

    private Label messageLabel;

    public SalesRecordPage(Stage stage) {
        this.stage = stage;
        this.saleService = new SaleService();
        this.productDAO = new ProductDAO();

    }

    public void show() {

        Label titleLabel = new Label("Sales Recording");

        productIdField = new TextField();
        productIdField.setPromptText("Product ID");

        quantityField = new TextField();
        quantityField.setPromptText("Quantity");
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
                productIdField,
                quantityField,
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
            String productIdText = productIdField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (!ValidationService.isPositiveInteger(productIdText)
                    || !ValidationService.isPositiveInteger(quantityText)) {
                messageLabel.setText("Please enter a valid product ID and quantity.");
                return;
            }

            int productId = Integer.parseInt(productIdText);
            int quantity = Integer.parseInt(quantityText);

            Product product = productDAO.getProductById(productId);

            if (product == null) {
                messageLabel.setText("Product not found.");
                return;
            }

            SaleItem item = new SaleItem(0, product, quantity, product.getPrice());

            User user = new User();
            user.setUserId(SessionManager.getCurrentUserId());

            Sale sale = new Sale(0, new Date(), 0, user);
            sale.addItem(item);

            boolean result = saleService.recordSale(sale);

            if (result) {
                messageLabel.setText("Sale recorded successfully.");
                productIdField.clear();
                quantityField.clear();
                refreshSales();
            } else {
                messageLabel.setText("Failed to record sale. Check stock or input.");
            }

        } catch (Exception ex) {
            messageLabel.setText("Error while recording sale.");
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
