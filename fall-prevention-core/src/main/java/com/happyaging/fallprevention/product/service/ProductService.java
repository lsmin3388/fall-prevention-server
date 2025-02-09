package com.happyaging.fallprevention.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.product.entity.Product;
import com.happyaging.fallprevention.product.exception.ProductNotFoundException;
import com.happyaging.fallprevention.product.persistence.ProductRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(@Valid Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        existingProduct.update(updatedProduct);
        return productRepository.save(existingProduct);
    }

    @Transactional
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
        // 트랜잭션이 끝나기 전까지 영속성 컨텍스트에 해당 엔티티가 남아있으므로 참조 가능.
        return product;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }
}
