package com.hackathon.backend.service.dish;

import com.hackathon.backend.dto.DishDto;
import com.hackathon.backend.dto.DishWithProductsDto;
import com.hackathon.backend.dto.ProductWithMeasureDto;
import com.hackathon.backend.enumeration.DishSortBy;
import com.hackathon.backend.enumeration.SortingOption;
import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.model.ProductToDish;
import com.hackathon.backend.model.User;
import com.hackathon.backend.repository.DishRepository;
import com.hackathon.backend.repository.ProductRepository;
import com.hackathon.backend.service.ProductToDishService;
import com.hackathon.backend.service.product.ProductService;
import com.hackathon.backend.service.user.UserService;
import com.hackathon.backend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final ProductRepository productRepository;
    private final ProductToDishService productToDishService;
    private final UserService userService;

    @Autowired
    public DishService(
            DishRepository dishRepository,
            ProductRepository productRepository,
            ProductToDishService productToDishService,
            UserService userService
    ) {
        this.dishRepository = dishRepository;
        this.productRepository = productRepository;
        this.productToDishService = productToDishService;
        this.userService = userService;
    }

    public List<Dish> getAllDishes(String userLogin, Integer page) {
        Long userId = userService.getUser(userLogin).getId();
        Pageable pageable = PageRequest.of(page, Constants.ITEMS_PER_PAGE);
        return dishRepository.getDishes(pageable, userId);
    }

    public DishWithProductsDto getDishInfo(String userLogin, Long id) {
        Long userId = userService.getUser(userLogin).getId();
        return dishRepository.getDishById(id, userId).map(dish -> {
            List<Product> products = productToDishService.getAllByDish(dish).stream()
                    .map(ProductToDish::getProduct)
                    .toList();
            return new DishWithProductsDto(products, dish);
        }).orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    public List<Dish> getAvailableDishes(String userLogin) {
        Long userId = userService.getUser(userLogin).getId();
        List<Long> userProductIds = productRepository.getUserProducts(userId).stream()
                .map(Product::getId)
                .toList();
        return dishRepository.getDishesWithALlProducts(userId, userProductIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public Dish insertDish(DishDto dishDto, List<ProductWithMeasureDto> productsWithMeasures) {
        Dish dish = dishRepository.save(mapToEntity(dishDto));
        List<ProductToDish> productToDishes = productsWithMeasures.stream()
                .map(productWithMeasure -> new ProductToDish()
                        .setDish(dish)
                        .setProduct(ProductService.mapToEntity(productWithMeasure.getProduct()))
                        .setMeasure(productWithMeasure.getMeasure()))
                .toList();
        productToDishService.saveAll(productToDishes);
        return dish;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDish(String userLogin, Long dishId) {
        User user = userService.getUser(userLogin);
        if(user.getCustomDishes().stream().map(Dish::getId).noneMatch(id -> Objects.equals(id, dishId))) {
            throw new RuntimeException("User's dishes not contain selected dish");
        }
        dishRepository.deleteById(dishId);
    }

    public List<Dish> getCustomDishes(
            SortingOption sortingOption,
            DishSortBy dishSortBy,
            String userLogin,
            Integer page
    ) {
        Long userId = userService.getUser(userLogin).getId();
        Sort sort = switch (sortingOption) {
            case ASCENDING -> Sort.by(dishSortBy.getSortBy()).ascending();
            case DESCENDING -> Sort.by(dishSortBy.getSortBy()).descending();
        };
        Pageable pageable = PageRequest.of(page, Constants.ITEMS_PER_PAGE, sort);
        return dishRepository.getCustomDishes(pageable, userId);
    }

    public List<Dish> saveAll(List<Dish> entities) {
        return dishRepository.saveAll(entities);
    }

    public void deleteAll(List<Dish> entities) {
        dishRepository.deleteAll(entities);
    }

    public void deleteAll() {
        dishRepository.deleteAll();
    }

    public static Dish mapToEntity(DishDto dto) {
        return new Dish()
                .setId(dto.getId())
                .setName(dto.getName())
                .setRecipe(dto.getRecipe())
                .setDifficulty(dto.getDifficulty())
                .setVotesAmount(dto.getVotesAmount())
                .setImageUrl(dto.getImageUrl());
    }

    public static DishDto mapToDto(Dish entity) {
        return new DishDto()
                .setId(entity.getId())
                .setName(entity.getName())
                .setRecipe(entity.getRecipe())
                .setDifficulty(entity.getDifficulty())
                .setVotesAmount(entity.getVotesAmount())
                .setImageUrl(entity.getImageUrl());
    }
}
