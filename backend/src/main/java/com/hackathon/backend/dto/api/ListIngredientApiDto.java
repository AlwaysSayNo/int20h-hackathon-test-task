package com.hackathon.backend.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListIngredientApiDto(List<IngredientApiDto> meals) {
}
