package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestRegisterPage extends Application {

    @Override
    public void start(Stage stage) {
        RegisterPage registerPage = new RegisterPage(stage);
        registerPage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
