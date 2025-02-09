package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.configs.properties.KafkaProperties;
import ru.otus.java.pro.mt.core.transfers.configs.properties.TransfersProperties;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersRepository;
import ru.otus.java.pro.mt.core.transfers.validators.TransferRequestValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransfersServiceImpl implements TransfersService {
    private final TransfersRepository transfersRepository;
    private final TransferRequestValidator transferRequestValidator;
    private final TransfersProperties transfersProperties;
    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientId(clientId);
    }

    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        transferRequestValidator.validate(executeTransferDtoRq);
        if (executeTransferDtoRq.getAmount().compareTo(transfersProperties.getMaxTransferSum()) > 0) {
            throw new BusinessLogicException("OOPS", "OOPS_CODE");
        }
        Transfer transfer = new Transfer(UUID.randomUUID().toString(), "1", "2", "1", "2", "Demo", BigDecimal.ONE);
        save(transfer);
        send(transfer);
    }

    @Override
    public void save(Transfer transfer) {
        transfersRepository.save(transfer);
    }

    @Override
    public void send(Transfer transfer) {
        if (transfer != null) {
            try {
                kafkaTemplate.send(kafkaProperties.getTopicName(), transfer.getId(), "status: EXECUTED").get();
                log.info("#### -> Produced a message to the kafka topic: {}", kafkaProperties.getTopicName());
            } catch (InterruptedException | ExecutionException e) {
                log.error("The error occurred when trying to send message to the Kafka topic: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
