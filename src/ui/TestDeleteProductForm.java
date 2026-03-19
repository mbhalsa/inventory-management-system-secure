package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestDeleteProductForm extends Application {
    @Override
    public void start(Stage primaryStage) {
        DeleteProductForm deleteProductForm = new DeleteProductForm();
        deleteProductForm.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}