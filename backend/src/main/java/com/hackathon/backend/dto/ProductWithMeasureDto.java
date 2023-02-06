package com.hackathon.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductWithMeasureDto {

    private ProductDto productDto;

    private String measure;

}
