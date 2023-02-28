package com.backend.bank.banking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TransactionAmountTooHighException extends ResponseStatusException {
    public TransactionAmountTooHighException(){
        super(HttpStatus.BAD_REQUEST, "Transaction amount is too high");
    }
}
