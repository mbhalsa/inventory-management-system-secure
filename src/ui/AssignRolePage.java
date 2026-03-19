package ui;

import dao.RoleDAO;
import dao.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Role;
import model.User;
import service.AuthService;
import service.SessionManager;

import java.util.ArrayList;

public class AssignRolePage {

    private Stage stage;
    private AuthService authService;
    private RoleDAO roleDAO;
    private UserDAO userDAO;

    public AssignRolePage(Stage stage) {
        this.stage = stage;
        this.authService = new AuthService();
        this.roleDAO = new RoleDAO();
        this.userDAO = new UserDAO();
    }

    public void show() {
        Label titleLabel = new Label("Assign User Role");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter user email");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.setPromptText("Select role");

        ArrayList<Role> roles = roleDAO.getAllRoles();
        for (Role role : roles) {
            roleComboBox.getItems().add(role.getRoleName());
        }

        Label messageLabel = new Label();

        Button assignButton = new Button("Assign Role");
        Button backButton = new Button("Back to Dashboard");

        assignButton.setOnAction(e -> {
            if (!SessionManager.isAdmin()) {
                messageLabel.setText("Only admin can assign roles.");
                return;
            }

            String email = emailField.getText().trim();
            String selectedRole = roleComboBox.getValue();

            if (email.isEmpty() || selectedRole == null) {
                messageLabel.setText("Please enter email and select role.");
                return;
            }

            User user = userDAO.getUserByEmail(email);
            if (user == null) {
                messageLabel.setText("User not found.");
                return;
            }

            Role role = roleDAO.getRoleByName(selectedRole);
            if (role == null) {
                messageLabel.setText("Invalid role selected.");
                return;
            }

            boolean success = authService.assignRole(email, role.getRoleId());

            if (success) {
                messageLabel.setText("Role assigned successfully.");
                emailField.clear();
                roleComboBox.setValue(null);
            } else {
                messageLabel.setText("Failed to assign role.");
            }
        });

        backButton.setOnAction(e -> {
            DashboardPage dashboardPage = new DashboardPage(stage);
            dashboardPage.show();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                titleLabel,
                emailField,
                roleComboBox,
                assignButton,
                backButton,
                messageLabel
        );

        Scene scene = new Scene(layout, 380, 300);
        stage.setTitle("Assign Role");
        stage.setScene(scene);
        stage.show();
    }
}
