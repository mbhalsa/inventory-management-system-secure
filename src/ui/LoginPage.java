package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AuthService;

public class LoginPage {

    private Stage stage;
    private AuthService authService;

    public LoginPage(Stage stage) {
        this.stage = stage;
        this.authService = new AuthService();
    }

    public void show() {

        Label title = new Label("Login");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label message = new Label();

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(e -> {

            String email = emailField.getText();
            String password = passwordField.getText();

            if(email.isEmpty() || password.isEmpty()){
                message.setText("Enter email and password");
                return;
            }

            boolean success = authService.login(email,password);

            if(success){
                DashboardPage dashboard = new DashboardPage(stage);
                dashboard.show();
            }else{
                message.setText("Invalid email or password");
            }

        });

        registerButton.setOnAction(e -> {

            RegisterPage registerPage = new RegisterPage(stage);
            registerPage.show();

        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                title,
                emailField,
                passwordField,
                loginButton,
                registerButton,
                message
        );

        Scene scene = new Scene(layout,350,300);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

    }

}
