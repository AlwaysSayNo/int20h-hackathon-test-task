package com.hackathon.backend.service.favorite;

import com.hackathon.backend.dto.favorite.FavoriteDishDto;
import com.hackathon.backend.dto.favorite.FavoriteDishViewDto;
import com.hackathon.backend.model.FavoriteDishes;
import com.hackathon.backend.repository.DishRepository;
import com.hackathon.backend.repository.FavoriteDishRepository;
import com.hackathon.backend.service.user.UserService;
import com.hackathon.backend.util.LabelValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class FavoriteDishService {
    private final UserService userService;
    private final FavoriteDishRepository favoriteDishRepository;
    private final DishRepository dishRepository;

    @Autowired
    public FavoriteDishService(UserService userService, FavoriteDishRepository favoriteDishRepository,
                               DishRepository dishRepository) {
        this.userService = userService;
        this.favoriteDishRepository = favoriteDishRepository;
        this.dishRepository = dishRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public FavoriteDishDto createFavoriteDish(String userLogin, FavoriteDishDto dto) throws Exception {
        var user = userService.getUser(userLogin);
        var favoriteDishes = dishRepository.findAllById(dto.getDishIds());

        var favoriteCollection = new FavoriteDishes();
        favoriteCollection.setName(dto.getName());
        favoriteCollection.setUser(user);
        favoriteCollection.setDishes(new HashSet<>(favoriteDishes));

        var saved = favoriteDishRepository.save(favoriteCollection);
        dto.setId(saved.getId());
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public FavoriteDishDto updateFavoriteDish(Long id, FavoriteDishDto dto) {
        var favoriteCollection = favoriteDishRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite dish not found"));

        var favoriteDishes = dishRepository.findAllById(dto.getDishIds());
        favoriteCollection.setName(dto.getName());
        favoriteCollection.setDishes(new HashSet<>(favoriteDishes));
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteFavoriteDish(Long id) {
        favoriteDishRepository.deleteById(id);
    }

    public FavoriteDishViewDto getFavoriteDish(Long id) {
        var favoriteCollection = favoriteDishRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite dish not found"));
        return mapToViewDto(favoriteCollection);
    }

    public List<LabelValue> getAllLabelValueFavoriteDishesForUser(String userLogin) throws Exception {
        var user = userService.getUser(userLogin);
        return favoriteDishRepository.getAllLabelValueFavoriteDishForUser(user.getId());
    }

    private FavoriteDishViewDto mapToViewDto(FavoriteDishes favoriteDishes) {
        var viewDto = new FavoriteDishViewDto();
        viewDto.setId(favoriteDishes.getId());
        viewDto.setName(favoriteDishes.getName());
//        TODO add using dishService map
//        viewDto.setDishList(favoriteDishes.getDishes());
        return viewDto;
    }
}






















