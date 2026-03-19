package ui;

import dao.RoleDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Role;
import service.AuthService;

import java.util.ArrayList;

public class RegisterPage {

    private Stage stage;
    private AuthService authService;
    private RoleDAO roleDAO;

    public RegisterPage(Stage stage){
        this.stage = stage;
        this.authService = new AuthService();
        this.roleDAO = new RoleDAO();
    }

    public void show(){

        Label title = new Label("Register");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.setPromptText("Select Role");

        ArrayList<Role> roles = roleDAO.getAllRoles();
        for (Role role : roles) {
            roleComboBox.getItems().add(role.getRoleName());
        }

        Label message = new Label();

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");

        registerButton.setOnAction(e -> {
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String selectedRole = roleComboBox.getValue();

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || selectedRole == null) {
                message.setText("Please fill in all fields.");
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                message.setText("Enter a valid email.");
                return;
            }

            if (password.length() < 8) {
                message.setText("Password must be at least 8 characters.");
                return;
            }

            Role role = roleDAO.getRoleByName(selectedRole);

            if (role == null) {
                message.setText("Invalid role selected.");
                return;
            }

            boolean registered = authService.register(fullName, email, password, role.getRoleId());

            if (registered) {
                message.setText("Registration successful. Please verify email before login.");
                fullNameField.clear();
                emailField.clear();
                passwordField.clear();
                roleComboBox.setValue(null);
            } else {
                message.setText("Registration failed. Email may already exist.");
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
                fullNameField,
                emailField,
                passwordField,
                roleComboBox,
                registerButton,
                backButton,
                message
        );

        Scene scene = new Scene(layout, 380, 380);

        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }
}
