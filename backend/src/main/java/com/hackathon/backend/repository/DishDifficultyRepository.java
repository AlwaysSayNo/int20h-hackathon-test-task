package com.hackathon.backend.repository;

import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.DishDifficulty;
import com.hackathon.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishDifficultyRepository extends JpaRepository<DishDifficulty, Long> {

    DishDifficulty findDishDifficultyByUserAndDish(User user, Dish dish);

}