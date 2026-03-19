package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import service.ProductService;

public class UpdateProductForm {
    private ProductService productService;

    public UpdateProductForm() {
        productService = new ProductService();
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Update Product");

        Label idLabel = new Label("Product ID:");
        TextField idField = new TextField();

        Label nameLabel = new Label("Product Name:");
        TextField nameField = new TextField();

        Label categoryLabel = new Label("Category:");
        TextField categoryField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();

        Button loadButton = new Button("Load Product");
        Button updateButton = new Button("Update Product");
        Label messageLabel = new Label();

        loadButton.setOnAction(e -> {
            try {
                int productId = Integer.parseInt(idField.getText());
                Product product = productService.getProductById(productId);

                if (product != null) {
                    nameField.setText(product.getProductName());
                    categoryField.setText(product.getCategory());
                    priceField.setText(String.valueOf(product.getPrice()));
                    quantityField.setText(String.valueOf(product.getQuantityInStock()));
                    descriptionField.setText(product.getDescription());
                    messageLabel.setText("Product loaded.");
                } else {
                    messageLabel.setText("Product not found.");
                }

            } catch (Exception ex) {
                messageLabel.setText("Invalid product ID.");
            }
        });

        updateButton.setOnAction(e -> {
            try {
                int productId = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String description = descriptionField.getText();

                Product product = new Product(productId, name, category, price, quantity, description);

                boolean updated = productService.updateProduct(product);

                if (updated) {
                    messageLabel.setText("Product updated successfully.");
                } else {
                    messageLabel.setText("Failed to update product.");
                }

            } catch (Exception ex) {
                messageLabel.setText("Invalid input.");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                idLabel, idField,
                loadButton,
                nameLabel, nameField,
                categoryLabel, categoryField,
                priceLabel, priceField,
                quantityLabel, quantityField,
                descriptionLabel, descriptionField,
                updateButton,
                messageLabel
        );

        Scene scene = new Scene(root, 350, 500);
        stage.setScene(scene);
        stage.show();
    }
}