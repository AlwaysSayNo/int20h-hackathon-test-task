package com.hackathon.backend.repository;

import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.DishDifficulty;
import com.hackathon.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The DishDifficultyRepository interface represents a repository for the {@link DishDifficulty} entity.
 * It extends {@link JpaRepository} and provides additional methods for retrieving {@link DishDifficulty} entities.
 */
@Repository
public interface DishDifficultyRepository extends JpaRepository<DishDifficulty, Long> {

    /**
     * Retrieves a {@link DishDifficulty} entity based on a specified {@link User} and {@link Dish}.
     * @param user the User entity to retrieve the DishDifficulty for.
     * @param dish the Dish entity to retrieve the DishDifficulty for.
     * @return the DishDifficulty entity for the specified User and Dish.
     */
    DishDifficulty findDishDifficultyByUserAndDish(User user, Dish dish);

}