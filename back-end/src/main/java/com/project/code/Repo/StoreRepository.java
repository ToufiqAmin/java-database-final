package com.project.code.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.project.code.Model.Store;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findById(Long id);

    @Query("SELECT s FROM Store s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Store> findBySubName(String name);
}
