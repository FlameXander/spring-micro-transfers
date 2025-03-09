package ru.otus.java.pro.mt.core.transfers.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KafkaMessage (@JsonProperty String transferId, @JsonProperty String status) {
}
