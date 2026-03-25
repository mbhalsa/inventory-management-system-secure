package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import service.SessionManager;

public class TestSalesRecordPage extends Application {

    @Override
    public void start(Stage stage) {
        SessionManager.login(2, "Store Manager User", "manager@example.com", "Store Manager");

        SalesRecordPage salesRecordPage = new SalesRecordPage(stage);
        salesRecordPage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}