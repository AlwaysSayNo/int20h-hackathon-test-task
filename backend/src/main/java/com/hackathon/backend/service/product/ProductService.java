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

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public Set<Product> getUserProducts(String userLogin) throws Exception {
        Long userId = userService.getUser(userLogin).getId();
        return productRepository.getUserProducts(userId);
    }

    public Map<ProductCategory, List<Product>> getProductsByCategories() {
        return productRepository.getProducts().stream().collect(groupingBy(Product::getCategory));
    }

    public List<ProductCategory> getProductCategories() {
        return productRepository.getCategories();
    }

    public List<Product> getProductsByCategory(ProductCategory category, int page) {
        Pageable pageable = PageRequest.of(page, Constants.ITEMS_PER_PAGE);
        return productRepository.getProductsByCategory(category, pageable);
    }

    public List<Product> saveAll(List<Product> entities) {
        return productRepository.saveAll(entities);
    }

    public static ProductDto mapToDto(Product entity) {
        return new ProductDto()
                .setId(entity.getId())
                .setName(entity.getName())
                .setCategory(entity.getCategory())
                .setImageUrl(entity.getImageUrl());
    }

    public static Product mapToEntity(ProductDto dto) {
        return new Product()
                .setId(dto.getId())
                .setName(dto.getName())
                .setCategory(dto.getCategory())
                .setImageUrl(dto.getImageUrl());
    }

}
