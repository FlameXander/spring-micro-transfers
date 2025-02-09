package ru.otus.java.pro.mt.core.transfers.configs.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "topics")
public class KafkaTopicsProperties {
    private String statusInfo;
}
