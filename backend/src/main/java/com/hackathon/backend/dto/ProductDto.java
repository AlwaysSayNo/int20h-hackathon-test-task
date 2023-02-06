package com.hackathon.backend.dto;

import com.hackathon.backend.model.enumeration.Category;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;

    private Category category;

    private String imageUrl;
}
