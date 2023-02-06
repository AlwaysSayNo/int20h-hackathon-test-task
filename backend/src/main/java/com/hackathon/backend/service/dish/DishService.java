package com.hackathon.backend.service.dish;

import com.hackathon.backend.dto.DishDto;
import com.hackathon.backend.dto.DishWithProductsDto;
import com.hackathon.backend.dto.ProductDto;
import com.hackathon.backend.dto.ProductWithMeasureDto;
import com.hackathon.backend.enumeration.DishSortBy;
import com.hackathon.backend.enumeration.SortingOption;
import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.model.ProductToDish;
import com.hackathon.backend.model.User;
import com.hackathon.backend.repository.DishRepository;
import com.hackathon.backend.repository.ProductRepository;
import com.hackathon.backend.repository.ProductToDishRepository;
import com.hackathon.backend.service.user.UserService;
import com.hackathon.backend.util.Constants;
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

    private final ProductToDishRepository productToDishRepository;

    private final UserService userService;

    public DishService(
            DishRepository dishRepository,
            ProductRepository productRepository,
            ProductToDishRepository productToDishRepository,
            UserService userService
    ) {
        this.dishRepository = dishRepository;
        this.productRepository = productRepository;
        this.productToDishRepository = productToDishRepository;
        this.userService = userService;
    }

    public List<Dish> getAllDishes(Integer page) {
        Pageable pageable = PageRequest.of(page, Constants.ITEMS_PER_PAGE);
        return dishRepository.getDishes(pageable);
    }

    public DishWithProductsDto getDishInfo(Long id) {
        return dishRepository.getDishById(id).map(dish -> {
            List<Product> products = productToDishRepository.getProductToDishByDish(dish).stream()
                    .map(ProductToDish::getProduct)
                    .toList();
            return new DishWithProductsDto(products, dish);
        }).orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    public List<Dish> getAvailableDishes(String userLogin) throws Exception {
        Long userId = userService.getUser(userLogin).getId();
        List<Long> userProductIds = productRepository.getUserProducts(userId).stream()
                .map(Product::getId)
                .toList();
        return dishRepository.getDishesWithALlProducts(userProductIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public Dish insertDish(DishDto dishDto, List<ProductWithMeasureDto> productsWithMeasures) {
        Dish dish = dishRepository.save(mapToDish(dishDto));
        List<ProductToDish> productToDishes = productsWithMeasures.stream()
                .map(productWithMeasure -> new ProductToDish()
                        .setDish(dish)
                        .setProduct(mapToProduct(productWithMeasure.getProductDto()))
                        .setMeasure(productWithMeasure.getMeasure()))
                .toList();
        productToDishRepository.saveAll(productToDishes);
        return dish;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDish(String userLogin, Long dishId) throws Exception {
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
    ) throws Exception {
        Long userId = userService.getUser(userLogin).getId();
        Sort sort = switch (sortingOption) {
            case ASCENDING -> Sort.by(dishSortBy.getSortBy()).ascending();
            case DESCENDING -> Sort.by(dishSortBy.getSortBy()).descending();
        };
        Pageable pageable = PageRequest.of(page, Constants.ITEMS_PER_PAGE, sort);
        return dishRepository.getCustomDishes(pageable, userId);
    }

    private Product mapToProduct(ProductDto productDto) {
        return new Product()
                .setName(productDto.getName())
                .setCategory(productDto.getCategory())
                .setImageUrl(productDto.getImageUrl());
    }

    private Dish mapToDish(DishDto dishDto) {
        return new Dish()
                .setName(dishDto.getName())
                .setRecipe(dishDto.getRecipe())
                .setDifficulty(dishDto.getDifficulty())
                .setVotesAmount(dishDto.getVotesAmount())
                .setImageUrl(dishDto.getImageUrl());
    }

    public DishDto mapToDishDto(Dish dish) {
        return new DishDto()
                .setName(dish.getName())
                .setRecipe(dish.getRecipe())
                .setDifficulty(dish.getDifficulty())
                .setVotesAmount(dish.getVotesAmount())
                .setImageUrl(dish.getImageUrl());
    }
}
