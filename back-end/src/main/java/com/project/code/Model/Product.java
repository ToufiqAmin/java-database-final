package com.project.code.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "sku"))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Category cannot be null")
    private String category;

    @NotNull(message = "Price cannot be null")
    private double price;

    @NotNull(message = "SKU cannot be null")
    private String sku;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("inventory-product")
    private List<Inventory> inventories;

    // Default constructor
    public Product() {}

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getSku() {
        return sku;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}