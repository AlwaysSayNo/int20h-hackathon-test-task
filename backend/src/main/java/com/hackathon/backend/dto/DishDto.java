package com.hackathon.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DishDto {

    private Long id;
    private String name;
    private String recipe;
    private Double difficulty;
    private Integer votesAmount;
    private String imageUrl;

}