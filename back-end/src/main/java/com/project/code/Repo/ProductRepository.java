package com.project.code.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.project.code.Model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Basic CRUD and derived queries
    List<Product> findAll();

    List<Product> findByCategory(String category);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    Product findBySku(String sku);

    List<Product> findByName(String name);

    Optional<Product> findById(Long id);

    // Custom queries via Inventory mapping
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND i.product.category = :category")
    List<Product> findProductByCategory(String category, Long storeId);

    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :name, '%')) AND i.product.category = :category")
    List<Product> findByNameAndCategory(@Param("storeId") Long storeId,
                                    @Param("name") String name,
                                    @Param("category") String category);

    // @Query("SELECT i.product FROM Inventory i WHERE LOWER(CONCAT(i.product.name, ' ', i.product.description)) LIKE LOWER(CONCAT('%', :name, '%'))")
    // List<Product> findInventoryProductsBySubName(@Param("name") String name);

    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId")
    List<Product> findProductsByStoreId(Long storeId);

    @Query("SELECT i.product FROM Inventory i WHERE i.product.category = :category AND i.store.id = :storeId")
    List<Product> findProductsByStoreIdAndCategory(Long storeId, String category);

    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByNameLike(@Param("storeId") Long storeId, @Param("name") String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findProductBySubName(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% AND p.category = :category")
    List<Product> findProductBySubNameAndCategory(@Param("name") String name, @Param("category") String category);

}
