package model;

import java.util.Date;

public class SalesReport {
    private int reportId;
    private Date startDate;
    private Date endDate;
    private double totalSales;

    public SalesReport() {
    }

    public SalesReport(int reportId, Date startDate, Date endDate, double totalSales) {
        this.reportId = reportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalSales = totalSales;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public void generate() {
        System.out.println("Sales report generated.");
    }

    public void exportReport() {
        System.out.println("Sales report exported.");
    }
}