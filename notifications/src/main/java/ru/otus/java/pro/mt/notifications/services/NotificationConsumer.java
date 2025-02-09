package ru.otus.java.pro.mt.notifications.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.notifications.dtos.NotificationDto;

import java.util.Objects;

@Component
@Slf4j
public class NotificationConsumer {

    @RetryableTopic(
            backoff = @Backoff(value = 6000),
            attempts = "4",
            sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC,
            exclude = {NullPointerException.class}
    )
    @KafkaListener(
            groupId = "${kafka.consumer-group-id}",
            topicPartitions = @TopicPartition(
                    topic = "${kafka.topic-name}",
                    partitionOffsets = {
                            @PartitionOffset(
                                    partition = "${kafka.partition}",
                                    initialOffset = "${kafka.initial-offset}"
                            )
                    }
            )
    )
    public void consume(ConsumerRecord<String, String> notification, @Headers MessageHeaders headers) {
        Acknowledgment ack = headers.get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        log.info("По переводу '{}' клиенту отправлена нотификация '{}'", notification.key(), notification.value());
        if (Objects.nonNull(ack)) ack.acknowledge();
    }

    @DltHandler
    public void dlt(NotificationDto notificationDto, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("Event from topic {}  is dead lettered - event:{}", topic, notificationDto);
    }
}
