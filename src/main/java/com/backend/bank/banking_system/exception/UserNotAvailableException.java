package com.backend.bank.banking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotAvailableException extends ResponseStatusException {
    public UserNotAvailableException(int accountId){
        super(HttpStatus.NOT_FOUND, "Account by id " + accountId + " was not found");
    }
}
