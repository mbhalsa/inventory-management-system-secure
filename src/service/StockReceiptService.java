package service;

import dao.AuditLogDAO;
import dao.StockReceiptDAO;

import java.util.ArrayList;

public class StockReceiptService {

    private StockReceiptDAO stockReceiptDAO;
    private AuditLogDAO auditLogDAO;

    public StockReceiptService() {
        stockReceiptDAO = new StockReceiptDAO();
        auditLogDAO = new AuditLogDAO();
    }

    public boolean createStockReceipt(int orderId, String receivedBy) {
        boolean result = stockReceiptDAO.createStockReceipt(orderId, receivedBy);

        if (result) {
            auditLogDAO.addLog(
                    "CREATE_STOCK_RECEIPT",
                    SessionManager.getCurrentUserName(),
                    "Created stock receipt for order ID: " + orderId
            );
        }

        return result;
    }

    public ArrayList<String> getAllReceipts() {
        return stockReceiptDAO.getAllReceipts();
    }

    public boolean deleteReceipt(int receiptId) {
        boolean result = stockReceiptDAO.deleteReceipt(receiptId);

        if (result) {
            auditLogDAO.addLog(
                    "DELETE_STOCK_RECEIPT",
                    SessionManager.getCurrentUserName(),
                    "Deleted stock receipt ID: " + receiptId
            );
        }

        return result;
    }
}