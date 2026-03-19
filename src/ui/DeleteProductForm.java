package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.ProductService;
import service.SessionManager;

public class DeleteProductForm {
    private ProductService productService;

    public DeleteProductForm() {
        productService = new ProductService();
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Delete Product");

        Label idLabel = new Label("Product ID:");
        TextField idField = new TextField();

        Button deleteButton = new Button("Delete Product");
        Label messageLabel = new Label();

        deleteButton.setOnAction(e -> {
            if (!SessionManager.isStoreManager()) {
                messageLabel.setText("Access denied. Only Store Manager can delete products.");
                return;
            }

            try {
                String idText = idField.getText().trim();

                if (idText.isEmpty()) {
                    messageLabel.setText("Product ID is required.");
                    return;
                }

                int productId = Integer.parseInt(idText);
                boolean deleted = productService.deleteProduct(productId);

                if (deleted) {
                    messageLabel.setText("Product deleted successfully.");
                    idField.clear();
                } else {
                    messageLabel.setText("Product not found or could not be deleted.");
                }

            } catch (NumberFormatException ex) {
                messageLabel.setText("Product ID must be numeric.");
            } catch (Exception ex) {
                messageLabel.setText("Invalid product ID.");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                idLabel, idField,
                deleteButton,
                messageLabel
        );

        Scene scene = new Scene(root, 300, 180);
        stage.setScene(scene);
        stage.show();
    }
}