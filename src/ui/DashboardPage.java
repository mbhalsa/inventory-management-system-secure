package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AuthService;
import service.SessionManager;

public class DashboardPage {

    private Stage stage;
    private AuthService authService;

    public DashboardPage(Stage stage) {
        this.stage = stage;
        this.authService = new AuthService();
    }

    public void show() {
        Label welcomeLabel = new Label("Welcome, " + SessionManager.getCurrentUserName());
        Label roleLabel = new Label("Role: " + SessionManager.getCurrentUserRole());
        Label messageLabel = new Label();

        Button verifyEmailButton = new Button("Verify Email");
        Button resetPasswordButton = new Button("Reset Password");
        Button assignRoleButton = new Button("Assign Role");

        Button addProductButton = new Button("Add Product");
        Button updateProductButton = new Button("Update Product");
        Button deleteProductButton = new Button("Delete Product");
        Button viewInventoryButton = new Button("View Inventory");

        Button manageSuppliersButton = new Button("Manage Suppliers");
        Button managePurchaseOrdersButton = new Button("Manage Purchase Orders");
        Button manageStockReceiptsButton = new Button("Stock Receiving");
        Button salesRecordButton = new Button("Sales Records");
        Button salesReportButton = new Button("Sales Reports");
        Button logoutButton = new Button("Logout");

        verifyEmailButton.setOnAction(e -> {
            VerifyEmailPage verifyEmailPage = new VerifyEmailPage(stage);
            verifyEmailPage.show();
        });

        resetPasswordButton.setOnAction(e -> {
            ResetPasswordPage resetPasswordPage = new ResetPasswordPage(stage);
            resetPasswordPage.show();
        });

        assignRoleButton.setOnAction(e -> {
            if (SessionManager.isAdmin()) {
                AssignRolePage assignRolePage = new AssignRolePage(stage);
                assignRolePage.show();
            } else {
                messageLabel.setText("Only admin can access role assignment.");
            }
        });

        addProductButton.setOnAction(e -> {
            if (SessionManager.isStoreManager()) {
                AddProductForm addProductForm = new AddProductForm();
                addProductForm.show();
            } else {
                messageLabel.setText("Only Store Manager can add products.");
            }
        });

        updateProductButton.setOnAction(e -> {
            if (SessionManager.isStoreManager()) {
                UpdateProductForm updateProductForm = new UpdateProductForm();
                updateProductForm.show();
            } else {
                messageLabel.setText("Only Store Manager can update products.");
            }
        });

        deleteProductButton.setOnAction(e -> {
            if (SessionManager.isStoreManager()) {
                DeleteProductForm deleteProductForm = new DeleteProductForm();
                deleteProductForm.show();
            } else {
                messageLabel.setText("Only Store Manager can delete products.");
            }
        });

        viewInventoryButton.setOnAction(e -> {
            InventoryView inventoryView = new InventoryView();
            inventoryView.show();
        });


        manageSuppliersButton.setOnAction(e -> {
            if (SessionManager.isStoreManager()) {
                SupplierManagementPage supplierManagementPage = new SupplierManagementPage(stage);
                supplierManagementPage.show();
            } else {
                messageLabel.setText("Only Store Manager can access supplier management.");
            }
        });

        managePurchaseOrdersButton.setOnAction(e -> {
            if (SessionManager.isStoreManager()) {
                PurchaseOrderPage purchaseOrderPage = new PurchaseOrderPage(stage);
                purchaseOrderPage.show();
            } else {
                messageLabel.setText("Only Store Manager can access purchase orders.");
            }
        });

        manageStockReceiptsButton.setOnAction(e -> {
            if (SessionManager.isStoreManager()) {
                StockReceiptPage stockReceiptPage = new StockReceiptPage(stage);
                stockReceiptPage.show();
            } else {
                messageLabel.setText("Only Store Manager can access stock receiving.");
            }
        });

        salesRecordButton.setOnAction(e -> {
            SalesRecordPage salesRecordPage = new SalesRecordPage(stage);
            salesRecordPage.show();
        });

        salesReportButton.setOnAction(e -> {
            SalesReportPage salesReportPage = new SalesReportPage(stage);
            salesReportPage.show();
        });


        logoutButton.setOnAction(e -> {
            authService.logout();
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        });

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                welcomeLabel,
                roleLabel,
                verifyEmailButton,
                resetPasswordButton,
                assignRoleButton,
                addProductButton,
                updateProductButton,
                deleteProductButton,
                viewInventoryButton,
                manageSuppliersButton,
                managePurchaseOrdersButton,
                manageStockReceiptsButton,
                salesRecordButton,
                salesReportButton,
                logoutButton,
                messageLabel
        );

        Scene scene = new Scene(layout, 440, 420);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}
