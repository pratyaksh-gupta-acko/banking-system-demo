package com.backend.bank.banking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InsufficientBalanceException extends ResponseStatusException {
    public InsufficientBalanceException(int accountId){
        super(HttpStatus.BAD_REQUEST, "Insufficient balance in account " + accountId);
    }
}
