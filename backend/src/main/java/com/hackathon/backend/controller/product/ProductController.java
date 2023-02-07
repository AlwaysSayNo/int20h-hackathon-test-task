package com.hackathon.backend.controller.product;

import com.hackathon.backend.enumeration.ProductCategory;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/hackathon/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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
