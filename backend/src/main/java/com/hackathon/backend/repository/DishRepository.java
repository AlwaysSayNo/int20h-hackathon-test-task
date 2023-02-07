package com.hackathon.backend.repository;

import com.hackathon.backend.model.Dish;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("""
            SELECT d FROM Dish d WHERE NOT EXISTS (
                SELECT u FROM User u WHERE u.id != :user_id AND EXISTS (
                    SELECT cd FROM u.customDishes cd WHERE cd.id = d.id
                    )
                )
            """)
    List<Dish> getDishes(Pageable pageable, @Param("user_id") Long userId);

    @Query("""
            SELECT d FROM Dish d WHERE d.id = :id AND NOT EXISTS (
                SELECT u FROM User u WHERE u.id != :user_id AND EXISTS (
                    SELECT cd FROM u.customDishes cd WHERE cd.id = d.id
                    )
                )
            """)
    Optional<Dish> getDishById(@Param("id") Long id, @Param("user_id") Long userId);

    @Query("""
            SELECT d FROM Dish d WHERE NOT EXISTS (
                SELECT pd FROM ProductToDish pd WHERE pd.dish.id = d.id AND pd.product.id NOT IN :product_ids
                ) AND NOT EXISTS (
                SELECT u FROM User u WHERE u.id != :user_id AND EXISTS (
                    SELECT cd FROM u.customDishes cd WHERE cd.id = d.id
                    )
                )
            """)
    List<Dish> getDishesWithALlProducts(@Param("user_id") Long userId, @Param("product_ids") List<Long> productIds);

    @Query("SELECT u.productUserHas FROM User u WHERE u.id = :user_id")
    List<Dish> getCustomDishes(Pageable pageable, @Param("user_id") Long userId);

    void deleteById(@NonNull Long id);
}
