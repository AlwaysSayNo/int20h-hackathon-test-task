package com.hackathon.backend.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
