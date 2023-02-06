package com.hackathon.backend.controller.mealapi;

import com.hackathon.backend.service.mealapi.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hackathon/api/v1/meal")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/warm-database")
    void warmDataBase() {
        apiService.warmDatabase();
    }

}
