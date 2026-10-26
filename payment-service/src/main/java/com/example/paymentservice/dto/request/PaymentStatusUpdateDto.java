
package com.example.paymentservice.dto.request;

import com.example.paymentservice.enums.PaymentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payment status update request")
public class PaymentStatusUpdateDto {

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
}
