package com.hackathon.backend.repository;

import com.hackathon.backend.model.FavoriteProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProducts, Long> {
}
