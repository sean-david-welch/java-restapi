package com.example.demo.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // @Query("SELECT s FROM product WHERE s.id = ?1")
    Optional<Product> findProductById(String id);
}