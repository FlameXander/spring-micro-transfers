package ru.otus.java.pro.mt.notifications.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaProperties {
    private final String topicName;
    private final String consumerGroupId;
    private final String partition;
    private final String initialOffset;
}
