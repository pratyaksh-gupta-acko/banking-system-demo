package com.backend.bank.banking_system.annotations;

import com.backend.bank.banking_system.constants.TransactionConstants;
import com.backend.bank.banking_system.dtos.request.TransactionRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import static java.sql.Types.NULL;


public class TransactionValidator implements ConstraintValidator<TransactionConstraint, TransactionRequestDto> {
    @Override
    public boolean isValid(TransactionRequestDto transactionRequestDto, ConstraintValidatorContext cxt){
        cxt.disableDefaultConstraintViolation();
        if(transactionRequestDto.getAmount() == NULL){
//            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Amount field required").addConstraintViolation();
        }
        else if(transactionRequestDto.getAmount() <= 0 || transactionRequestDto.getAmount() > TransactionConstants.TRANSACTION_CAP){
            cxt.buildConstraintViolationWithTemplate("Amount should be between 1 and "+ TransactionConstants.TRANSACTION_CAP).addConstraintViolation();
        }
        else if(transactionRequestDto.getDebitFrom() == NULL && transactionRequestDto.getCreditTo() == NULL){
            cxt.buildConstraintViolationWithTemplate("Account required for transaction").addConstraintViolation();
        }
        else
            return true;
        return false;
    }
}
