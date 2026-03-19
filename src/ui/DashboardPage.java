package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.SessionManager;

public class DashboardPage {

    private Stage stage;

    public DashboardPage(Stage stage){
        this.stage = stage;
    }

    public void show(){

        Label welcome = new Label("Welcome " + SessionManager.getCurrentUserName());
        Label role = new Label("Role: " + SessionManager.getCurrentUserRole());

        VBox layout = new VBox(10,welcome,role);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,400,300);

        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();

    }

}
