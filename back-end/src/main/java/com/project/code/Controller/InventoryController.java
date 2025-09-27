package com.project.code.Controller;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import com.project.code.Model.CombinedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    // 3. Update Inventory
    @PutMapping
    public Map<String, String> updateInventory(@RequestBody CombinedRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            Product product = request.getProduct();
            Inventory inventory = request.getInventory();

            if (!serviceClass.validateProductId(product.getId())) {
                response.put("message", "Invalid product ID");
                return response;
            }

            Inventory existingInventory = serviceClass.getInventoryId(inventory);
            if (existingInventory != null) {
                existingInventory.setStockLevel(inventory.getStockLevel());
                inventoryRepository.save(existingInventory);
                response.put("message", "Update available");
            } else {
                response.put("message", "No data available");
            }
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Data integrity error");
        } catch (Exception e) {
            response.put("message", "Unexpected error: " + e.getMessage());
        }
        return response;
    }

    // 4. Save Inventory
    @PostMapping
    public Map<String, Object> saveInventory(@RequestBody Inventory inventory) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!serviceClass.validateInventory(inventory)) {
                response.put("message", "Data already present");
            } else {
                inventoryRepository.save(inventory);
                response.put("message", "Data saved successfully");
            }
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Data integrity error");
        } catch (Exception e) {
            response.put("message", "Unexpected error: " + e.getMessage());
        }
        return response;
    }

    // 5. Get All Products by Store
    @GetMapping("/{storeId}")
    public Map<String, Object> getAllProducts(@PathVariable Long storeId) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findProductsByStoreId(storeId);
        response.put("products", products);
        return response;
    }

    // 6. Filter Products by Category and Name
    @GetMapping("/filter/{category}/{name}/{storeId}")
    public Map<String, Object> getProductName(@PathVariable String category,
                                              @PathVariable String name,
                                              @PathVariable Long storeId) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if ("null".equals(category) && !"null".equals(name)) {
            products = productRepository.findByNameLike(storeId, name);
        } else if (!"null".equals(category) && "null".equals(name)) {
            products = productRepository.findProductsByStoreIdAndCategory(storeId, category);
        } else {
            products = productRepository.findByNameAndCategory(storeId, name, category);
        }

        response.put("product", products);
        return response;
    }

    // 7. Search Products by Name
    @GetMapping("/search/{name}/{storeId}")
    public Map<String, Object> searchProduct(@PathVariable String name,
                                             @PathVariable Long storeId) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findByNameLike(storeId, name);
        response.put("product", products);
        return response;
    }

    // 8. Remove Product by ID
    @DeleteMapping("/{id}")
    public Map<String, String> removeProduct(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        if (!serviceClass.validateProductId(id)) {
            response.put("message", "Product not present in database");
        } else {
            productRepository.deleteById(id);
            inventoryRepository.deleteByProductId(id);
            response.put("message", "Product deleted successfully");
        }
        return response;
    }

    // 9. Validate Quantity
    @GetMapping("/validate/{quantity}/{storeId}/{productId}")
    public boolean validateQuantity(@PathVariable int quantity,
                                    @PathVariable Long storeId,
                                    @PathVariable Long productId) {
        Inventory inventory = inventoryRepository.findByProductIdAndStoreId(productId, storeId);
        return inventory != null && inventory.getStockLevel() >= quantity;
    }
}
