package service;

import dao.AuditLogDAO;
import dao.SupplierDAO;
import model.Supplier;
import java.util.ArrayList;

public class SupplierService {

    private SupplierDAO supplierDAO;
    private AuditLogDAO auditLogDAO;

    public SupplierService() {
        supplierDAO = new SupplierDAO();
        auditLogDAO = new AuditLogDAO();
    }

    public boolean addSupplier(Supplier supplier) {

        boolean result = supplierDAO.addSupplier(supplier);

        if (result) {
            auditLogDAO.addLog("ADD_SUPPLIER", SessionManager.getCurrentUserName(), "Supplier added: " + supplier.getSupplierName());
        }
        return result;}

    public boolean updateSupplier(Supplier supplier) {
        boolean result = supplierDAO.updateSupplier(supplier);
        if (result) {
            auditLogDAO.addLog("UPDATE_SUPPLIER", SessionManager.getCurrentUserName(), "Supplier updated ID: " + supplier.getSupplierId());
        }
        return result;
    }
    public boolean deleteSupplier(int supplierId) {

        boolean result = supplierDAO.deleteSupplier(supplierId);
        if (result) {
            auditLogDAO.addLog("DELETE_SUPPLIER", SessionManager.getCurrentUserName(), "Supplier deleted ID: " + supplierId);
        }

        return result;}

    public Supplier getSupplierById(int supplierId) {
        return supplierDAO.getSupplierById(supplierId);
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }
}
