package com.backend.bank.banking_system.service;

import com.backend.bank.banking_system.constants.TransactionConstants;
import com.backend.bank.banking_system.dtos.request.TransactionRequestDto;
import com.backend.bank.banking_system.enums.TransactionType;
import com.backend.bank.banking_system.exception.InvalidTransactionException;
import com.backend.bank.banking_system.exception.TransactionAmountTooHighException;
import com.backend.bank.banking_system.exception.TransactionNotFoundException;
import com.backend.bank.banking_system.model.Transaction;
import com.backend.bank.banking_system.repo.TransactionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepo transactionRepo;

    @Test
    void testValidateTransaction_whenBothAccountsAreNull_thenThrowException() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        assertThrows(InvalidTransactionException.class, () -> {
            transactionService.validate(transactionRequestDto);
        });
    }

    @Test
    void testValidateTransaction_whenAmountIsAboveLimit_thenThrowException() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1,2,500001);
        assertThrows(TransactionAmountTooHighException.class, () -> {
            transactionService.validate(transactionRequestDto);
        });
    }

    @Test
    void testValidateTransaction_whenAmountIsTransactionCap_thenThrowException() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1,2, TransactionConstants.TRANSACTION_CAP);
        assertTrue(transactionService.validate(transactionRequestDto));
    }

    @Test
    void testValidateTransaction_whenTransactionIsValid_thenThrowException() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1,2,100);
        assertTrue(transactionService.validate(transactionRequestDto));
    }

    @Test
    void testGetTransactionById_whenTransactionIsNotPresent_thenThrowException() {
        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransactionById(1);
        });
    }

    @Test
    void testGetTransactionById_whenTransactionIsValid_thenThrowException() {
        Transaction transaction = new Transaction(1, 100, TransactionType.DEBIT);
        when(transactionRepo.findById(1)).thenReturn(Optional.of(transaction));
        assertEquals(transaction, transactionService.getTransactionById(1));
    }
}