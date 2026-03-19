package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterPage {

    private Stage stage;

    public RegisterPage(Stage stage){
        this.stage = stage;
    }

    public void show(){

        Label label = new Label("Register Page (Coming Next)");

        VBox layout = new VBox(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,350,300);

        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();

    }

}
