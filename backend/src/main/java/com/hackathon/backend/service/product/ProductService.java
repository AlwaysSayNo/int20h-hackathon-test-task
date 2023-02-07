package com.hackathon.backend.service.product;

import com.hackathon.backend.dto.ProductDto;
import com.hackathon.backend.enumeration.ProductCategory;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.repository.ProductRepository;
import com.hackathon.backend.service.user.UserService;
import com.hackathon.backend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;

/**
 * ProductService is a service class that handles operations related to products.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    /**
     * Returns the set of products associated with the user identified by the given user login.
     *
     * @param userLogin the login of the user to retrieve the products for
     * @return the set of products associated with the user
     * @throws Exception if an error occurs while retrieving the user or their products
     */
    public Set<Product> getUserProducts(String userLogin) throws Exception {
        Long userId = userService.getUser(userLogin).getId();
        return productRepository.getUserProducts(userId);
    }

    /**
     * Returns a map of product categories to lists of products for each category.
     *
     * @return a map of product categories to lists of products
     */
    public Map<ProductCategory, List<Product>> getProductsByCategories() {
        return productRepository.getProducts().stream().collect(groupingBy(Product::getCategory));
    }

    /**
     * Returns a list of all product categories.
     *
     * @return a list of all product categories
     */
    public List<ProductCategory> getProductCategories() {
        return productRepository.getCategories();
    }

    /**
     * Returns a list of products for the specified category and page.
     *
     * @param category the category to retrieve the products for
     * @param page the page number to retrieve
     * @return a list of products for the specified category and page
     */
    public List<Product> getProductsByCategory(ProductCategory category, int page) {
        Pageable pageable = PageRequest.of(page, Constants.ITEMS_PER_PAGE);
        return productRepository.getProductsByCategory(category, pageable);
    }

    /**
     * Saves a list of products to the repository.
     *
     * @param entities the list of products to save
     * @return the list of saved products
     */
    public List<Product> saveAll(List<Product> entities) {
        return productRepository.saveAll(entities);
    }

    /**
     * Deletes all products from the repository.
     */
    public void deleteAll() {
        productRepository.deleteAll();
    }

    /**
     * Maps a product entity to its DTO representation.
     *
     * @param entity the product entity to map
     * @return the DTO representation of the product entity
     */
    public static ProductDto mapToDto(Product entity) {
        return new ProductDto()
                .setId(entity.getId())
                .setName(entity.getName())
                .setCategory(entity.getCategory())
                .setImageUrl(entity.getImageUrl());
    }

    /**
     * Maps a product DTO to its entity representation.
     *
     * @param dto the product DTO to map
     * @return the entity representation of the product DTO
     */
    public static Product mapToEntity(ProductDto dto) {
        return new Product()
                .setId(dto.getId())
                .setName(dto.getName())
                .setCategory(dto.getCategory())
                .setImageUrl(dto.getImageUrl());
    }

}
