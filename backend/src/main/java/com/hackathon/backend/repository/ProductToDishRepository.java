package com.hackathon.backend.repository;

import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.ProductToDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductToDishRepository extends JpaRepository<ProductToDish, Long> {

    List<ProductToDish> getProductToDishByDish(Dish dish);

}
