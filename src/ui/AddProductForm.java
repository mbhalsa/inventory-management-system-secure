package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import service.ProductService;

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
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String description = descriptionField.getText();

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