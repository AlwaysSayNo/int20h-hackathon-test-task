package com.hackathon.backend.service;

import com.hackathon.backend.dto.ProductToDishDto;
import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.ProductToDish;
import com.hackathon.backend.repository.ProductToDishRepository;
import com.hackathon.backend.service.dish.DishService;
import com.hackathon.backend.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductToDishService {

    private final ProductToDishRepository repository;

    public ProductToDishService(ProductToDishRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a list of {@link ProductToDish} entities based on a specified {@link Dish}.
     * @param dish the Dish entity to retrieve ProductToDish entities for.
     * @return a list of ProductToDish entities that belong to the specified Dish.
     */
    public List<ProductToDish> getAllByDish(Dish dish) {
        return repository.getProductToDishByDish(dish);
    }

    /**
     * Saves a list of {@link ProductToDish} entities to the repository.
     * @param entities the list of ProductToDish entities to save.
     * @return the list of saved ProductToDish entities.
     */
    public List<ProductToDish> saveAll(List<ProductToDish> entities) {
        return repository.saveAll(entities);
    }

    /**
     * Deletes all {@link ProductToDish} entities from the repository.
     */
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * Maps a {@link ProductToDishDto} to a {@link ProductToDish} entity.
     * @param dto the ProductToDishDto to map to a ProductToDish entity.
     * @return a ProductToDish entity created from the specified ProductToDishDto.
     */
    public static ProductToDish mapToEntity(ProductToDishDto dto) {
        return new ProductToDish()
                .setId(dto.getId())
                .setDish(DishService.mapToEntity(dto.getDish()))
                .setProduct(ProductService.mapToEntity(dto.getProduct()))
                .setMeasure(dto.getMeasure());
    }

}

