package service;

public class ValidationService {

    public ValidationService() {
    }

    public boolean validateSupplierData() {
        return true;
    }

    public boolean validateSaleTransaction() {
        return true;
    }

    public boolean validatePurchaseOrder() {
        return true;
    }

    public boolean detectDuplicateOrders() {
        return false;
    }

    public String sanitizeInput() {
        return "Sanitized input";
    }
}