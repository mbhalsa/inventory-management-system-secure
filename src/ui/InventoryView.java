package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import service.ProductService;

import java.util.ArrayList;

public class InventoryView {
    private ProductService productService;

    public InventoryView() {
        productService = new ProductService();
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Inventory View");

        ListView<String> listView = new ListView<>();

        ArrayList<Product> products = productService.getInventoryProducts();

        for (Product product : products) {
            String item = "ID: " + product.getProductId()
                    + " | Name: " + product.getProductName()
                    + " | Category: " + product.getCategory()
                    + " | Price: " + product.getPrice()
                    + " | Quantity: " + product.getQuantityInStock()
                    + " | Description: " + product.getDescription();

            listView.getItems().add(item);
        }

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().add(listView);

        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.show();
    }
}