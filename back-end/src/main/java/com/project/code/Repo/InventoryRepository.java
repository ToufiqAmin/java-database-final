package com.project.code.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import com.project.code.Model.Inventory;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId AND i.store.id = :storeId")
    Inventory findByProductIdAndStoreId(Long productId, Long storeId);

    List<Inventory> findByStore_Id(Long storeId);

    @Modifying
    @Transactional
    void deleteByProductId(Long productId);
}
