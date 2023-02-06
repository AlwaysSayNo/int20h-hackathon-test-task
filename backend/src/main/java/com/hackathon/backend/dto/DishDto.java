package com.hackathon.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DishDto {
    private String name;
    private String recipe;
    private Double difficulty;
    private Integer votesAmount;
    private String imageUrl;
}
