package com.hackathon.backend.service;

import com.hackathon.backend.dto.FavoriteProductsDto;
import com.hackathon.backend.model.FavoriteProducts;
import com.hackathon.backend.repository.FavoriteProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteProductService {

    private final FavoriteProductRepository favoriteProductRepository;

    @Autowired
    public FavoriteProductService(FavoriteProductRepository favoriteProductRepository) {
        this.favoriteProductRepository = favoriteProductRepository;
    }

    public FavoriteProducts insertFavoriteProducts(FavoriteProductsDto favoriteProductsDto) {
        return favoriteProductRepository.save(mapToFavoriteProducts(favoriteProductsDto));
    }

    private FavoriteProducts mapToFavoriteProducts(FavoriteProductsDto favoriteProductsDto) {
        return new FavoriteProducts()
                .setName(favoriteProductsDto.getName())
                .setUser(favoriteProductsDto.getUser())
                .setProducts(favoriteProductsDto.getProducts());
    }
}
