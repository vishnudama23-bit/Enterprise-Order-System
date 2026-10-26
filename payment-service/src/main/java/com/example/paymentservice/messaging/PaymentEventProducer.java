
package com.example.paymentservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.paymentservice.event.PaymentEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Value("${app.kafka.topic.payment-result}")
    private String paymentResultTopic;

    public void publishPaymentResult(PaymentEvent event) {

        kafkaTemplate.send(
                paymentResultTopic,
                event.getOrderId().toString(),
                event
        ).whenComplete((result, exception) -> {

            if (exception != null) {
                log.error(
                        "Failed to publish payment result for order {}",
                        event.getOrderId(),
                        exception);
            } else {
                log.info(
                        "Published payment result for order {} to topic {}",
                        event.getOrderId(),
                        result.getRecordMetadata().topic());
            }
        });
    }
}
