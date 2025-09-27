package com.project.code.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderDetails orderDetails;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    private Double price;

    // Default constructor
    public OrderItem() {}

    // Parameterized constructor
    public OrderItem(OrderDetails orderDetails, Product product, Integer quantity, Double price) {
        this.orderDetails = orderDetails;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}