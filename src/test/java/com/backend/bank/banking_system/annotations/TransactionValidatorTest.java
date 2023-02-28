package com.backend.bank.banking_system.annotations;

import com.backend.bank.banking_system.constants.TransactionConstants;
import com.backend.bank.banking_system.dtos.request.TransactionRequestDto;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import static java.sql.Types.NULL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionValidatorTest {
    @InjectMocks
    private TransactionValidator transactionValidator;
    @Mock
    private ConstraintValidatorContext cxt;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @Test
    void testIsValid_whenAmountIsNull_thenReturnFalse() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1, 2, NULL);
        Mockito.when(cxt.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(builder);
        assertFalse(transactionValidator.isValid(transactionRequestDto, cxt));
    }
    @Test
    void testIsValid_whenBothAccountAreNull_thenReturnFalse() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(NULL, NULL, 100);
        Mockito.when(cxt.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(builder);
        assertFalse(transactionValidator.isValid(transactionRequestDto, cxt));
    }
    @Test
    void testIsValid_whenAmountIsNegative_thenReturnFalse() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1, 2, -100);
        Mockito.when(cxt.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(builder);
        assertFalse(transactionValidator.isValid(transactionRequestDto, cxt));
    }
    @Test
    void testIsValid_whenAmountIsAboveLimit_thenReturnFalse() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1, 2, TransactionConstants.TRANSACTION_CAP + 1);
        Mockito.when(cxt.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(builder);
        assertFalse(transactionValidator.isValid(transactionRequestDto, cxt));
    }
    @Test
    void testIsValid_whenAmountIsLimit_thenReturnTrue() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1, 2, TransactionConstants.TRANSACTION_CAP);
        assertTrue(transactionValidator.isValid(transactionRequestDto, cxt));
    }
    @Test
    void testIsValid_whenRequestIsValid_thenReturnTrue() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(1, 2, 1000);
        assertTrue(transactionValidator.isValid(transactionRequestDto, cxt));
    }
}