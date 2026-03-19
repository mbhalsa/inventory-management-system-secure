package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestLoginPage extends Application {

    @Override
    public void start(Stage stage) {

        LoginPage loginPage = new LoginPage(stage);
        loginPage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
