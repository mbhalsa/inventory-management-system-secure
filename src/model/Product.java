package model;

public class Product {
    private int productId;
    private String productName;
    private String category;
    private double price;
    private int quantityInStock;
    private String description;

    public Product() {
    }

    public Product(int productId, String productName, String category, double price, int quantityInStock, String description) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addProduct() {
        System.out.println("Product added successfully.");
    }

    public void updateProduct() {
        System.out.println("Product updated successfully.");
    }

    public void deleteProduct() {
        System.out.println("Product deleted successfully.");
    }

    public void viewProduct() {
        System.out.println("Product details displayed.");
    }
}