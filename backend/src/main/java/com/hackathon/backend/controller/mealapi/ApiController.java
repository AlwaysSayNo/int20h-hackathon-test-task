package com.hackathon.backend.controller.mealapi;

import com.hackathon.backend.service.mealapi.ApiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The `ApiController` class maps HTTP requests to RESTful endpoints.
 * It is responsible for handling all requests related to the /hackathon/api/v1/meal endpoint.
 */
@RestController
@RequestMapping("/hackathon/api/v1/meal")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * This method maps an HTTP `POST` request to the `/warm-database` endpoint.
     * It warms up the database by calling the `warmDatabase()` method of the `ApiService` object.
     */
    @PostMapping("/warm-database")
    void warmDataBase() {
        apiService.warmDatabase();
    }

}
