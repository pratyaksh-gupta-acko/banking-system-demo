package com.backend.bank.banking_system.dtos.response;

import com.backend.bank.banking_system.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TransactionResponseDto {
    private int id;
    private Timestamp timestamp;
    private int amount;
    private TransactionType type;
    private int debitedFrom;
    private int creditedTo;
}
