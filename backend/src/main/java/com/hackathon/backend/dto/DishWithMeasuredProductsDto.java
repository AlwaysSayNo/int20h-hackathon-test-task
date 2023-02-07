package com.hackathon.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DishWithMeasuredProductsDto {

    private DishDto dish;

    private List<ProductWithMeasureDto> productsWithMeasure;

}
