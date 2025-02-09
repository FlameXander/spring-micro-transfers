package ru.otus.java.pro.mt.core.transfers.kafka;

public interface MessageService <T> {
    void send(T t);
}
