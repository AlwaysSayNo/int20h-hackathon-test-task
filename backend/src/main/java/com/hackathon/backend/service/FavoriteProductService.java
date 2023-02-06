package com.hackathon.backend.service;

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

    public FavoriteProducts insertFavoriteProducts(FavoriteProducts favoriteProducts) {
        return favoriteProductRepository.save(favoriteProducts);
    }
}
