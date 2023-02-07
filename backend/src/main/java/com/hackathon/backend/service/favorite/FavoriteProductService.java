package com.hackathon.backend.service.favorite;

import com.hackathon.backend.dto.favorite.FavoriteProductDto;
import com.hackathon.backend.dto.favorite.FavoriteProductViewDto;
import com.hackathon.backend.model.FavoriteProducts;
import com.hackathon.backend.repository.FavoriteProductRepository;
import com.hackathon.backend.repository.ProductRepository;
import com.hackathon.backend.service.product.ProductService;
import com.hackathon.backend.service.user.UserService;
import com.hackathon.backend.util.LabelValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * Service class for handling FavoriteProducts objects.
 *
 * @author [Author Name]
 * @see FavoriteProductDto
 * @see FavoriteProductRepository
 * @see ProductRepository
 * @see UserService
 */
@Service
public class FavoriteProductService {
    private final UserService userService;
    private final FavoriteProductRepository favoriteProductRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FavoriteProductService(UserService userService, FavoriteProductRepository favoriteProductRepository,
                                  ProductRepository productRepository) {
        this.userService = userService;
        this.favoriteProductRepository = favoriteProductRepository;
        this.productRepository = productRepository;
    }

    /**
     * Create a favorite product and returns the created object.
     *
     * @param userLogin the user login
     * @param dto the favorite product DTO
     * @return the created favorite product DTO
     * @throws Exception if any error occurs during the transaction
     */
    @Transactional(rollbackFor = Exception.class)
    public FavoriteProductDto createFavoriteProduct(String userLogin, FavoriteProductDto dto) {
        var user = userService.getUser(userLogin);
        var favoriteProducts = productRepository.findAllById(dto.getProductIds());

        var favoriteCollection = new FavoriteProducts();
        favoriteCollection.setName(dto.getName());
        favoriteCollection.setUser(user);
        favoriteCollection.setProducts(new HashSet<>(favoriteProducts));

        var saved = favoriteProductRepository.save(favoriteCollection);
        dto.setId(saved.getId());
        return dto;
    }

    /**
     * Update a favorite product and returns the updated object.
     *
     * @param id the favorite product id
     * @param dto the favorite product DTO
     * @return the updated favorite product DTO
     * @throws IllegalArgumentException if the favorite product was not found
     * @throws Exception if any error occurs during the transaction
     */
    @Transactional(rollbackFor = Exception.class)
    public FavoriteProductDto updateFavoriteProduct(Long id, FavoriteProductDto dto) {
        var favoriteCollection = favoriteProductRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Favorite product not found"));

        var favoriteProducts = productRepository.findAllById(dto.getProductIds());
        favoriteCollection.setName(dto.getName());
        favoriteCollection.setProducts(new HashSet<>(favoriteProducts));
        return dto;
    }

    /**
     * Delete a favorite product.
     *
     * @param id the favorite product id
     * @throws IllegalArgumentException if the favorite product was not found
     * @throws Exception if any error occurs during the transaction
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteFavoriteProduct(Long id) {
        favoriteProductRepository.deleteById(id);
    }

    /**
     * Get a favorite product by id.
     *
     * @param id the favorite product id
     * @return the favorite product view DTO
     * @throws IllegalArgumentException if the favorite product was not found
     */
    public FavoriteProductViewDto getFavoriteProduct(Long id) {
        var favoriteCollection = favoriteProductRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Favorite product not found"));
        return mapToViewDto(favoriteCollection);
    }

    /**
     * Get all favorite products for a user as a list of {@link LabelValue} objects.
     *
     * @param userLogin the user login
     * @return the list of label value favorite products
     */
    public List<LabelValue> getAllLabelValueFavoriteProductsForUser(String userLogin) {
        var user = userService.getUser(userLogin);
        return favoriteProductRepository.getAllLabelValueFavoriteProductForUser(user.getId());
    }

    /**
     * Maps a FavoriteProducts object to a FavoriteProductViewDto.
     *
     * @param favoriteProducts the favorite products object to be mapped
     * @return the mapped view DTO object
     */
    private FavoriteProductViewDto mapToViewDto(FavoriteProducts favoriteProducts) {
        var viewDto = new FavoriteProductViewDto();
        viewDto.setId(favoriteProducts.getId());
        viewDto.setName(favoriteProducts.getName());

        var dishDtoList = favoriteProducts.getProducts().stream()
                .map(ProductService::mapToDto)
                .toList();
        viewDto.setProducts(dishDtoList);
        return viewDto;
    }
}
