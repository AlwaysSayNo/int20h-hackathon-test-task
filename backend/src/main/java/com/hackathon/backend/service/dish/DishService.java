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

/**
 * Service class for handling operations related to dishes.
 */
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

    /**
     * Returns a list of dishes for the given user and page number.
     *
     * @param userLogin Login of the user.
     * @param page      Page number for pagination.
     * @return List of dishes for the given user and page.
     */
    public List<Dish> getAllDishes(String userLogin, Integer page) {
        Long userId = userService.getUser(userLogin).getId();
        Pageable pageable = PageRequest.of(page, Constants.ITEMS_PER_PAGE);
        return dishRepository.getDishes(pageable, userId);
    }

    /**
     * Returns information about the dish, including a list of products in the dish.
     *
     * @param userLogin Login of the user.
     * @param id        ID of the dish.
     * @return Dish information and a list of products in the dish.
     * @throws RuntimeException if the dish is not found.
     */
    public DishWithProductsDto getDishInfo(String userLogin, Long id) {
        Long userId = userService.getUser(userLogin).getId();
        return dishRepository.getDishById(id, userId).map(dish -> {
            List<Product> products = productToDishService.getAllByDish(dish).stream()
                    .map(ProductToDish::getProduct)
                    .toList();
            return new DishWithProductsDto(products, dish);
        }).orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    /**
     * Returns a list of dishes that can be made with the products the user has.
     *
     * @param userLogin Login of the user.
     * @return List of dishes that can be made with the user's products.
     */
    public List<Dish> getAvailableDishes(String userLogin) {
        Long userId = userService.getUser(userLogin).getId();
        List<Long> userProductIds = productRepository.getUserProducts(userId).stream()
                .map(Product::getId)
                .toList();
        return dishRepository.getDishesWithALlProducts(userId, userProductIds);
    }

    /**
     * Creates a dish and related product to dish relationships.
     *
     * @param dishDto             Dish data transfer object.
     * @param productsWithMeasures List of product with measure data transfer objects.
     * @return Created dish entity.
     * @throws Exception When any error occurs.
     */
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

    /**
     * Deletes a Dish entity with the given id for a user specified by userLogin.
     *
     * @param userLogin The login of the user for whom the Dish entity should be deleted.
     * @param dishId The id of the Dish entity to be deleted.
     *
     * @throws RuntimeException If the user's dishes don't contain the selected dish.
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDish(String userLogin, Long dishId) {
        User user = userService.getUser(userLogin);
        if(user.getCustomDishes().stream().map(Dish::getId).noneMatch(id -> Objects.equals(id, dishId))) {
            throw new RuntimeException("User's dishes not contain selected dish");
        }
        dishRepository.deleteById(dishId);
    }

    /**
     * Retrieves a list of custom dishes for a user specified by userLogin.
     *
     * @param sortingOption The option to sort the dishes (ASCENDING or DESCENDING).
     * @param dishSortBy The criteria to sort the dishes by.
     * @param userLogin The login of the user for whom the custom dishes should be retrieved.
     * @param page The page number of the custom dishes to be retrieved.
     *
     * @return A list of custom Dish entities for the user.
     */
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

    /**
     * Saves a list of Dish entities.
     *
     * @param entities The list of Dish entities to be saved.
     *
     * @return A list of saved Dish entities.
     */
    public List<Dish> saveAll(List<Dish> entities) {
        return dishRepository.saveAll(entities);
    }

    /**
     * Deletes a list of Dish entities.
     *
     * @param entities The list of Dish entities to be deleted.
     */
    public void deleteAll(List<Dish> entities) {
        dishRepository.deleteAll(entities);
    }

    /**
     * Deletes all Dish entities.
     */
    public void deleteAll() {
        dishRepository.deleteAll();
    }

    /**
    * Maps a DishDto object to a Dish object.
     *
    * @param dto DishDto object to be mapped.
    * @return Dish object that is mapped from the input DishDto object.
    */
    public static Dish mapToEntity(DishDto dto) {
        return new Dish()
                .setId(dto.getId())
                .setName(dto.getName())
                .setRecipe(dto.getRecipe())
                .setDifficulty(dto.getDifficulty())
                .setVotesAmount(dto.getVotesAmount())
                .setImageUrl(dto.getImageUrl());
    }

    /**
     * Maps a Dish object to a DishDto object.
     *
     * @param entity Dish object to be mapped.
     * @return DishDto object that is mapped from the input Dish object.
     */
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
