package model;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String contactInfo;
    private String address;

    public Supplier() {
    }

    public Supplier(int supplierId, String supplierName, String contactInfo, String address) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.address = address;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addSupplier() {
        System.out.println("Supplier added successfully.");
    }

    public void updateSupplier() {
        System.out.println("Supplier updated successfully.");
    }

    public void deleteSupplier() {
        System.out.println("Supplier deleted successfully.");
    }

    public void viewSupplier() {
        System.out.println("Supplier details displayed.");
    }
}