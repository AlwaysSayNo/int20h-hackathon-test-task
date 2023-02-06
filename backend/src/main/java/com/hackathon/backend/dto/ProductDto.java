package com.hackathon.backend.dto;

import com.hackathon.backend.enumeration.ProductCategory;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

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