package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AuthService;

public class ResetPasswordPage {

    private Stage stage;
    private AuthService authService;

    public ResetPasswordPage(Stage stage) {
        this.stage = stage;
        this.authService = new AuthService();
    }

    public void show() {
        Label titleLabel = new Label("Reset Password");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter new password");

        Label messageLabel = new Label();

        Button resetButton = new Button("Reset Password");
        Button backButton = new Button("Back to Login");

        resetButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String newPassword = newPasswordField.getText().trim();

            if (email.isEmpty() || newPassword.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            if (newPassword.length() < 8) {
                messageLabel.setText("Password must be at least 8 characters.");
                return;
            }

            boolean success = authService.resetPassword(email, newPassword);

            if (success) {
                messageLabel.setText("Password reset successfully.");
                emailField.clear();
                newPasswordField.clear();
            } else {
                messageLabel.setText("Reset failed. Check the email.");
            }
        });

        backButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                titleLabel,
                emailField,
                newPasswordField,
                resetButton,
                backButton,
                messageLabel
        );

        Scene scene = new Scene(layout, 350, 280);
        stage.setTitle("Reset Password");
        stage.setScene(scene);
        stage.show();
    }
}
