package com.hackathon.backend.dto;

import com.hackathon.backend.model.enumeration.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    private String name;

    private Category category;

    private String imageUrl;
}
