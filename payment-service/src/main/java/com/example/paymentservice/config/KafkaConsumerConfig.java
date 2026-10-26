
package com.example.paymentservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.paymentservice.event.InventoryResultEvent;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, InventoryResultEvent>
            inventoryResultConsumerFactory() {

        Map<String, Object> properties = new HashMap<>();

        properties.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        properties.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "payment-service-inventory-group");

        properties.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest");

        properties.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        properties.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        JsonDeserializer<InventoryResultEvent> deserializer =
                new JsonDeserializer<>(InventoryResultEvent.class);

        deserializer.ignoreTypeHeaders();
        deserializer.addTrustedPackages(
                "com.example.paymentservice.event");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                deserializer);
    }

    @Bean(name = "inventoryResultKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, InventoryResultEvent>
            inventoryResultKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, InventoryResultEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(inventoryResultConsumerFactory());

        return factory;
    }
}
