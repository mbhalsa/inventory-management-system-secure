package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import service.SessionManager;

public class TestDashboardPage extends Application {

    @Override
    public void start(Stage stage) {
        SessionManager.login(1, "Abdelrahmen Test", "abdelrahmen@test.com", "Cashier");

        DashboardPage dashboardPage = new DashboardPage(stage);
        dashboardPage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
