package com.backend.bank.banking_system.mapper;

import com.backend.bank.banking_system.dtos.request.TransactionRequestDto;
import com.backend.bank.banking_system.dtos.response.TransactionResponseDto;
import com.backend.bank.banking_system.enums.TransactionType;
import com.backend.bank.banking_system.model.Transaction;
import com.backend.bank.banking_system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.sql.Types.NULL;

@Component
public class TransactionMapper {
    @Autowired
    private AccountService accountService;
    public Transaction transactionRequestToTransaction(TransactionRequestDto transactionRequestDto){
        Transaction transaction = new Transaction();
        if(transactionRequestDto.getDebitFrom()==NULL) {
            transaction.setCreditedTo(accountService.getAccountById(transactionRequestDto.getCreditTo()));
            transaction.setType(TransactionType.CREDIT);
        }
        else if(transactionRequestDto.getCreditTo()==NULL) {
            transaction.setDebitedFrom(accountService.getAccountById(transactionRequestDto.getDebitFrom()));
            transaction.setType(TransactionType.DEBIT);
        }
        else {
            transaction.setCreditedTo(accountService.getAccountById(transactionRequestDto.getCreditTo()));
            transaction.setDebitedFrom(accountService.getAccountById(transactionRequestDto.getDebitFrom()));
            transaction.setType(TransactionType.TRANSFER);
        }
        transaction.setAmount(transactionRequestDto.getAmount());
        return transaction;
    }

    public TransactionResponseDto fromTransactionToTransactionResponseDto(Transaction transaction){
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setId(transaction.getId());
        transactionResponseDto.setTimestamp(transaction.getTimestamp());
        transactionResponseDto.setAmount(transaction.getAmount());
        if(transaction.getType() == TransactionType.DEBIT || transaction.getType() == TransactionType.TRANSFER)
            transactionResponseDto.setDebitedFrom(transaction.getDebitedFrom().getId());
        if(transaction.getType() == TransactionType.CREDIT || transaction.getType() == TransactionType.TRANSFER)
            transactionResponseDto.setCreditedTo(transaction.getCreditedTo().getId());
        transactionResponseDto.setType(transaction.getType());
        return transactionResponseDto;
    }
}
