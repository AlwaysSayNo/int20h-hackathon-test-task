package com.hackathon.backend.service;

import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.DishWithProducts;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.model.ProductToDish;
import com.hackathon.backend.model.enumeration.SortingOption;
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

    Optional<DishWithProducts> getDishInfo(Long id) {
        return dishRepository.getDishById(id).map(dish -> {
            List<Product> products = productToDishRepository.getProductToDishByDish(dish).stream()
                    .map(ProductToDish::getProduct)
                    .toList();
            return new DishWithProducts(products, dish);
        });
    }

    List<Dish> getAvailableDishes(Long userId) {
        List<Long> userProductIds = productRepository.getUserProducts(userId).stream()
                .map(Product::getId)
                .toList();
        return dishRepository.getDishesWithALlProducts(userProductIds);
    }

    @Transactional
    Dish insertDish(Dish dish, List<Product> products, String measure) {
        Dish insertedDish = dishRepository.save(dish);
        List<ProductToDish> productToDishes = products.stream()
                .map(product -> new ProductToDish().setDish(insertedDish).setProduct(product).setMeasure(measure))
                .toList();
        productToDishRepository.saveAll(productToDishes);
        return dish;
    }

    List<Dish> getCustomDishes(SortingOption sortingOption, int page, Long userId) {
        Sort sort = switch (sortingOption) {
            case ASCENDING -> Sort.by("difficulty").ascending();
            case DESCENDING -> Sort.by("difficulty").descending();
        };
        // TODO: 05.02.2023 Move page size to constants or pass as argument;
        Pageable pageable = PageRequest.of(page, 10, sort);
        return dishRepository.getCustomDishes(pageable, userId);
    }
}
