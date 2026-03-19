package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestUpdateProductForm extends Application {
    @Override
    public void start(Stage primaryStage) {
        UpdateProductForm updateProductForm = new UpdateProductForm();
        updateProductForm.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}