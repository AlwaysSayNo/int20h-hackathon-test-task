package com.hackathon.backend.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {
    private String name;
    private String recipe;
    private Double difficulty;
    private Integer votesAmount;
    private String imageUrl;
}
