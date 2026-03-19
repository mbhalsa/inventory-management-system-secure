package service;

import dao.AuditLogDAO;
import dao.InventoryDAO;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;

public class ProductService {
    private ProductDAO productDAO;
    private InventoryDAO inventoryDAO;
    private AuditLogDAO auditLogDAO;

    public ProductService() {
        productDAO = new ProductDAO();
        inventoryDAO = new InventoryDAO();
        auditLogDAO = new AuditLogDAO();
    }

    public boolean addProduct(Product product) {
        boolean added = productDAO.addProduct(product);

        if (added) {
            Product savedProduct = getLastInsertedProduct(product.getProductName());
            if (savedProduct != null) {
                boolean inventoryAdded = inventoryDAO.insertInventoryRecord(
                        savedProduct.getProductId(),
                        product.getQuantityInStock()
                );

                if (inventoryAdded) {
                    auditLogDAO.addLog(
                            "ADD_PRODUCT",
                            SessionManager.getCurrentUserName(),
                            "Added product: " + product.getProductName()
                    );
                    return true;
                }
            }
        }

        return false;
    }

    public boolean updateProduct(Product product) {
        boolean updated = productDAO.updateProduct(product);

        if (updated) {
            boolean inventoryUpdated = inventoryDAO.updateInventoryQuantity(
                    product.getProductId(),
                    product.getQuantityInStock()
            );

            if (inventoryUpdated) {
                auditLogDAO.addLog(
                        "UPDATE_PRODUCT",
                        SessionManager.getCurrentUserName(),
                        "Updated product ID: " + product.getProductId()
                );
                return true;
            }
        }

        return false;
    }

    public boolean deleteProduct(int productId) {
        Product product = productDAO.getProductById(productId);
        boolean deleted = productDAO.deleteProduct(productId);

        if (deleted) {
            String productInfo = (product != null) ? product.getProductName() : "ID " + productId;

            auditLogDAO.addLog(
                    "DELETE_PRODUCT",
                    SessionManager.getCurrentUserName(),
                    "Deleted product: " + productInfo
            );
            return true;
        }

        return false;
    }

    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

    public ArrayList<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public ArrayList<Product> getInventoryProducts() {
        return inventoryDAO.getInventoryProducts();
    }

    private Product getLastInsertedProduct(String productName) {
        ArrayList<Product> products = productDAO.getAllProducts();

        for (int i = products.size() - 1; i >= 0; i--) {
            if (products.get(i).getProductName().equals(productName)) {
                return products.get(i);
            }
        }

        return null;
    }
}