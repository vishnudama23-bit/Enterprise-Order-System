package com.example.product.service.service;

import java.util.List;

import com.example.product.service.dto.requestdto.ProductRequestDto;
import com.example.product.service.dto.responsedto.ProductResponseDto;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto request);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(Long id);

    ProductResponseDto updateProduct(
            Long id, ProductRequestDto request);

    void deleteProduct(Long id);
}