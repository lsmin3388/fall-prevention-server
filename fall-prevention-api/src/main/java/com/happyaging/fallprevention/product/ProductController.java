package com.happyaging.fallprevention.product;

import java.util.List;

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

import com.happyaging.fallprevention.product.usecase.ProductUseCase;
import com.happyaging.fallprevention.product.usecase.dto.ProductRequestDto;
import com.happyaging.fallprevention.product.usecase.dto.ProductResponseDto;
import com.happyaging.fallprevention.product.usecase.dto.ProductUpdateRequestDto;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductUseCase productUseCase;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiSuccessResult<Long>> createProduct(
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        Long responseBody = productUseCase.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, responseBody));
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResult<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> responseBody = productUseCase.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, responseBody));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiSuccessResult<ProductResponseDto>> getProduct(
            @PathVariable("productId") Long productId) {
        ProductResponseDto responseBody = productUseCase.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, responseBody));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<ApiSuccessResult<Long>> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody ProductUpdateRequestDto requestDto) {
        Long responseBody = productUseCase.updateProduct(productId, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, responseBody));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiSuccessResult<Long>> deleteProduct(
            @PathVariable("productId") Long productId) {
        Long responseBody = productUseCase.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, responseBody));
    }
}

