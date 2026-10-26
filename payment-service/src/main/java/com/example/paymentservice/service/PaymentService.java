
package com.example.paymentservice.service;

import java.util.List;

import com.example.paymentservice.dto.request.PaymentRequestDto;
import com.example.paymentservice.dto.request.PaymentStatusUpdateDto;
import com.example.paymentservice.dto.response.PaymentResponseDto;
import com.example.paymentservice.enums.PaymentStatus;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto request);

    List<PaymentResponseDto> getAllPayments();

    PaymentResponseDto getPaymentById(Long id);

    List<PaymentResponseDto> getPaymentsByOrderId(Long orderId);

    List<PaymentResponseDto> getPaymentsByStatus(PaymentStatus status);

    PaymentResponseDto updatePayment(Long id, PaymentRequestDto request);

    PaymentResponseDto updatePaymentStatus(
            Long id, PaymentStatusUpdateDto request);

    void deletePayment(Long id);
}
