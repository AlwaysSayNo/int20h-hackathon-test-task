package com.hackathon.backend.service;

import com.hackathon.backend.dto.DishDto;
import com.hackathon.backend.dto.DishWithProductsDto;
import com.hackathon.backend.dto.ProductWithMeasureDto;
import com.hackathon.backend.enumeration.DishSortBy;
import com.hackathon.backend.enumeration.SortingOption;
import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.model.ProductToDish;
import com.hackathon.backend.repository.DishRepository;
import com.hackathon.backend.repository.ProductRepository;
import com.hackathon.backend.repository.ProductToDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private final DishRepository dishRepository;

    private final ProductRepository productRepository;

    private final ProductToDishRepository productToDishRepository;

    @Autowired
    public DishService(
            DishRepository dishRepository,
            ProductRepository productRepository,
            ProductToDishRepository productToDishRepository
    ) {
        this.dishRepository = dishRepository;
        this.productRepository = productRepository;
        this.productToDishRepository = productToDishRepository;
    }

    public List<Dish> getAllDishes(int page) {
        // TODO: 05.02.2023 Move page size to constants or pass as argument
        Pageable pageable = PageRequest.of(page, 10);
        return dishRepository.getDishes(pageable);
    }

    public Optional<DishWithProductsDto> getDishInfo(Long id) {
        return dishRepository.getDishById(id).map(dish -> {
            List<Product> products = productToDishRepository.getProductToDishByDish(dish).stream()
                    .map(ProductToDish::getProduct)
                    .toList();
            return new DishWithProductsDto(products, dish);
        });
    }

    public List<Dish> getAvailableDishes(Long userId) {
        List<Long> userProductIds = productRepository.getUserProducts(userId).stream()
                .map(Product::getId)
                .toList();
        return dishRepository.getDishesWithALlProducts(userProductIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public Dish insertDish(DishDto dishDto, List<ProductWithMeasureDto> productsWithMeasures) {
        Dish dish = dishRepository.save(mapToEntity(dishDto));
        List<ProductToDish> productToDishes = productsWithMeasures.stream()
                .map(productWithMeasure -> new ProductToDish()
                        .setDish(dish)
                        .setProduct(ProductService.mapToEntity(productWithMeasure.getProductDto()))
                        .setMeasure(productWithMeasure.getMeasure()))
                .toList();
        productToDishRepository.saveAll(productToDishes);
        return dish;
    }

    public List<Dish> getCustomDishes(SortingOption sortingOption, DishSortBy dishSortBy, int page, Long userId) {
        Sort sort = switch (sortingOption) {
            case ASCENDING -> Sort.by(dishSortBy.getSortBy()).ascending();
            case DESCENDING -> Sort.by(dishSortBy.getSortBy()).descending();
        };
        // TODO: 05.02.2023 Move page size to constants or pass as argument;
        Pageable pageable = PageRequest.of(page, 10, sort);
        return dishRepository.getCustomDishes(pageable, userId);
    }

    private static Dish mapToEntity(DishDto dishDto) {
        return new Dish()
                .setName(dishDto.getName())
                .setRecipe(dishDto.getRecipe())
                .setDifficulty(dishDto.getDifficulty())
                .setVotesAmount(dishDto.getVotesAmount())
                .setImageUrl(dishDto.getImageUrl());
    }

    public static DishDto mapToDto(Dish entity) {
        return new DishDto()
                .setName(entity.getName())
                .setRecipe(entity.getRecipe())
                .setDifficulty(entity.getDifficulty())
                .setVotesAmount(entity.getVotesAmount())
                .setImageUrl(entity.getImageUrl());
    }
}
