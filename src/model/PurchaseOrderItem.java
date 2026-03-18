package model;

public class PurchaseOrderItem {
    private int orderItemId;
    private Product product;
    private int quantity;
    private double unitPrice;

    public PurchaseOrderItem() {
    }

    public PurchaseOrderItem(int orderItemId, Product product, int quantity, double unitPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
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