package com.hackathon.backend.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IngredientApiDto(String strIngredient) {
}
