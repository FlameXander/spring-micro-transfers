package com.example.notifications;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.core.transfers.kafka.TransactionMessage;

@Component
public class Listener {

    @KafkaListener(id = "1", topics = "default")
    public void listen(TransactionMessage message) {
        System.out.println("По переводу " + message.transferId() + "клиенту отправлена нотификация");
    }
}
