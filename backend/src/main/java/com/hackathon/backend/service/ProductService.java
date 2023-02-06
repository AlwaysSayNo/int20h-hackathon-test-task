package com.hackathon.backend.service;

import com.hackathon.backend.dto.ProductDto;
import com.hackathon.backend.enumeration.ProductCategory;
import com.hackathon.backend.model.Product;
import com.hackathon.backend.repository.ProductRepository;
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

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Set<Product> getUserProducts(String token) {
        // TODO: 05.02.2023 UserRepository get userId from token
        long userId = -1;
        return productRepository.getUserProducts(userId);
    }

    public Map<ProductCategory, List<Product>> getProductsByCategories() {
        return productRepository.getProducts().stream().collect(groupingBy(Product::getCategory));
    }

    public List<ProductCategory> getProductCategories() {
        return productRepository.getCategories();
    }

    public List<Product> getProductsByCategory(ProductCategory category, int page) {
        // TODO: 05.02.2023 Move page size to constants or pass as argument
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.getProductsByCategory(category, pageable);
    }

    public ProductDto mapToProductDto(Product product) {
        return new ProductDto()
                .setName(product.getName())
                .setCategory(product.getCategory())
                .setImageUrl(product.getImageUrl());
    }
}
