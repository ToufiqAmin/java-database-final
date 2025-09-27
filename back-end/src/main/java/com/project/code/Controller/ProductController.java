package com.project.code.Controller;

import com.project.code.Model.Product;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    // 3. Add Product
    @PostMapping
    public Map<String, String> addProduct(@RequestBody Product product) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!serviceClass.validateProduct(product)) {
                response.put("message", "Product already exists");
            } else {
                productRepository.save(product);
                response.put("message", "Product added successfully");
            }
        } catch (DataIntegrityViolationException e) {
            response.put("message", "SKU must be unique");
        } catch (Exception e) {
            response.put("message", "Unexpected error: " + e.getMessage());
        }
        return response;
    }

    // 4. Get Product by ID
    @GetMapping("/product/{id}")
    public Map<String, Object> getProductById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            response.put("products", product.get());
        } else {
            response.put("message", "Product not found");
        }
        return response;
    }

    // 5. Update Product
    @PutMapping
    public Map<String, String> updateProduct(@RequestBody Product product) {
        Map<String, String> response = new HashMap<>();
        try {
            productRepository.save(product);
            response.put("message", "Product updated successfully");
        } catch (Exception e) {
            response.put("message", "Error updating product: " + e.getMessage());
        }
        return response;
    }

    // 6. Filter by Category and Name
    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterByCategoryProduct(@PathVariable String name,
                                                       @PathVariable String category) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if ("null".equals(name) && !"null".equals(category)) {
            products = productRepository.findByCategory(category);
        } else if (!"null".equals(name) && "null".equals(category)) {
            products = productRepository.findProductBySubName(name);
        } else {
            products = productRepository.findProductBySubNameAndCategory(name, category);
        }

        response.put("products", products);
        return response;
    }

    // 7. List All Products
    @GetMapping
    public Map<String, Object> listProduct() {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findAll();
        response.put("products", products);
        return response;
    }

    // 8. Filter by Category and Store ID
    @GetMapping("/filter/{category}/{storeid}")
    public Map<String, Object> getProductByCategoryAndStoreId(@PathVariable String category,
                                                              @PathVariable Long storeid) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findProductByCategory(category, storeid);
        response.put("product", products);
        return response;
    }

    // 9. Delete Product
    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        if (!serviceClass.validateProductId(id)) {
            response.put("message", "Product not present in database");
        } else {
            inventoryRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            response.put("message", "Product deleted successfully");
        }
        return response;
    }

    // 10. Search Product by Name
    @GetMapping("/searchProduct/{name}")
    public Map<String, Object> searchProduct(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findProductBySubName(name);
        response.put("products", products);
        return response;
    }
}