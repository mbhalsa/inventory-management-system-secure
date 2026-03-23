package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import service.SessionManager;

public class TestPurchaseOrderPage extends Application {

    @Override
    public void start(Stage stage) {
        SessionManager.login(2, "Store Manager User", "manager@example.com", "Store Manager");

        PurchaseOrderPage purchaseOrderPage = new PurchaseOrderPage(stage);
        purchaseOrderPage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}