package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestAddProductForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        AddProductForm addProductForm = new AddProductForm();
        addProductForm.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}