package model;

public class SaleItem {
    private int saleItemId;
    private Product product;
    private int quantity;
    private double unitPrice;

    public SaleItem() {
    }

    public SaleItem(int saleItemId, Product product, int quantity, double unitPrice) {
        this.saleItemId = saleItemId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getSaleItemId() {
        return saleItemId;
    }

    public void setSaleItemId(int saleItemId) {
        this.saleItemId = saleItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double calculateSubtotal() {
        return quantity * unitPrice;
    }
}