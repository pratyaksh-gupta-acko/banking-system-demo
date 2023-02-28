package com.backend.bank.banking_system.mapper;

import com.backend.bank.banking_system.dtos.request.TransactionRequestDto;
import com.backend.bank.banking_system.dtos.response.TransactionResponseDto;
import com.backend.bank.banking_system.enums.TransactionType;
import com.backend.bank.banking_system.model.Account;
import com.backend.bank.banking_system.model.Transaction;
import com.backend.bank.banking_system.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static java.sql.Types.NULL;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransactionMapperTest {
    @InjectMocks
    private TransactionMapper transactionMapper;
    @MockBean
    private AccountService accountService;
    Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
    @Test
    void testTransactionRequestDtoToTransaction_whenRequestIsTransfer_thenReturnTransaction() {
        Account creditAccount = new Account(1, true, "Pratyaksh", 1000);
        Account debitAccount = new Account(2, true, "Udit", 2000);
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1, 2, 100);
        Transaction expectedTransaction = new Transaction(debitAccount, creditAccount, 100, TransactionType.TRANSFER);

        when(accountService.getAccountById(1)).thenReturn(creditAccount);
        when(accountService.getAccountById(2)).thenReturn(debitAccount);
        assertEquals(expectedTransaction, transactionMapper.transactionRequestToTransaction(transactionRequestDto));
    }

    @Test
    void testTransactionRequestDtoToTransaction_whenRequestIsCredit_thenReturnTransaction() {
        Account creditAccount = new Account(1, true, "Pratyaksh", 1000);
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1, NULL, 100);
        Transaction expectedTransaction = new Transaction(creditAccount, 100, TransactionType.CREDIT);

        when(accountService.getAccountById(1)).thenReturn(creditAccount);
        assertEquals(expectedTransaction, transactionMapper.transactionRequestToTransaction(transactionRequestDto));
    }

    @Test
    void testTransactionRequestDtoToTransaction_whenRequestIsDebit_thenReturnTransaction() {
        Account debitAccount = new Account(2, true, "Udit", 2000);
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(NULL, 2, 100);
        Transaction expectedTransaction = new Transaction(debitAccount, 100, TransactionType.DEBIT);

        when(accountService.getAccountById(2)).thenReturn(debitAccount);
        assertEquals(expectedTransaction, transactionMapper.transactionRequestToTransaction(transactionRequestDto));
    }

    @Test
    void testFromTransactionToTransactionResponseDto_whenTransactionIsValid_thenReturnTransactionRequestDto() {
        Account creditAccount = new Account(1, true, "Pratyaksh", 1000);
        Account debitAccount = new Account(2, true, "Udit", 2000);
        Transaction transaction = new Transaction(1, timestamp, debitAccount, creditAccount, 100, TransactionType.TRANSFER);
        TransactionResponseDto expectedTransactionResponseDto = new TransactionResponseDto(1, timestamp, 100, TransactionType.TRANSFER, 2, 1);

        assertEquals(expectedTransactionResponseDto, transactionMapper.fromTransactionToTransactionResponseDto(transaction));
    }
}