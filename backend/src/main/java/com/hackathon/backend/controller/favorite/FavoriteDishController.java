package com.hackathon.backend.controller.favorite;

import com.hackathon.backend.dto.favorite.FavoriteDishDto;
import com.hackathon.backend.service.favorite.FavoriteDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * The `FavoriteDishController` class is a REST controller responsible for handling requests to the /hackathon/api/v1/favorite/dish endpoint.
 * It uses the `FavoriteDishService` to perform the business logic and manage the data.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/hackathon/api/v1/favorite/dish")
public class FavoriteDishController {
    private final FavoriteDishService favoriteDishService;

    @Autowired
    public FavoriteDishController(FavoriteDishService favoriteDishService) {
        this.favoriteDishService = favoriteDishService;
    }

    /**
     * Handles GET requests to the /hackathon/api/v1/favorite/dish/list endpoint and returns a list of label-value pairs of all favorite dishes for a user.
     *
     * @param principal the principal representing the current user
     * @return a `ResponseEntity` containing the list of label-value pairs or a bad request response with an error message
     */
    @GetMapping("/list")
    public ResponseEntity<?> getLabelValueFavoriteDishList(Principal principal) {
        try {
            var labelValueList = favoriteDishService.getAllLabelValueFavoriteDishesForUser(principal.getName());
            return ResponseEntity.ok(labelValueList);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Handles GET requests to the /hackathon/api/v1/favorite/dish endpoint and returns the favorite dish with the given ID.
     *
     * @param id the ID of the favorite dish to retrieve
     * @return a `ResponseEntity` containing the favorite dish or a bad request response with an error message
     */
    @GetMapping
    public ResponseEntity<?> getFavoriteDish(@RequestParam Long id) {
        try {
            var favoriteDish = favoriteDishService.getFavoriteDish(id);
            return ResponseEntity.ok(favoriteDish);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Handles POST requests to the /hackathon/api/v1/favorite/dish endpoint and creates a new favorite dish for the current user.
     *
     * @param principal the principal representing the current user
     * @param dto the `FavoriteDishDto` containing the data for the new favorite dish
     * @return a `ResponseEntity` containing the created favorite dish or a bad request response with an error message
     */
    @PostMapping
    public ResponseEntity<?> createFavoriteDish(Principal principal, @RequestBody FavoriteDishDto dto) {
        try {
            var createdFavoriteDishCollection = favoriteDishService
                    .createFavoriteDish(principal.getName(), dto);
            return ResponseEntity.ok(createdFavoriteDishCollection);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Updates a favorite dish entity.
     *
     * @param id the ID of the favorite dish to update
     * @param dto the favorite dish data transfer object
     * @return a response entity with the updated favorite dish collection, if successful
     * @throws Exception if the favorite dish update fails
     */
    @PutMapping
    public ResponseEntity<?> updateFavoriteDish(@RequestParam Long id, @RequestBody FavoriteDishDto dto) {
        try {
            var updatedFavoriteDishCollection = favoriteDishService.updateFavoriteDish(id, dto);
            return ResponseEntity.ok(updatedFavoriteDishCollection);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Deletes a favorite dish entity.
     *
     * @param id the ID of the favorite dish to delete
     * @return a response entity with a success message, if the favorite dish deletion was successful
     * @throws Exception if the favorite dish deletion fails
     */
    @DeleteMapping
    public ResponseEntity<?> deleteFavoriteDish(@RequestParam Long id) {
        try {
            favoriteDishService.deleteFavoriteDish(id);
            return ResponseEntity.ok("Dish was deleted");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
