package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import service.SessionManager;

public class TestAssignRolePage extends Application {

    @Override
    public void start(Stage stage) {
        SessionManager.login(1, "Admin User", "admin@example.com", "Admin");

        AssignRolePage assignRolePage = new AssignRolePage(stage);
        assignRolePage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
