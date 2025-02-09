package ru.otus.java.pro.mt.notifications.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.avro.StatusInfoDto;

import java.util.Objects;


@Component
public class StatusConsumer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RetryableTopic(
            backoff = @Backoff(value = 6000),
            attempts = "4",
            autoCreateTopics = "true",
            retryTopicSuffix = "-retry",
            dltTopicSuffix = "-dlt",
            sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC,
            exclude = {NullPointerException.class}
    )
    @KafkaListener(topics = "${topics.status.info}")
    public void consume(ConsumerRecord<String, StatusInfoDto> status, @Headers MessageHeaders headers) {
        logger.info("### -> Header is acquired: {}", headers);
        Acknowledgment ack = headers.get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        logger.info(String.format("#### -> Consumed a message -> %s", status.value()));
        logger.info("#### -> Key: {}", status.key());
        logger.info("#### -> Status: {}", status.value());
        if (Objects.nonNull(ack)) ack.acknowledge();
    }

    @DltHandler
    public void dlt(StatusInfoDto statusInfoDto, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        logger.error("Event from topic {}  is dead lettered - event:{}", topic, statusInfoDto);
    }
}
