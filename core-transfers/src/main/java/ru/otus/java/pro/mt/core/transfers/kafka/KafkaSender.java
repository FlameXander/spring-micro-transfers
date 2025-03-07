package ru.otus.java.pro.mt.core.transfers.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Value(value = "${app.sending.topic.name}")
    String kafkaTopic;

    public KafkaSender(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public KafkaMessage send(KafkaMessage message) {
        kafkaTemplate.send(kafkaTopic, message);
        return message;
    }
}
