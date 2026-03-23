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
        Button manageSuppliersButton = new Button("Manage Suppliers");
        Button managePurchaseOrdersButton = new Button("Manage Purchase Orders");
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
                manageSuppliersButton,
                managePurchaseOrdersButton,
                logoutButton,
                messageLabel
        );

        Scene scene = new Scene(layout, 440, 420);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}
