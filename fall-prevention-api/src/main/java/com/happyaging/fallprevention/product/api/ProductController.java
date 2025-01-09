package com.happyaging.fallprevention.product.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.product.dto.ProductRequestDto;
import com.happyaging.fallprevention.product.dto.ProductResponseDto;
import com.happyaging.fallprevention.product.entity.Product;
import com.happyaging.fallprevention.product.service.ProductService;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiSuccessResult<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        Product product = productService.createProduct(requestDto.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, ProductResponseDto.fromEntity(product)));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiSuccessResult<List<ProductResponseDto>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> productDto = products.stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, productDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResult<ProductResponseDto>> getProduct(
            @PathVariable Long id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, ProductResponseDto.fromEntity(product)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiSuccessResult<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto requestDto) {
        Product updatedProduct = productService.updateProduct(id, requestDto.toEntity());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, ProductResponseDto.fromEntity(updatedProduct)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiSuccessResult<ProductResponseDto>> deleteProduct(
            @PathVariable Long id) {
        Product deletedProduct = productService.deleteProduct(id);
        ProductResponseDto responseDto = ProductResponseDto.fromEntity(deletedProduct);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, responseDto));
    }
}

