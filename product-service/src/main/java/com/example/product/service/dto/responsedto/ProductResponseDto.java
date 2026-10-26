package com.example.product.service.dto.responsedto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer version;
}