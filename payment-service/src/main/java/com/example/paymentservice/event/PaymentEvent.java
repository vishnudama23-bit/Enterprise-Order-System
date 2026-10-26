
package com.example.paymentservice.event;

import java.math.BigDecimal;

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
public class PaymentEvent {

    private String eventId;
    private String eventType;
    private Long paymentId;
    private Long orderId;
    private BigDecimal amount;
    private String paymentStatus;
    private String transactionReference;
}
