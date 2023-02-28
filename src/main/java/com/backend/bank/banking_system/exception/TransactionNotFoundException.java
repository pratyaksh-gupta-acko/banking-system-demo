package com.backend.bank.banking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TransactionNotFoundException extends ResponseStatusException {
    public TransactionNotFoundException(int id) {
        super(HttpStatus.NOT_FOUND, "Transaction with id " + id + " not found");
    }
}
