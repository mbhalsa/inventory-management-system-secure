package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.ProductService;

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
            try {
                int productId = Integer.parseInt(idField.getText());
                boolean deleted = productService.deleteProduct(productId);

                if (deleted) {
                    messageLabel.setText("Product deleted successfully.");
                    idField.clear();
                } else {
                    messageLabel.setText("Product not found or could not be deleted.");
                }

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