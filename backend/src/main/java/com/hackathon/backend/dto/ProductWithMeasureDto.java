package com.hackathon.backend.dto;

import com.hackathon.backend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductWithMeasureDto {

    private Product product;

    private String measure;

}
