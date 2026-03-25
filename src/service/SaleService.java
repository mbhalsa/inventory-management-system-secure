package service;

import dao.AuditLogDAO;
import dao.SaleDAO;
import model.Sale;
import dao.InventoryDAO;
import model.SaleItem;


import java.util.ArrayList;

public class SaleService {

    private SaleDAO saleDAO;
    private AuditLogDAO auditLogDAO;
    private InventoryDAO inventoryDAO;


    public SaleService() {
        saleDAO = new SaleDAO();
        auditLogDAO = new AuditLogDAO();
        inventoryDAO = new InventoryDAO();

    }

    public boolean recordSale(Sale sale) {
        if (sale == null || sale.getItems() == null || sale.getItems().isEmpty()) {
            return false;
        }

        double calculatedTotal = 0;

        for (SaleItem item : sale.getItems()) {
            if (item == null || item.getProduct() == null) {
                return false;
            }

            if (item.getQuantity() <= 0 || item.getUnitPrice() <= 0) {
                return false;
            }

            int availableStock = inventoryDAO.getProductQuantity(item.getProduct().getProductId());

            if (availableStock < item.getQuantity()) {
                return false;
            }

            calculatedTotal += item.calculateSubtotal();
        }

        sale.setTotalAmount(calculatedTotal);

        boolean result = saleDAO.recordSale(sale);

        if (result) {
            auditLogDAO.addLog(
                    "RECORD_SALE",
                    SessionManager.getCurrentUserName(),
                    "New sale recorded with total amount: " + sale.getTotalAmount()
            );
        }

        return result;
    }



public ArrayList<Sale> getAllSales() {
    return saleDAO.getAllSales();
}
}