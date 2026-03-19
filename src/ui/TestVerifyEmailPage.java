package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestVerifyEmailPage extends Application {

    @Override
    public void start(Stage stage) {

        VerifyEmailPage verifyEmailPage = new VerifyEmailPage(stage);
        verifyEmailPage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
