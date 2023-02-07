package com.hackathon.backend.controller.dish;

import com.hackathon.backend.dto.DishWithMeasuredProductsDto;
import com.hackathon.backend.dto.DishWithProductsDto;
import com.hackathon.backend.enumeration.DishSortBy;
import com.hackathon.backend.enumeration.SortingOption;
import com.hackathon.backend.model.Dish;
import com.hackathon.backend.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/hackathon/api/v1/dish")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<?> getAllDishes(@RequestParam("page") Integer page) {
        try {
            List<Dish> dishes = dishService.getAllDishes(page);
            return ResponseEntity.ok(dishes);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getDishInfo(@RequestParam("id") Long id) {
        try {
            DishWithProductsDto dishWithProducts = dishService.getDishInfo(id);
            return ResponseEntity.ok(dishWithProducts);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableDishes(Principal principal) {
        try {
            List<Dish> availableDishes = dishService.getAvailableDishes(principal.getName());
            return ResponseEntity.ok(availableDishes);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertDish(@RequestBody DishWithMeasuredProductsDto dishWithMeasuredProductsDto) {
        try {
            Dish dish = dishService.insertDish(
                    dishWithMeasuredProductsDto.getDishDto(),
                    dishWithMeasuredProductsDto.getProductWithMeasureDtoList()
            );
            return ResponseEntity.ok(dish);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDish(Principal principal, @RequestParam("dish_id") Long dishId) {
        try {
            dishService.deleteDish(principal.getName(), dishId);
            return ResponseEntity.ok("Dish deleted successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/custom")
    public ResponseEntity<?> getCustomDishes(
            Principal principal,
            @RequestParam("sorting_option") String sortingOption,
            @RequestParam("page") Integer page
    ) {
        try {
            List<Dish> customDishes = dishService.getCustomDishes(
                    SortingOption.valueOf(sortingOption.toUpperCase()),
                    DishSortBy.DIFFICULTY,
                    principal.getName(),
                    page
            );
            return ResponseEntity.ok(customDishes);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
