package com.hackathon.backend.controller;

import com.hackathon.backend.dto.dish.difficulty.GetDishDifficultyDto;
import com.hackathon.backend.dto.dish.difficulty.PutDishDifficultyDto;
import com.hackathon.backend.service.DishDifficultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hackathon/api/v1/dish")
public class DishController {

    private final DishDifficultyService difficultyService;

    public DishController(DishDifficultyService difficultyService) {
        this.difficultyService = difficultyService;
    }

    @PostMapping("/difficulty")
    public ResponseEntity<?> setDifficulty(@RequestBody PutDishDifficultyDto dto) {
        try {
            var difficulty = difficultyService.setDifficulty(dto);
            return ResponseEntity.ok(difficulty);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/difficulty")
    public ResponseEntity<?> getDifficulty(@RequestBody GetDishDifficultyDto dto) {
        try {
            var difficulty = difficultyService.getDifficulty(dto);
            return ResponseEntity.ok(difficulty);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

}
