package com.hackathon.backend.controller.product;

import com.hackathon.backend.enumeration.ProductCategory;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The `ProductController` class is a RESTful web service controller for handling product related operations.
 * It maps all incoming requests under the "/hackathon/api/v1/product" URL.
 */
@RestController
@RequestMapping("/hackathon/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Handles HTTP GET requests for retrieving the products for the authenticated user.
     *
     * @param principal the authenticated user
     * @return a `ResponseEntity` object containing the products for the authenticated user
     */
    @GetMapping
    public ResponseEntity<?> getUserProducts(Principal principal) {
        try {
            Set<Product> userProducts = productService.getUserProducts(principal.getName());
            return ResponseEntity.ok(userProducts);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests for retrieving the products grouped by their categories.
     *
     * @return a `ResponseEntity` object containing a map of product categories to their respective products
     */
    @GetMapping("/group-by-categories")
    public ResponseEntity<?> getProductsByCategories() {
        try {
            Map<ProductCategory, List<Product>> groupedProducts = productService.getProductsByCategories();
            return ResponseEntity.ok(groupedProducts);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests for retrieving the product categories.
     *
     * @return a `ResponseEntity` object containing the product categories
     */
    @GetMapping("/categories")
    public ResponseEntity<?> getProductCategories() {
        try {
            List<ProductCategory> categories = productService.getProductCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests for retrieving the products for a specific category and page.
     *
     * @param category the product category
     * @param page the page number
     * @return a `ResponseEntity` object containing the products for the specified category and page
     */
    @GetMapping("/products-by-category")
    public ResponseEntity<?> getProductsByCategory(
            @RequestParam("category") String category,
            @RequestParam("page") Integer page
    ) {
        try {
            List<Product> products = productService
                    .getProductsByCategory(ProductCategory.valueOf(category.toUpperCase()), page);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
