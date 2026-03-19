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

public class AddProductForm {
    private ProductService productService;

    public AddProductForm() {
        productService = new ProductService();
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Add Product");

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

        Button addButton = new Button("Add Product");
        Label messageLabel = new Label();

        addButton.setOnAction(e -> {
            if (!SessionManager.isStoreManager()) {
                messageLabel.setText("Access denied. Only Store Manager can add products.");
                return;
            }

            try {
                String name = nameField.getText().trim();
                String category = categoryField.getText().trim();
                String priceText = priceField.getText().trim();
                String quantityText = quantityField.getText().trim();
                String description = descriptionField.getText().trim();

                if (name.isEmpty() || category.isEmpty() || priceText.isEmpty()
                        || quantityText.isEmpty() || description.isEmpty()) {
                    messageLabel.setText("All fields are required.");
                    return;
                }

                double price = Double.parseDouble(priceText);
                int quantity = Integer.parseInt(quantityText);

                if (price <= 0) {
                    messageLabel.setText("Price must be greater than 0.");
                    return;
                }

                if (quantity < 0) {
                    messageLabel.setText("Quantity cannot be negative.");
                    return;
                }

                Product product = new Product(0, name, category, price, quantity, description);
                boolean added = productService.addProduct(product);

                if (added) {
                    messageLabel.setText("Product added successfully.");
                    nameField.clear();
                    categoryField.clear();
                    priceField.clear();
                    quantityField.clear();
                    descriptionField.clear();
                } else {
                    messageLabel.setText("Failed to add product.");
                }

            } catch (NumberFormatException ex) {
                messageLabel.setText("Price and Quantity must be numeric.");
            } catch (Exception ex) {
                messageLabel.setText("Invalid input.");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                nameLabel, nameField,
                categoryLabel, categoryField,
                priceLabel, priceField,
                quantityLabel, quantityField,
                descriptionLabel, descriptionField,
                addButton, messageLabel
        );

        Scene scene = new Scene(root, 350, 400);
        stage.setScene(scene);
        stage.show();
    }
}