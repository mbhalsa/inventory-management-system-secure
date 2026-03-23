package service;

import dao.AuditLogDAO;
import dao.PurchaseOrderDAO;
import model.PurchaseOrder;

import java.util.ArrayList;

public class PurchaseOrderService {

    private PurchaseOrderDAO purchaseOrderDAO;
    private AuditLogDAO auditLogDAO;

    public PurchaseOrderService() {
        purchaseOrderDAO = new PurchaseOrderDAO();
        auditLogDAO = new AuditLogDAO();
    }

    public boolean createPurchaseOrder(PurchaseOrder order) {
        boolean result = purchaseOrderDAO.createPurchaseOrder(order);

        if (result) {
            auditLogDAO.addLog(
                    "CREATE_PURCHASE_ORDER",
                    SessionManager.getCurrentUserName(),
                    "Created purchase order for supplier ID: " + order.getSupplier().getSupplierId()
            );
        }

        return result;}

    public ArrayList<PurchaseOrder> getAllOrders() {
        return purchaseOrderDAO.getAllOrders();
    }

    public boolean updateOrderStatus(int orderId, String status) {
        boolean result = purchaseOrderDAO.updateOrderStatus(orderId, status);

        if (result) {
            auditLogDAO.addLog(
                    "UPDATE_PURCHASE_ORDER_STATUS",
                    SessionManager.getCurrentUserName(),
                    "Updated purchase order ID " + orderId + " to status " + status
            );
        }

        return result;
    }

    public boolean deleteOrder(int orderId) {
        boolean result = purchaseOrderDAO.deleteOrder(orderId);

        if (result) {
            auditLogDAO.addLog(
                    "DELETE_PURCHASE_ORDER",
                    SessionManager.getCurrentUserName(),
                    "Deleted purchase order ID: " + orderId
            );
        }

        return result;
    }
}