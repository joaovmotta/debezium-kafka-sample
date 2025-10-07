package br.com.debezium.consumer.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventConsumer {

    @KafkaListener(topics = "dbserver1.public.product", groupId = "product-consumer-group")
    public void consume(String message) {
        System.out.println("Receiving event from debezium:");
        System.out.println(message);
        System.out.println("Event logged successfully");
    }
}