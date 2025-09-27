package com.project.code.DTO;



import java.util.List;

public class PlaceOrderRequestDTO {
    private Long storeId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String datetime;
    private List<PurchaseProductDTO> purchaseProductList;
    private Double totalPrice;

    // Getters and Setters

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<PurchaseProductDTO> getPurchaseProductList() {
        return purchaseProductList;
    }

    public void setPurchaseProductList(List<PurchaseProductDTO> purchaseProductList) {
        this.purchaseProductList = purchaseProductList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
