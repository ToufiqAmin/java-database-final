package com.project.code.Service;

import com.project.code.DTO.PlaceOrderRequestDTO;
import com.project.code.DTO.PurchaseProductDTO;

import com.project.code.Model.*;
import com.project.code.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        // 1. Retrieve or Create Customer
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setName(placeOrderRequest.getCustomerName());
                    newCustomer.setEmail(placeOrderRequest.getCustomerEmail());
                    newCustomer.setPhone(placeOrderRequest.getCustomerPhone());
                    return customerRepository.save(newCustomer);
                });

        // 2. Retrieve Store
        Store store = storeRepository.findById(placeOrderRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + placeOrderRequest.getStoreId()));

        // 3. Create OrderDetails
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCustomer(customer);
        orderDetails.setStore(store);
        orderDetails.setTotalPrice(placeOrderRequest.getTotalPrice().floatValue());
        orderDetails.setDate(LocalDateTime.now());
        orderDetailsRepository.save(orderDetails);

        // 4. Create and Save OrderItems
        for (PurchaseProductDTO purchase : placeOrderRequest.getPurchaseProductList()) {
            Long productId = purchase.getId();
            int quantity = purchase.getQuantity();

            Inventory inventory = inventoryRepository.findByProductIdAndStoreId(productId, store.getId());
            if (inventory == null || inventory.getStockLevel() < quantity) {
                throw new RuntimeException("Insufficient stock for product ID: " + productId);
            }

            // Update inventory stock
            inventory.setStockLevel(inventory.getStockLevel() - quantity);
            inventoryRepository.save(inventory);

            // Create and save OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderDetails(orderDetails);
            orderItem.setProduct(productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId)));
            orderItem.setQuantity(quantity);
            orderItemRepository.save(orderItem);
        }
    }
}
