package com.backend.bank.banking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTransactionException extends ResponseStatusException {
    public InvalidTransactionException(){
        super(HttpStatus.BAD_REQUEST,"Invalid Transaction");
    }
}
