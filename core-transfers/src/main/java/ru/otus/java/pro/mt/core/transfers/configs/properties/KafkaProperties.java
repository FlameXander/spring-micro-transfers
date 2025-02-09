package ru.otus.java.pro.mt.core.transfers.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaProperties {
    private final String topicName;
}
