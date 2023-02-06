package com.hackathon.backend.repository;

import com.hackathon.backend.model.FavoriteDishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteDishRepository extends JpaRepository<FavoriteDishes, Long> {
}
