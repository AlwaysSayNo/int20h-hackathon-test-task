package com.hackathon.backend.dto;

import com.hackathon.backend.model.Product;
import com.hackathon.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class FavoriteProductsDto {
    private String name;

    private User user;

    private Set<Product> products;
}
