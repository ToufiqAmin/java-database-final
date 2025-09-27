package com.project.code.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.project.code.Model.Review;
import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByStoreIdAndProductId(Long storeId, Long productId);
}