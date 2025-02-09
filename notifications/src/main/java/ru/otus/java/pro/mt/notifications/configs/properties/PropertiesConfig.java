package ru.otus.java.pro.mt.notifications.configs.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        KafkaProperties.class
})
public class PropertiesConfig {
}
