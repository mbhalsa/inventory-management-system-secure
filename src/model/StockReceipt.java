package model;

import java.util.Date;

public class StockReceipt {
    private int receiptId;
    private Date receivedDate;
    private PurchaseOrder purchaseOrder;
    private String receivedBy;

    public StockReceipt() {
    }

    public StockReceipt(int receiptId, Date receivedDate, PurchaseOrder purchaseOrder, String receivedBy) {
        this.receiptId = receiptId;
        this.receivedDate = receivedDate;
        this.purchaseOrder = purchaseOrder;
        this.receivedBy = receivedBy;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public void receiveStock() {
        System.out.println("Stock received successfully.");
    }

    public void updateInventory() {
        System.out.println("Inventory updated after stock receipt.");
    }
}