package com.happyaging.fallprevention.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.product.entity.Product;
import com.happyaging.fallprevention.product.exception.ProductNotFoundException;
import com.happyaging.fallprevention.product.persistence.ProductRepository;
import com.happyaging.fallprevention.product.usecase.ProductUseCase;
import com.happyaging.fallprevention.product.usecase.dto.ProductRequestDto;
import com.happyaging.fallprevention.product.usecase.dto.ProductResponseDto;
import com.happyaging.fallprevention.product.usecase.dto.ProductUpdateRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Long createProduct(@Valid ProductRequestDto productRequestDto) {
        Product savedProduct = productRepository.save(productRequestDto.toEntity());

        return savedProduct.getId();
    }

    @Override
    @Transactional
    public Long updateProduct(Long id, ProductUpdateRequestDto productUpdateRequestDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        existingProduct.update(productUpdateRequestDto);
        Product product = productRepository.save(existingProduct);

        return product.getId();
    }

    @Override
    @Transactional
    public Long deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);

        return product.getId();
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        return ProductResponseDto.fromEntity(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) return List.of();

        return products.stream()
            .map(ProductResponseDto::fromEntity)
            .toList();
    }
}
