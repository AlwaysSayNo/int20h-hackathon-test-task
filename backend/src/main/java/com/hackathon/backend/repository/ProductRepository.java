package com.hackathon.backend.repository;

import com.hackathon.backend.enumeration.ProductCategory;
import com.hackathon.backend.model.Product;
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
    List<ProductCategory> getCategories();

    @Query("SELECT u.productUserHas FROM User u WHERE u.id = :user_id")
    Set<Product> getUserProducts(@Param("user_id") Long userId);

    List<Product> getProductsByCategory(ProductCategory category, Pageable pageable);
}
