package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestResetPasswordPage extends Application {

    @Override
    public void start(Stage stage) {
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(stage);
        resetPasswordPage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
