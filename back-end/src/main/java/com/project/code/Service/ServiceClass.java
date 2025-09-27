package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceClass {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ServiceClass(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * Checks if an inventory record exists for a given product-store combination.
     * Returns true if no inventory exists (i.e., valid for creation).
     */
    public boolean validateInventory(Inventory inventory) {
        Inventory existing = inventoryRepository.findByProductIdAndStoreId(
            inventory.getProduct().getId(),
            inventory.getStore().getId()
        );
        return existing == null;
    }

    /**
     * Checks if a product with the same name already exists.
     * Returns true if no such product exists (i.e., valid for creation).
     */
    public boolean validateProduct(Product product) {
        List<Product> existingProducts = productRepository.findByName(product.getName());
        return existingProducts.isEmpty();
    }

    /**
     * Checks if a product exists by its ID.
     * Returns true if the product exists.
     */
    public boolean validateProductId(long id) {
        return productRepository.findById(id).isPresent();
    }

    /**
     * Retrieves the inventory record for a given product-store combination.
     */
    public Inventory getInventoryId(Inventory inventory) {
        return inventoryRepository.findByProductIdAndStoreId(
            inventory.getProduct().getId(),
            inventory.getStore().getId()
        );
    }
}
