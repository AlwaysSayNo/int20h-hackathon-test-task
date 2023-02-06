package com.hackathon.backend.dto;

import com.hackathon.backend.enumeration.ProductCategory;
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
public class ProductDto {
    private Long id;
    private String name;
    private ProductCategory category;
    private String imageUrl;
}