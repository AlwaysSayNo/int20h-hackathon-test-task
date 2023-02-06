package com.hackathon.backend.repository;

import com.hackathon.backend.model.Product;
import com.hackathon.backend.model.enumeration.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product as p")
    List<Product> getProducts();

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<Category> getCategories();

    @Query("SELECT u.products FROM User u WHERE u.id = :user_id")
    Set<Product> getUserProducts(@Param("user_id") Long userId);

    List<Product> getProductsByCategory(Category category, Pageable pageable);
}
