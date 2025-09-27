package com.project.code.Controller;

import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import com.project.code.DTO.PlaceOrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    // 3. Add Store
    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {
        Map<String, String> response = new HashMap<>();
        Store savedStore = storeRepository.save(store);
        response.put("message", "Store created successfully with ID: " + savedStore.getId());
        return response;
    }

    // 4. Validate Store
    @GetMapping("validate/{storeId}")
    public boolean validateStore(@PathVariable Long storeId) {      
        return storeRepository.findById(storeId).isPresent();
    }

    // 5. Place Order
    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            orderService.saveOrder(placeOrderRequest);
            response.put("message", "Order placed successfully");
        } catch (Exception e) {
            response.put("Error", "Failed to place order: " + e.getMessage());
        }
        return response;
    }
}
