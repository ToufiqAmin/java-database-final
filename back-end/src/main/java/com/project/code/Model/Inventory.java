package com.project.code.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("inventory-product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference("inventory-store")
    private Store store;

    @NotNull(message = "Stock level cannot be null")
    private Integer stockLevel;

    // Default constructor (required by JPA)
    public Inventory() {}

    // Custom constructor
    public Inventory(Product product, Store store, Integer stockLevel) {
        this.product = product;
        this.store = store;
        this.stockLevel = stockLevel;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Store getStore() {
        return store;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }
}


