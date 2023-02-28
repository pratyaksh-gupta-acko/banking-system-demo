package com.backend.bank.banking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountDeactivatedException extends ResponseStatusException {
    public AccountDeactivatedException(int id){
        super(HttpStatus.BAD_REQUEST, "Account with id " + id + " has been deactivated");
    }
}
