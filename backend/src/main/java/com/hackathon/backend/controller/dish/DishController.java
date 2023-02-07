package com.hackathon.backend.controller.dish;

import com.hackathon.backend.dto.DishWithMeasuredProductsDto;
import com.hackathon.backend.dto.DishWithProductsDto;
import com.hackathon.backend.dto.dish.difficulty.GetDishDifficultyDto;
import com.hackathon.backend.dto.dish.difficulty.PutDishDifficultyDto;
import com.hackathon.backend.enumeration.DishSortBy;
import com.hackathon.backend.enumeration.SortingOption;
import com.hackathon.backend.model.Dish;
import com.hackathon.backend.service.dish.DishDifficultyService;
import com.hackathon.backend.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * DishController class represents the endpoints for the Dish management REST API.
 * This class uses DishService and DishDifficultyService for handling the business logic.
 */
@RestController
@RequestMapping("/hackathon/api/v1/dish")
public class DishController {

    private final DishService dishService;
    private final DishDifficultyService difficultyService;

    @Autowired
    public DishController(DishService dishService, DishDifficultyService difficultyService) {
        this.dishService = dishService;
        this.difficultyService = difficultyService;
    }

    /**
     * Endpoint to retrieve all dishes.
     * It maps requests with HTTP GET method under "/hackathon/api/v1/dish
     *
     * @param principal the instance of Principal
     * @param page the page number for pagination
     * @return ResponseEntity with HTTP status code 200 and the list of dishes in the body,
     *         or with HTTP status code 400 and error message in the body if there is any exception
     */
    @GetMapping
    public ResponseEntity<?> getAllDishes(Principal principal, @RequestParam("page") Integer page) {
        try {
            List<Dish> dishes = dishService.getAllDishes(principal.getName(), page);
            return ResponseEntity.ok(dishes);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Endpoint to retrieve the information of a specific dish.
     * It maps requests with HTTP GET method under "/hackathon/api/v1/dish/info".
     *
     * @param principal the instance of Principal
     * @param id the id of the dish to retrieve information for
     * @return ResponseEntity with HTTP status code 200 and the DishWithProductsDto object in the body,
     *         or with HTTP status code 400 and error message in the body if there is any exception
     */
    @GetMapping("/info")
    public ResponseEntity<?> getDishInfo(Principal principal, @RequestParam("id") Long id) {
        try {
            DishWithProductsDto dishWithProducts = dishService.getDishInfo(principal.getName(), id);
            return ResponseEntity.ok(dishWithProducts);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Endpoint to retrieve all available dishes.
     * It maps requests with HTTP GET method under "/hackathon/api/v1/dish/available".
     *
     * @param principal the instance of Principal
     * @return ResponseEntity with HTTP status code 200 and the list of available dishes in the body,
     *         or with HTTP status code 400 and error message in the body if there is any exception
     */
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

    /**
     * insertDish method is used to insert a dish along with its measured products.
     * It maps requests with HTTP POST method under "/hackathon/api/v1/dish".
     *
     * @param dishWithMeasuredProductsDto the DishWithMeasuredProductsDto object containing dish information and its measured products
     * @return ResponseEntity<?> containing the inserted dish if successful, otherwise a bad request with error message.
     */
    @PostMapping
    public ResponseEntity<?> insertDish(@RequestBody DishWithMeasuredProductsDto dishWithMeasuredProductsDto) {
        try {
            Dish dish = dishService.insertDish(
                    dishWithMeasuredProductsDto.getDish(),
                    dishWithMeasuredProductsDto.getProductsWithMeasure()
            );
            return ResponseEntity.ok(dish);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * deleteDish method is used to delete a dish.
     * It maps requests with HTTP DELETE method under "/hackathon/api/v1/dish".
     *
     * @param principal the principal object containing user information
     * @param dishId the ID of the dish to be deleted
     * @return ResponseEntity<?> containing a message "Dish deleted successfully" if successful, otherwise a bad request with error message.
     */
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

    /**
     * getCustomDishes method is used to get the custom dishes of a user.
     * It maps requests with HTTP GET method under "/hackathon/api/v1/dish/custom".
     *
     * @param principal the principal object containing user information
     * @param sortingOption the sorting option to sort the custom dishes (options available in SortingOption enum)
     * @param page the page number to retrieve custom dishes
     * @return ResponseEntity<?> containing the list of custom dishes if successful, otherwise a bad request with error message.
     */
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

    /**
     * Endpoint to set the difficulty of a dish for a user.
     * It maps requests with HTTP POST method under "/hackathon/api/v1/dish/difficulty".
     *
     * @param principal  the authenticated user's principal
     * @param dto        the PutDishDifficultyDto object containing dishId and difficulty
     * @return           a ResponseEntity with the difficulty set for the user, or a bad request with the error message
     */
    @PostMapping("/difficulty")
    public ResponseEntity<?> setDifficulty(Principal principal, @RequestBody PutDishDifficultyDto dto) {
        try {
            var difficulty = difficultyService.setDifficultyForUser(principal.getName(), dto);
            return ResponseEntity.ok(difficulty);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Endpoint to get the difficulty of a dish for a user.
     * It maps requests with HTTP GET method under "/hackathon/api/v1/dish/difficulty".
     *
     * @param principal  the authenticated user's principal
     * @param dto        the GetDishDifficultyDto object containing dishId
     * @return           a ResponseEntity with the difficulty for the user, or a bad request with the error message
     */
    @GetMapping("/difficulty")
    public ResponseEntity<?> getDifficulty(Principal principal, @RequestBody GetDishDifficultyDto dto) {
        try {
            var difficulty = difficultyService.getDifficultyForUser(principal.getName(), dto);
            return ResponseEntity.ok(difficulty);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
