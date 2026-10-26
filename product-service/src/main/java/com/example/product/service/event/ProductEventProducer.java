
package com.example.product.service.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventProducer {

    private static final String TOPIC = "product-created";

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public void publishProductCreated(ProductCreatedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.getProductId().toString(),
                event
        );
    }
}
