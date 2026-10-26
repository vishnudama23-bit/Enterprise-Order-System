
package com.example.product.service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.example.product.service.dto.requestdto.ProductRequestDto;
import com.example.product.service.dto.responsedto.ProductResponseDto;
import com.example.product.service.entity.Product;
import com.example.product.service.event.ProductCreatedEvent;
import com.example.product.service.event.ProductEventProducer;
import com.example.product.service.exception.DuplicateProductException;
import com.example.product.service.exception.ProductNotFoundException;
import com.example.product.service.mapper.ProductMapper;
import com.example.product.service.repository.ProductRepository;
import com.example.product.service.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductEventProducer productEventProducer;

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto request) {

        if (productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateProductException(
                    "Product already exists: " + request.getName());
        }

        Product product = productMapper.toEntity(request);

        if (product.getActive() == null) {
            product.setActive(true);
        }

        Product saved = productRepository.save(product);

        ProductCreatedEvent event = new ProductCreatedEvent(
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getActive(),
                request.getInitialQuantity()
        );
        // Publish only after the product transaction commits.
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {

                    @Override
                    public void afterCommit() {
                        productEventProducer.publishProductCreated(event);
                    }
                }
        );

        log.info("Created product with ID: {}", saved.getId());

        return productMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {

        return productMapper.toResponseDto(findProduct(id));
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(
            Long id, ProductRequestDto request) {

        Product product = findProduct(id);

        if (productRepository.existsByNameIgnoreCaseAndIdNot(
                request.getName(), id)) {
            throw new DuplicateProductException(
                    "Another product already uses name: "
                    + request.getName());
        }

        productMapper.updateEntity(request, product);

        Product updated = productRepository.save(product);

        log.info("Updated product with ID: {}", updated.getId());

        return productMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        Product product = findProduct(id);

        productRepository.delete(product);

        log.info("Deleted product with ID: {}", id);
    }

    private Product findProduct(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with ID: " + id));
    }
}
