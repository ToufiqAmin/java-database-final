package com.project.code.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    @NotNull(message = "Customer ID cannot be null")
    private String customerId;

    @NotNull(message = "Product ID cannot be null")
    private String productId;

    @NotNull(message = "Store ID cannot be null")
    private String storeId;

    @NotNull(message = "Rating cannot be null")
    private int rating;

    private String review;

    // Default constructor
    public Review() {}

    // Parameterized constructor
    public Review(String customerId, String productId, String storeId, int rating, String review) {
        this.customerId = customerId;
        this.productId = productId;
        this.storeId = storeId;
        this.rating = rating;
        this.review = review;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public String getStoreId() {
        return storeId;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }
}