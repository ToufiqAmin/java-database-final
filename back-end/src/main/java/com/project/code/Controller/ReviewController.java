package com.project.code.Controller;

import com.project.code.Model.Review;
import com.project.code.Model.Customer;
import com.project.code.Repo.ReviewRepository;
import com.project.code.Repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable Long storeId,
                                          @PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> reviewList = new ArrayList<>();

        List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);

        for (Review review : reviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("rating", review.getRating());
            reviewMap.put("comment", review.getReview());
            
            Long customerId = Long.parseLong(review.getCustomerId());
            Optional<Customer> customerOpt = customerRepository.findById(customerId);
            String customerName = customerOpt.map(Customer::getName).orElse("Unknown");
            reviewMap.put("customerName", customerName);

            reviewList.add(reviewMap);
        }

        response.put("reviews", reviewList);
        return response;
    }
}
