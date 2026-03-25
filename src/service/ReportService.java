package service;

import database.DBConnection;
import model.SalesReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;

public class ReportService {

    public ArrayList<SalesReport> getSalesReports() {
        ArrayList<SalesReport> reports = new ArrayList<>();

        String sql = "SELECT DATE(sale_date) AS report_date, SUM(total_amount) AS total_sales " +
                "FROM sales GROUP BY DATE(sale_date) ORDER BY report_date DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            int reportId = 1;

            while (resultSet.next()) {
                Date reportDate = resultSet.getDate("report_date");
                double totalSales = resultSet.getDouble("total_sales");

                SalesReport report = new SalesReport(reportId, reportDate, reportDate, totalSales);
                reports.add(report);
                reportId++;
            }

        } catch (Exception e) {
            System.out.println("Error loading sales reports: " + e.getMessage());
        }

        return reports;
    }
}