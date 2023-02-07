package com.hackathon.backend.controller.favorite;

import com.hackathon.backend.dto.favorite.FavoriteProductDto;
import com.hackathon.backend.service.favorite.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * The `FavoriteProductController` class is a RESTful web service controller that handles favorite product related requests.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/hackathon/api/v1/favorite/product")
public class FavoriteProductController {
    private final FavoriteProductService favoriteProductService;

    @Autowired
    public FavoriteProductController(FavoriteProductService favoriteProductService) {
        this.favoriteProductService = favoriteProductService;
    }

    /**
     * This method is used to handle the GET request to retrieve a list of favorite products for a user.
     *
     * @param principal the instance of `Principal` that holds the information of the currently logged-in user.
     * @return a `ResponseEntity` object containing the list of favorite products as a response to the request.
     */
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

    /**
     * This method is used to handle the GET request to retrieve a specific favorite product by its id.
     *
     * @param id the id of the favorite product to be retrieved.
     * @return a `ResponseEntity` object containing the favorite product as a response to the request.
     */
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

    /**
     * This method is used to handle the POST request to create a new favorite product.
     *
     * @param principal the instance of `Principal` that holds the information of the currently logged-in user.
     * @param dto the `FavoriteProductDto` object that contains the information of the new favorite product.
     * @return a `ResponseEntity` object containing the created favorite product as a response to the request.
     */
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

    /**
     * This method is used to handle the PUT request to update a favorite product.
     *
     * @param id the id of the favorite product to be updated.
     * @param dto the `FavoriteProductDto` object that contains the updated information of the favorite product.
     * @return a `ResponseEntity` object containing the updated favorite product as a response to the request.
     */
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

    /**
     * Delete the favorite product with the specified id.
     * Maps to the "/hackathon/api/v1/favorite/product" URI with HTTP DELETE method.
     *
     * @param id the id of the favorite product to be deleted
     * @return HTTP ResponseEntity with the status and result of the operation
     */
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