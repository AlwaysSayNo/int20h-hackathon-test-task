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
public class ProductToDishDto {
    private Long id;
    private DishDto dish;
    private ProductDto product;
    private String measure;
}
