package com.hackathon.backend.repository;

import com.hackathon.backend.model.Dish;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("SELECT d FROM Dish d")
    List<Dish> getDishes(Pageable pageable);

    Optional<Dish> getDishById(Long id);

    @Query("""
            SELECT d FROM Dish d WHERE NOT EXISTS
                (SELECT pd FROM ProductToDish pd WHERE pd.dish.id = d.id AND pd.product.id NOT IN :product_ids)
            """)
    List<Dish> getDishesWithALlProducts(@Param("product_ids") List<Long> productIds);

    @Query("SELECT u.productUserHas FROM User u WHERE u.id = :user_id")
    List<Dish> getCustomDishes(Pageable pageable, @Param("user_id") Long userId);
}
