package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestInventoryView extends Application {
    @Override
    public void start(Stage primaryStage) {
        InventoryView inventoryView = new InventoryView();
        inventoryView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}