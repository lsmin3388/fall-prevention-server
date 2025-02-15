package com.happyaging.fallprevention.product.usecase;

import java.util.List;

import com.happyaging.fallprevention.product.usecase.dto.ProductRequestDto;
import com.happyaging.fallprevention.product.usecase.dto.ProductResponseDto;
import com.happyaging.fallprevention.product.usecase.dto.ProductUpdateRequestDto;

public interface ProductUseCase {
	Long createProduct(ProductRequestDto productRequestDto);

	Long updateProduct(Long id, ProductUpdateRequestDto productUpdateRequestDto);

	Long deleteProduct(Long id);

	ProductResponseDto getProduct(Long id);

	List<ProductResponseDto> getAllProducts();
}
