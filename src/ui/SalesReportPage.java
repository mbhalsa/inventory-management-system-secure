package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SalesReport;
import service.ReportService;

import java.util.ArrayList;

public class SalesReportPage {

    private Stage stage;
    private ReportService reportService;
    private ListView<String> reportList;

    public SalesReportPage(Stage stage) {
        this.stage = stage;
        this.reportService = new ReportService();
    }

    public void show() {

        Label title = new Label("Sales Reports");

        reportList = new ListView<>();

        Button loadReportButton = new Button("Load Sales Report");
        Button backButton = new Button("Back");

        loadReportButton.setOnAction(e -> loadReport());

        backButton.setOnAction(e -> {
            DashboardPage dashboardPage = new DashboardPage(stage);
            dashboardPage.show();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        layout.getChildren().addAll(
                title,
                loadReportButton,
                backButton,
                reportList
        );

        Scene scene = new Scene(layout, 500, 500);

        stage.setTitle("Sales Report");
        stage.setScene(scene);
        stage.show();
    }

    private void loadReport() {

        reportList.getItems().clear();

        ArrayList<SalesReport> reports = reportService.getSalesReports();

        for (SalesReport report : reports) {

            String row =
                    "Report ID: " + report.getReportId() +
                            " | Date: " + report.getStartDate() +
                            " | Total Sales: " + report.getTotalSales();

            reportList.getItems().add(row);
        }
    }
}