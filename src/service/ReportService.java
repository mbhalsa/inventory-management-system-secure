package service;

import model.SalesReport;
import java.util.Date;

public class ReportService {

    public ReportService() {
    }

    public SalesReport generateSalesReport(Date startDate, Date endDate) {
        SalesReport report = new SalesReport();
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        return report;
    }
}