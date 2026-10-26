
package com.example.paymentservice.event;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private String eventId;
    private String eventType;
    private Long orderId;
    private Long userId;
    private BigDecimal total;
    private List<OrderItemEvent> items;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemEvent {

        private Long productId;
        private Integer quantity;
    }
}
