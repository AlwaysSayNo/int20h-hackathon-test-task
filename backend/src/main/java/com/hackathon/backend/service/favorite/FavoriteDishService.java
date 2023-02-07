package com.hackathon.backend.service.favorite;

import com.hackathon.backend.dto.favorite.FavoriteDishDto;
import com.hackathon.backend.dto.favorite.FavoriteDishViewDto;
import com.hackathon.backend.model.FavoriteDishes;
import com.hackathon.backend.repository.DishRepository;
import com.hackathon.backend.repository.FavoriteDishRepository;
import com.hackathon.backend.service.dish.DishService;
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

    /**
     * Create a favorite dish collection for a user.
     *
     * @param userLogin the login of the user
     * @param dto the data transfer object representing the favorite dish collection
     * @return the updated data transfer object, including the generated id of the favorite dish collection
     */
    @Transactional(rollbackFor = Exception.class)
    public FavoriteDishDto createFavoriteDish(String userLogin, FavoriteDishDto dto) {
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

    /**
     * Updates the favorite dish collection with the provided information.
     *
     * @param id Long identifier of the favorite dish collection.
     * @param dto FavoriteDishDto object with updated information for the favorite dish collection.
     * @return FavoriteDishDto object with the updated information.
     * @throws RuntimeException if the favorite dish collection with the provided identifier is not found.
     */
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

    /**
     * Deletes the favorite dish collection with the provided identifier.
     *
     * @param id Long identifier of the favorite dish collection.
     * @throws RuntimeException if the favorite dish collection with the provided identifier is not found.
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteFavoriteDish(Long id) {
        favoriteDishRepository.deleteById(id);
    }

    /**
     * Retrieve a favorite dish by its id.
     *
     * @param id The id of the favorite dish to retrieve.
     * @return The FavoriteDishViewDto object representing the retrieved favorite dish.
     * @throws RuntimeException if the favorite dish is not found.
     */
    public FavoriteDishViewDto getFavoriteDish(Long id) {
        var favoriteCollection = favoriteDishRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite dish not found"));
        return mapToViewDto(favoriteCollection);
    }

    /**
     * Retrieve all label-value pairs of favorite dishes for a user.
     *
     * @param userLogin The login of the user.
     * @return A list of LabelValue objects representing the favorite dishes of the user.
     */
    public List<LabelValue> getAllLabelValueFavoriteDishesForUser(String userLogin) {
        var user = userService.getUser(userLogin);
        return favoriteDishRepository.getAllLabelValueFavoriteDishForUser(user.getId());
    }

    /**
     * Map a FavoriteDishes object to a FavoriteDishViewDto object.
     *
     * @param favoriteDishes The FavoriteDishes object to map.
     * @return The FavoriteDishViewDto object representing the mapped favorite dishes.
     */
    private FavoriteDishViewDto mapToViewDto(FavoriteDishes favoriteDishes) {
        var viewDto = new FavoriteDishViewDto();
        viewDto.setId(favoriteDishes.getId());
        viewDto.setName(favoriteDishes.getName());

        var dishDtoList = favoriteDishes.getDishes().stream()
                .map(DishService::mapToDto)
                .toList();
        viewDto.setDishes(dishDtoList);
        return viewDto;
    }
}
