package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import service.ProductService;
import service.SessionManager;
import service.ValidationService;


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
                String idText = idField.getText().trim();

                if (!ValidationService.isPositiveInteger(idText)) {
                    messageLabel.setText("Product ID must be a valid number.");
                    return;
                }

                int productId = Integer.parseInt(idText);
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

            } catch (NumberFormatException ex) {
                messageLabel.setText("Product ID must be numeric.");
            } catch (Exception ex) {
                messageLabel.setText("Invalid product ID.");
            }
        });

        updateButton.setOnAction(e -> {
            if (!SessionManager.isStoreManager()) {
                messageLabel.setText("Access denied. Only Store Manager can update products.");
                return;
            }

            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            String priceText = priceField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String description = descriptionField.getText().trim();

            if (!ValidationService.isPositiveInteger(idText)
                    || !ValidationService.isTextValid(name)
                    || !ValidationService.isTextValid(category)
                    || !ValidationService.isPositiveDouble(priceText)
                    || !ValidationService.isNonNegativeInteger(quantityText)
                    || !ValidationService.isTextValid(description)) {
                messageLabel.setText("Please enter valid product information.");
                return;
            }

                int productId = Integer.parseInt(idText);
                double price = Double.parseDouble(priceText);
                int quantity = Integer.parseInt(quantityText);
                Product product = new Product(productId, name, category, price, quantity, description);
                boolean updated = productService.updateProduct(product);

            if (updated) {
                messageLabel.setText("Product updated successfully.");
            } else {
                messageLabel.setText("Failed to update product.");
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

