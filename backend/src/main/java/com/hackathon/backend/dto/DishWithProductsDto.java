package com.hackathon.backend.dto;

import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DishWithProductsDto {
    private List<Product> products;
    private Dish dish;
}
