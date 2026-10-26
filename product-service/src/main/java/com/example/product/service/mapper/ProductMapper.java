package com.example.product.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.product.service.dto.requestdto.ProductRequestDto;
import com.example.product.service.dto.responsedto.ProductResponseDto;
import com.example.product.service.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDto request);

    ProductResponseDto toResponseDto(Product product);

    void updateEntity(
            ProductRequestDto request,
            @MappingTarget Product product);
}