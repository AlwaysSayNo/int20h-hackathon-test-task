package com.hackathon.backend.controller;

import com.hackathon.backend.dto.DishDto;
import com.hackathon.backend.dto.ProductDto;
import com.hackathon.backend.service.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/get-products")
    List<ProductDto> parseProductsWithPath() {
        return apiService.getAllProducts();
    }

    @GetMapping("/get-dishes")
    List<DishDto> getDishes() {
        return apiService.getAllDishes();
    }

    @GetMapping("/warm-data-base")
    void warmDataBase() {
        apiService.warmDataBase();
    }

}
