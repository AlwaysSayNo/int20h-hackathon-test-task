package com.hackathon.backend.service;

import com.hackathon.backend.dto.FavoriteDishesDto;
import com.hackathon.backend.model.FavoriteDishes;
import com.hackathon.backend.repository.FavoriteDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteDishService {

    private final FavoriteDishRepository favoriteDishRepository;

    @Autowired
    public FavoriteDishService(FavoriteDishRepository favoriteDishRepository) {
        this.favoriteDishRepository = favoriteDishRepository;
    }

    public FavoriteDishes insertFavoriteDishes(FavoriteDishesDto favoriteDishesDto) {
        return favoriteDishRepository.save(FavoriteDishes.fromFavoriteDishesDto(favoriteDishesDto));
    }
}
