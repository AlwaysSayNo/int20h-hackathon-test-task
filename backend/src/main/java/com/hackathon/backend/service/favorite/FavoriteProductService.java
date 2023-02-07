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

    @Transactional(rollbackFor = Exception.class)
    public void deleteFavoriteProduct(Long id) {
        favoriteProductRepository.deleteById(id);
    }

    public FavoriteProductViewDto getFavoriteProduct(Long id) {
        var favoriteCollection = favoriteProductRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Favorite product not found"));
        return mapToViewDto(favoriteCollection);
    }

    public List<LabelValue> getAllLabelValueFavoriteProductsForUser(String userLogin) {
        var user = userService.getUser(userLogin);
        return favoriteProductRepository.getAllLabelValueFavoriteProductForUser(user.getId());
    }

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
