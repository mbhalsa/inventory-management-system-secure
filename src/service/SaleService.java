package service;

import dao.AuditLogDAO;
import dao.SaleDAO;
import model.Sale;

import java.util.ArrayList;

public class SaleService {

    private SaleDAO saleDAO;
    private AuditLogDAO auditLogDAO;

    public SaleService() {
        saleDAO = new SaleDAO();
        auditLogDAO = new AuditLogDAO();
    }

    public boolean recordSale(Sale sale) {

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