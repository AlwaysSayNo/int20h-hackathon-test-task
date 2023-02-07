package com.hackathon.backend.service;

import com.hackathon.backend.dto.ProductToDishDto;
import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.ProductToDish;
import com.hackathon.backend.repository.ProductToDishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductToDishService {

    private final ProductToDishRepository repository;

    public ProductToDishService(ProductToDishRepository repository) {
        this.repository = repository;
    }

    public List<ProductToDish> getAllByDish(Dish dish) {
        return repository.getProductToDishByDish(dish);
    }

    public List<ProductToDish> saveAll(List<ProductToDish> entities) {
        return repository.saveAll(entities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public static ProductToDish mapToEntity(ProductToDishDto dto) {
        return new ProductToDish()
                .setId(dto.getId())
                .setDish(DishService.mapToEntity(dto.getDish()))
                .setProduct(ProductService.mapToEntity(dto.getProduct()))
                .setMeasure(dto.getMeasure());
    }

}

