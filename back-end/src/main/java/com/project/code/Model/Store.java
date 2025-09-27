package com.project.code.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Store name cannot be null")
    @NotBlank(message = "Store name cannot be blank")
    private String name;

    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be blank")
    private String address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("inventory-store")
    private List<Inventory> inventories;

    // Default constructor
    public Store() {}

    // Custom constructor
    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}