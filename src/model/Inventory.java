package model;

import java.util.ArrayList;

public class Inventory {
    private int inventoryId;
    private ArrayList<Product> productList;

    public Inventory() {
        productList = new ArrayList<>();
    }

    public Inventory(int inventoryId) {
        this.inventoryId = inventoryId;
        this.productList = new ArrayList<>();
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public void addStock(Product product, int qty) {
        if (product != null && qty > 0) {
            product.setQuantityInStock(product.getQuantityInStock() + qty);
            if (!productList.contains(product)) {
                productList.add(product);
            }
            System.out.println("Stock added successfully.");
        } else {
            System.out.println("Invalid product or quantity.");
        }
    }

    public void reduceStock(Product product, int qty) {
        if (product != null && qty > 0 && product.getQuantityInStock() >= qty) {
            product.setQuantityInStock(product.getQuantityInStock() - qty);
            System.out.println("Stock reduced successfully.");
        } else {
            System.out.println("Invalid stock reduction.");
        }
    }

    public void viewInventory() {
        for (Product product : productList) {
            System.out.println(product.getProductName() + " - Stock: " + product.getQuantityInStock());
        }
    }

    public Product findProduct(int id) {
        for (Product product : productList) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }
}