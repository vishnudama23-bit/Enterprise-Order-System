package com.example.product.service.mapper;

import com.example.product.service.dto.requestdto.ProductRequestDto;
import com.example.product.service.dto.responsedto.ProductResponseDto;
import com.example.product.service.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-22T12:54:23+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260528-0407, environment: Java 25.0.3 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductRequestDto request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.description( request.getDescription() );
        product.price( request.getPrice() );
        product.active( request.getActive() );

        return product.build();
    }

    @Override
    public ProductResponseDto toResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDto.ProductResponseDtoBuilder productResponseDto = ProductResponseDto.builder();

        productResponseDto.id( product.getId() );
        productResponseDto.name( product.getName() );
        productResponseDto.description( product.getDescription() );
        productResponseDto.price( product.getPrice() );
        productResponseDto.active( product.getActive() );
        productResponseDto.createdDate( product.getCreatedDate() );
        productResponseDto.updatedDate( product.getUpdatedDate() );
        productResponseDto.version( product.getVersion() );

        return productResponseDto.build();
    }

    @Override
    public void updateEntity(ProductRequestDto request, Product product) {
        if ( request == null ) {
            return;
        }

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
        product.setActive( request.getActive() );
    }
}
