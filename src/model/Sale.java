package model;

import java.util.ArrayList;
import java.util.Date;

public class Sale {
    private int saleId;
    private Date saleDate;
    private double totalAmount;
    private User user;
    private ArrayList<SaleItem> items;

    public Sale() {
        items = new ArrayList<>();
    }

    public Sale(int saleId, Date saleDate, double totalAmount, User user) {
        this.saleId = saleId;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.user = user;
        this.items = new ArrayList<>();
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<SaleItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<SaleItem> items) {
        this.items = items;
    }

    public void recordSale() {
        System.out.println("Sale recorded successfully.");
    }

    public void addItem(SaleItem item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void generateInvoice() {
        System.out.println("Invoice generated successfully.");
    }
}