package model;

import java.util.Date;

public class AuditLog {
    private int logId;
    private String action;
    private Date actionDate;
    private String performedBy;
    private String details;

    public AuditLog() {
    }

    public AuditLog(int logId, String action, Date actionDate, String performedBy, String details) {
        this.logId = logId;
        this.action = action;
        this.actionDate = actionDate;
        this.performedBy = performedBy;
        this.details = details;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void logAction() {
        System.out.println("Audit log recorded successfully.");
    }

    public void viewLogs() {
        System.out.println("Audit logs displayed.");
    }
}