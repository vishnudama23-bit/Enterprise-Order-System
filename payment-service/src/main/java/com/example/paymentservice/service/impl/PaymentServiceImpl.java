
package com.example.paymentservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.paymentservice.dto.request.PaymentRequestDto;
import com.example.paymentservice.dto.request.PaymentStatusUpdateDto;
import com.example.paymentservice.dto.response.PaymentResponseDto;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.event.PaymentEvent;
import com.example.paymentservice.exception.PaymentNotFoundException;
import com.example.paymentservice.mapper.PaymentMapper;
import com.example.paymentservice.messaging.PaymentEventProducer;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentEventProducer paymentEventProducer;

    @Override
    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto request) {

        log.info("Creating payment for order ID: {}", request.getOrderId());

        Payment payment = paymentMapper.toEntity(request);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionReference(UUID.randomUUID().toString());

        Payment saved = paymentRepository.save(payment);

        log.info("Payment created with ID: {}", saved.getId());

        saved.setPaymentStatus(PaymentStatus.SUCCESS);
        Payment completed = paymentRepository.save(saved);

        PaymentEvent event = PaymentEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("PAYMENT_COMPLETED")
                .paymentId(completed.getId())
                .orderId(completed.getOrderId())
                .amount(completed.getAmount())
                .paymentStatus(completed.getPaymentStatus().name())
                .transactionReference(completed.getTransactionReference())
                .build();

        paymentEventProducer.publishPaymentResult(event);

        log.info("Payment completed for Order ID: {}", completed.getOrderId());

        return paymentMapper.toResponseDto(completed);
    }
    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentById(Long id) {

        return paymentMapper.toResponseDto(findPayment(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByOrderId(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(paymentMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByStatus(
            PaymentStatus status) {

        return paymentRepository.findByPaymentStatus(status)
                .stream()
                .map(paymentMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public PaymentResponseDto updatePayment(
            Long id, PaymentRequestDto request) {

        Payment payment = findPayment(id);

        paymentMapper.updateEntity(request, payment);

        Payment updated = paymentRepository.save(payment);

        log.info("Payment details updated for ID: {}", id);

        return paymentMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public PaymentResponseDto updatePaymentStatus(
            Long id, PaymentStatusUpdateDto request) {

        Payment payment = findPayment(id);

        payment.setPaymentStatus(request.getPaymentStatus());

        Payment updated = paymentRepository.save(payment);

        log.info("Payment status updated for ID: {}", id);

        return paymentMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {

        Payment payment = findPayment(id);

        paymentRepository.delete(payment);

        log.info("Payment deleted with ID: {}", id);
    }

    private Payment findPayment(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(
                        "Payment not found with ID: " + id));
    }
}
