package com.example.notifications;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.core.transfers.kafka.KafkaMessage;

@Component
public class Listener {

    @KafkaListener(id = "1", topics = "default")
    public void listen(KafkaMessage message) {
        System.out.println("Transfer ID: " + message.transferId());
    }
}
