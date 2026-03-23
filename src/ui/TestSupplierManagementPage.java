package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import service.SessionManager;

public class TestSupplierManagementPage extends Application {

    @Override
    public void start(Stage stage) {
        SessionManager.login(2, "Store Manager User", "manager@example.com", "Store Manager");

        SupplierManagementPage supplierManagementPage = new SupplierManagementPage(stage);
        supplierManagementPage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}