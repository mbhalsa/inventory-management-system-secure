package model;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseOrder {
    private int orderId;
    private Date orderDate;
    private String status;
    private Supplier supplier;
    private ArrayList<PurchaseOrderItem> items;

    public PurchaseOrder() {
        items = new ArrayList<>();
    }

    public PurchaseOrder(int orderId, Date orderDate, String status, Supplier supplier) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.supplier = supplier;
        this.items = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public ArrayList<PurchaseOrderItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<PurchaseOrderItem> items) {
        this.items = items;
    }

    public void createOrder() {
        System.out.println("Purchase order created successfully.");
    }

    public void addItem(PurchaseOrderItem item) {
        if (item != null) {
            items.add(item);
            System.out.println("Item added to purchase order.");
        }
    }

    public void viewOrder() {
        System.out.println("Purchase order details displayed.");
    }
}