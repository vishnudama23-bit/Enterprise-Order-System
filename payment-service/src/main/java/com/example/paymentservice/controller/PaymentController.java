
package com.example.paymentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.paymentservice.dto.request.PaymentRequestDto;
import com.example.paymentservice.dto.request.PaymentStatusUpdateDto;
import com.example.paymentservice.dto.response.PaymentResponseDto;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.exception.ApiErrorResponse;
import com.example.paymentservice.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Payment Management",
     description = "APIs for managing payment records")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
        summary = "Create payment",
        description = "Creates a payment record with PENDING status."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201",
            description = "Payment created successfully",
            content = @Content(
                schema = @Schema(implementation = PaymentResponseDto.class))),
        @ApiResponse(responseCode = "400",
            description = "Invalid payment request",
            content = @Content(
                schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(
            @Valid @RequestBody PaymentRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createPayment(request));
    }

    @Operation(summary = "Get all payments")
    @ApiResponse(responseCode = "200",
        description = "Payments retrieved successfully")
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @Operation(summary = "Get payment by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
            description = "Payment retrieved successfully"),
        @ApiResponse(responseCode = "404",
            description = "Payment not found",
            content = @Content(
                schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(
            @Parameter(description = "Payment ID", example = "1")
            @PathVariable @Positive Long id) {

        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @Operation(summary = "Get payments by order ID")
    @ApiResponse(responseCode = "200",
        description = "Payments retrieved successfully")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByOrderId(
            @Parameter(description = "Order ID", example = "1001")
            @PathVariable @Positive Long orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByOrderId(orderId));
    }

    @Operation(summary = "Get payments by status")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
            description = "Payments retrieved successfully"),
        @ApiResponse(responseCode = "400",
            description = "Invalid payment status")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByStatus(
            @Parameter(description = "Payment status", example = "PENDING")
            @PathVariable PaymentStatus status) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByStatus(status));
    }

    @Operation(
        summary = "Update payment details",
        description = "Updates payment amount and method. The order ID and "
                + "transaction reference remain unchanged."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200",
            description = "Payment updated successfully"),
        @ApiResponse(responseCode = "404",
            description = "Payment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> updatePayment(
            @Parameter(description = "Payment ID", example = "1")
            @PathVariable @Positive Long id,
            @Valid @RequestBody PaymentRequestDto request) {

        return ResponseEntity.ok(
                paymentService.updatePayment(id, request));
    }

    @Operation(
        summary = "Update payment status",
        description = "Changes the stored payment status only; this does "
                + "not execute an actual financial transaction."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200",
            description = "Payment status updated successfully"),
        @ApiResponse(responseCode = "404",
            description = "Payment not found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDto> updatePaymentStatus(
            @Parameter(description = "Payment ID", example = "1")
            @PathVariable @Positive Long id,
            @Valid @RequestBody PaymentStatusUpdateDto request) {

        return ResponseEntity.ok(
                paymentService.updatePaymentStatus(id, request));
    }

    @Operation(summary = "Delete payment")
    @ApiResponses({
        @ApiResponse(responseCode = "204",
            description = "Payment deleted successfully"),
        @ApiResponse(responseCode = "404",
            description = "Payment not found",
            content = @Content(
                schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @Parameter(description = "Payment ID", example = "1")
            @PathVariable @Positive Long id) {

        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
