package ru.otus.java.pro.mt.core.transfers.kafka;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.avro.StatusInfoDto;
import ru.otus.java.pro.mt.core.transfers.configs.properties.KafkaTopicsProperties;

@Component
@AllArgsConstructor
public class StatusProducer implements MessageService<StatusInfoDto>{
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topicsProperties;

    @SneakyThrows
    public void send(StatusInfoDto statusInfoDto) {
        kafkaTemplate.send(topicsProperties.getStatusInfo(), statusInfoDto.getTransferId(), statusInfoDto).get();
    }

}
