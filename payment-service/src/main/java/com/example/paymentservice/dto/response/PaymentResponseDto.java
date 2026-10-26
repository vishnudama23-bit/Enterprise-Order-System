
package com.example.paymentservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.paymentservice.enums.PaymentMethod;
import com.example.paymentservice.enums.PaymentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payment details response")
public class PaymentResponseDto {

    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionReference;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer version;
}
