
package com.example.paymentservice.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class InventoryResultEvent {

    private String eventId;
    private String eventType;
    private Long orderId;
    private BigDecimal total;
    private String inventoryStatus;
    private String message;
    private LocalDateTime processedAt;
}
