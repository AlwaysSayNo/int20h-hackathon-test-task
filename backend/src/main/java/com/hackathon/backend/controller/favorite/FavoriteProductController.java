package com.hackathon.backend.controller.favorite;

import com.hackathon.backend.dto.favorite.FavoriteProductDto;
import com.hackathon.backend.service.favorite.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/hackathon/api/v1/favorite/product")
public class FavoriteProductController {
    private final FavoriteProductService favoriteProductService;

    @Autowired
    public FavoriteProductController(FavoriteProductService favoriteProductService) {
        this.favoriteProductService = favoriteProductService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getLabelValueFavoriteProductList(Principal principal) {
        try {
            var labelValueList = favoriteProductService.getAllLabelValueFavoriteProductsForUser(principal.getName());
            return ResponseEntity.ok(labelValueList);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getFavoriteProduct(@RequestParam Long id) {
        try {
            var favoriteProduct = favoriteProductService.getFavoriteProduct(id);
            return ResponseEntity.ok(favoriteProduct);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createFavoriteProduct(Principal principal, @RequestBody FavoriteProductDto dto) {
        try {
            var createdFavoriteProductCollection = favoriteProductService
                    .createFavoriteProduct(principal.getName(), dto);
            return ResponseEntity.ok(createdFavoriteProductCollection);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateFavoriteProduct(@RequestParam Long id, @RequestBody FavoriteProductDto dto) {
        try {
            var updatedFavoriteProductCollection = favoriteProductService.updateFavoriteProduct(id, dto);
            return ResponseEntity.ok(updatedFavoriteProductCollection);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFavoriteProduct(@RequestParam Long id) {
        try {
            favoriteProductService.deleteFavoriteProduct(id);
            return ResponseEntity.ok("Product was deleted");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}