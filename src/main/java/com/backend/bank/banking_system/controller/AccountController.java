package com.backend.bank.banking_system.controller;

import com.backend.bank.banking_system.dtos.request.AccountRequestDto;
import com.backend.bank.banking_system.dtos.request.AccountUpdateDto;
import com.backend.bank.banking_system.dtos.response.AccountResponseDto;
import com.backend.bank.banking_system.dtos.response.TransactionResponseDto;
import com.backend.bank.banking_system.service.AccountService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable int id){
        return new ResponseEntity<>(accountService.getAccountDetails(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> addAccount(@Valid @RequestBody AccountRequestDto accountRequestDto){
        return new ResponseEntity<>(accountService.addAccount(accountRequestDto),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable int id,@Valid @RequestBody AccountUpdateDto accountUpdateDto){
        return new ResponseEntity<>(accountService.updateAccount(id,accountUpdateDto),HttpStatus.OK);
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<AccountResponseDto> deactivateAccount(@PathVariable int id){
        return new ResponseEntity<>(accountService.deactivateAccount(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransaction(@PathVariable int id){
        return new ResponseEntity<>(accountService.getTransactions(id),HttpStatus.OK);
    }
}

// todo - exception handling, request and response dtos , use enums , annotations, models relations, http status