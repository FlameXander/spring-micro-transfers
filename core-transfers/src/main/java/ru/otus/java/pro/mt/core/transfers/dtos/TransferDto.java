package ru.otus.java.pro.mt.core.transfers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Перевод клиента")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TransferDto {
    @Schema(
            description = "Идентификатор перевода",
            example = "bde76ffa-f133-4c23-9bca-03618b2a94b2",
            required = true,
            minLength = 36,
            maxLength = 36
    )
    private String id;

    @Schema(
            description = "Идентификатор клиента отправителя",
            example = "1234567890",
            required = true,
            maxLength = 10
    )
    private String clientId;

    @Schema(
            description = "Идентификатор клиента получателя",
            example = "1234567890",
            required = true,
            maxLength = 10
    )
    private String targetClientId;

    @Schema(
            description = "Номер счета отправителя",
            example = "123456789012",
            required = true,
            minLength = 12,
            maxLength = 12
    )
    private String sourceAccount;

    @Schema(
            description = "Номер счета получателя",
            example = "123456789012",
            required = true,
            minLength = 12,
            maxLength = 12
    )
    private String targetAccount;

    @Schema(
            description = "Сообщение получателю",
            example = "На день рождения",
            required = true,
            maxLength = 255
    )
    private String message;

    @Schema(
            description = "Сумма",
            example = "100.00",
            required = true
    )
    private BigDecimal amount;
}