
package com.example.product.service.event;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {

    private Long productId;
    private String productName;
    private BigDecimal price;
    private Boolean active;
    private Integer initialQuantity;
}
