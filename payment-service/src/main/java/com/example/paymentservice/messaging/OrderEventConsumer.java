
package com.example.paymentservice.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.paymentservice.dto.request.PaymentRequestDto;
import com.example.paymentservice.enums.PaymentMethod;
import com.example.paymentservice.event.InventoryResultEvent;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @KafkaListener(
            topics = "${app.kafka.topic.inventory-result}",
            groupId = "payment-service-inventory-group",
            containerFactory = "inventoryResultKafkaListenerContainerFactory")
    public void consumeInventoryResult(InventoryResultEvent event) {

        if (event == null || event.getOrderId() == null) {
            log.error("Invalid inventory result event received");
            return;
        }

        Long orderId = event.getOrderId();

        log.info("Received inventory result for order ID: {}", orderId);

        // Payment must only be requested after successful inventory reservation.
        if (!"INVENTORY_RESERVED".equals(event.getInventoryStatus())) {
            log.warn(
                    "Skipping payment for order ID: {}. Inventory status: {}",
                    orderId,
                    event.getInventoryStatus());
            return;
        }

        if (event.getTotal() == null || event.getTotal().signum() <= 0) {
            log.error("Invalid order total for order ID: {}", orderId);
            return;
        }

        // Avoid creating another payment when this order already has one.
        if (paymentRepository.existsByOrderId(orderId)) {
            log.info(
                    "Payment already exists for order ID: {}. "
                            + "Skipping duplicate event.",
                    orderId);
            return;
        }

        PaymentRequestDto request = new PaymentRequestDto();
        request.setOrderId(orderId);
        request.setAmount(event.getTotal());
        request.setPaymentMethod(PaymentMethod.UPI);

        paymentService.createPayment(request);

        log.info("Payment creation requested for order ID: {}", orderId);
    }
}
