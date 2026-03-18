package service;

import dao.InventoryDAO;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;

public class ProductService {
    private ProductDAO productDAO;
    private InventoryDAO inventoryDAO;

    public ProductService() {
        productDAO = new ProductDAO();
        inventoryDAO = new InventoryDAO();
    }

    public boolean addProduct(Product product) {
        boolean added = productDAO.addProduct(product);

        if (added) {
            Product savedProduct = getLastInsertedProduct(product.getProductName());
            if (savedProduct != null) {
                return inventoryDAO.insertInventoryRecord(
                        savedProduct.getProductId(),
                        product.getQuantityInStock()
                );
            }
        }

        return false;
    }

    public boolean updateProduct(Product product) {
        boolean updated = productDAO.updateProduct(product);

        if (updated) {
            return inventoryDAO.updateInventoryQuantity(
                    product.getProductId(),
                    product.getQuantityInStock()
            );
        }

        return false;
    }

    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
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