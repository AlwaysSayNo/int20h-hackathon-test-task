package com.hackathon.backend.controller.favorite;

import com.hackathon.backend.dto.favorite.FavoriteDishDto;
import com.hackathon.backend.service.favorite.FavoriteDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/hackathon/api/v1/favorite/dish")
public class FavoriteDishController {
    private final FavoriteDishService favoriteDishService;

    @Autowired
    public FavoriteDishController(FavoriteDishService favoriteDishService) {
        this.favoriteDishService = favoriteDishService;
    }

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
