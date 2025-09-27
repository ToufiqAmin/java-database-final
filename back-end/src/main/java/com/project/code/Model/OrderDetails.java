package com.project.code.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonManagedReference
    private Store store;

    @NotNull(message = "Total price cannot be null")
    private float totalPrice;

    @NotNull(message = "Order date cannot be null")
    private LocalDateTime date;

    @OneToMany(mappedBy = "orderDetails", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    // Default constructor
    public OrderDetails() {}

    // Parameterized constructor
    public OrderDetails(Customer customer, Store store, List<OrderItem> orderItems, float totalPrice, LocalDateTime date) {
        this.customer = customer;
        this.store = store;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    // Getters
    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Store getStore() {
        return store;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
