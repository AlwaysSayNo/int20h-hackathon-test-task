package com.hackathon.backend.service.mealapi;

import com.hackathon.backend.dto.DishDto;
import com.hackathon.backend.dto.ProductDto;
import com.hackathon.backend.dto.api.ListIngredientApiDto;
import com.hackathon.backend.dto.api.ListMealApiDto;
import com.hackathon.backend.dto.api.MealApiDto;
import com.hackathon.backend.enumeration.MealApi;
import com.hackathon.backend.enumeration.ProductCategory;
import com.hackathon.backend.enumeration.Template;
import com.hackathon.backend.model.Dish;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.model.ProductToDish;
import com.hackathon.backend.service.ProductToDishService;
import com.hackathon.backend.service.dish.DishService;
import com.hackathon.backend.service.product.ProductService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApiService {

    private final Map<ProductCategory, String> productCategoryData;
    private final RestTemplate restTemplate;
    private final DishService dishService;
    private final ProductService productService;
    private final ProductToDishService productToDishService;

    @Value("${meal_api.meal.start_id}")
    private Integer MEAL_START_ID;
    @Value("${meal_api.meal.end_id}")
    private Integer MEAL_END_ID;
    @Value("${meal_api.ingredient.fields_amount}")
    private Integer INGREDIENT_FIELDS_AMOUNT;
    @Value("${meal_api.meal.pulled_amount}")
    private Integer MEALS_PULLED_AMOUNT;

    public ApiService(Map<ProductCategory, String> productCategoryData, RestTemplate restTemplate,
                      DishService dishService, ProductService productService, ProductToDishService productToDishService) {
        this.productCategoryData = productCategoryData;
        this.restTemplate = restTemplate;
        this.dishService = dishService;
        this.productService = productService;
        this.productToDishService = productToDishService;
    }

    public void warmDatabase() {
        clearTables();
        fillTables();
    }

    private void fillTables() {
        var meals = getAllMeals();

        var products = getAllProducts()
                .stream()
                .map(ProductService::mapToEntity)
                .collect(Collectors.toList());
        productService.saveAll(products);

        var dishes = getAllDishes(meals)
                .stream()
                .map(DishService::mapToEntity)
                .collect(Collectors.toList());
        dishService.saveAll(dishes);

        var productsToDishes = new ArrayList<ProductToDish>();
        var dishesToDelete = new ArrayList<Dish>();

        for (int i = 0; i < meals.size(); ++i) {
            var productsToDish = getAllProductsForMeal(meals.get(i), dishes.get(i), products);

            if (!productsToDish.isEmpty()) {
                productsToDishes.addAll(productsToDish);
            }
            else {
                dishesToDelete.add(dishes.get(i));
            }
        }

        dishService.deleteAll(dishesToDelete);
        productToDishService.saveAll(productsToDishes);
    }

    private void clearTables() {
        productToDishService.deleteAll();
        productService.deleteAll();
        dishService.deleteAll();
    }

    private List<ProductToDish> getAllProductsForMeal(MealApiDto mealApiDto, Dish dish,
                                                        List<Product> products) {
        var result = new ArrayList<ProductToDish>();
        for (int i = 1; i <= INGREDIENT_FIELDS_AMOUNT; ++i) {
            var info = getProductToDishInfo(mealApiDto, i);
            if (info == null) break;

            var compareName = formatIngredientNameForComparison(info.get(0));
            var productDto = products.stream()
                    .filter(p -> p.getName().equals(compareName))
                    .findAny()
                    .orElse(null);

            if (productDto != null) {
                var productToDish = new ProductToDish()
                        .setMeasure(info.get(1))
                        .setDish(dish)
                        .setProduct(productDto);

                result.add(productToDish);
            }
        }

        return result;
    }

    private List<ProductDto> getAllProducts() {
        var productsDto = parseAllProductFiles();
        setProductImagesIfExist(productsDto);

        return productsDto;
    }

    private List<DishDto> getAllDishes(List<MealApiDto> meals) {
        var dishes = new ArrayList<DishDto>(meals.size());

        for (MealApiDto meal : meals) {
            var dishDtoValue = mapToDishDto(meal);
            dishes.add(dishDtoValue);
        }

        return dishes;
    }

    private List<MealApiDto> getAllMeals() {
        var meals = new ArrayList<MealApiDto>(MEALS_PULLED_AMOUNT);

        for (int i = MEAL_START_ID; i < MEAL_START_ID + MEALS_PULLED_AMOUNT; ++i) {
            var mealApiDto = getMealApiDtoById(i);
            if (mealApiDto != null) meals.add(mealApiDto);
        }

        return meals;
    }

    private List<ProductDto> parseAllProductFiles() {
        var allProducts = new ArrayList<ProductDto>(1100);

        for (var category : productCategoryData.keySet()) {
            var path = productCategoryData.get(category);

            var products = parseProductFile(category, path);
            allProducts.addAll(products);
        }

        return allProducts;
    }

    private void setProductImagesIfExist(List<ProductDto> productsDto) {
        ListIngredientApiDto ingredientsList = restTemplate.getForObject(
                MealApi.GET_ALL_INGREDIENTS.getPath(), ListIngredientApiDto.class);
        if (ingredientsList == null || ingredientsList.meals() == null) return;

        int counter = 0;
        for (var ingredient : ingredientsList.meals()) {
            var compareName = formatIngredientNameForComparison(ingredient.strIngredient());
            var product = productsDto
                    .stream()
                    .filter(p -> p.getName().equals(compareName))
                    .findAny()
                    .orElse(null);

            if (product != null) {
                var imageName = formatIngredientNameForImage(ingredient.strIngredient());
                var imageUrl = Template.INGREDIENT_IMAGE_URL.getValue().formatted(imageName);
                product.setImageUrl(imageUrl);
                counter++;
            }
        }

        log.info("Products amount with images: {}", counter);
    }

    private List<ProductDto> parseProductFile(ProductCategory category, String path) {
        var is = getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) throw new IllegalArgumentException("File '%s' is not found".formatted(path));

        var result = new ArrayList<ProductDto>();

        try (var streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             var reader = new BufferedReader(streamReader)) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                var productDto = new ProductDto()
                        .setName(line)
                        .setCategory(category)
                        .setImageUrl(category.getDefaultImageUrl());

                result.add(productDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

        return result;
    }

    private MealApiDto getMealApiDtoById(int index) {
        var path = MealApi.GET_DISH_BY_ID.getPath().formatted(index);
        var dishDto = restTemplate.getForObject(
                path, ListMealApiDto.class);

        if (dishDto == null || dishDto.meals() == null || dishDto.meals().isEmpty()) return null;
        return dishDto.meals().get(0);
    }

    private String formatIngredientNameForComparison(String name) {
        return name
                .trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("-", " ");
    }

    private String formatIngredientNameForImage(String name) {
        return name
                .trim()
                .replaceAll(" ", "-");
    }

    private DishDto mapToDishDto(MealApiDto mealApiDto) {
        return new DishDto()
                .setName(mealApiDto.strMeal())
                .setRecipe(mealApiDto.strInstructions())
                .setDifficulty(0D)
                .setVotesAmount(0)
                .setImageUrl(mealApiDto.strMealThumb());
    }

    @SneakyThrows
    private List<String> getProductToDishInfo(MealApiDto mealApiDto, int number) {
        var ingredientMethod = mealApiDto.getClass().getMethod("strIngredient".concat(Integer.toString(number)));
        var measureMethod = mealApiDto.getClass().getMethod("strMeasure".concat(Integer.toString(number)));

        var ingredientName = (String) ingredientMethod.invoke(mealApiDto);
        var measureName = (String) measureMethod.invoke(mealApiDto);

        if (ingredientName == null || ingredientName.isEmpty()
                || measureName == null || measureName.isEmpty()) return null;

        return List.of(ingredientName, measureName);
    }

}
