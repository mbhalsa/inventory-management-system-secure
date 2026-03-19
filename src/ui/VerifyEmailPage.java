package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AuthService;

public class VerifyEmailPage {

    private Stage stage;
    private AuthService authService;

    public VerifyEmailPage(Stage stage){
        this.stage = stage;
        this.authService = new AuthService();
    }

    public void show(){

        Label title = new Label("Verify Email");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label message = new Label();

        Button verifyButton = new Button("Verify Email");
        Button backButton = new Button("Back to Login");

        verifyButton.setOnAction(e -> {

            String email = emailField.getText().trim();

            if(email.isEmpty()){
                message.setText("Enter your email.");
                return;
            }

            boolean verified = authService.verifyEmail(email);

            if(verified){
                message.setText("Email verified successfully. You can login now.");
            }else{
                message.setText("Verification failed.");
            }

        });

        backButton.setOnAction(e -> {

            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();

        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                title,
                emailField,
                verifyButton,
                backButton,
                message
        );

        Scene scene = new Scene(layout,350,250);

        stage.setScene(scene);
        stage.setTitle("Verify Email");
        stage.show();

    }

}
