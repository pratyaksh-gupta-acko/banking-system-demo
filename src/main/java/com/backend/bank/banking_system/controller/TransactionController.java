package com.backend.bank.banking_system.controller;

import com.backend.bank.banking_system.dtos.request.TransactionRequestDto;
import com.backend.bank.banking_system.dtos.response.TransactionResponseDto;
import com.backend.bank.banking_system.model.Transaction;
import com.backend.bank.banking_system.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}")
    public TransactionResponseDto getTransactionById(@PathVariable int id){
        return transactionService.getTransactionDetails(id);
    }

    @PostMapping("/transfer")
    public TransactionResponseDto transfer(@Valid @RequestBody TransactionRequestDto transactionRequestDto){
        return transactionService.transfer(transactionRequestDto); //TODO -- Validate for both account present case
    }

    @PostMapping("/debit")
    public TransactionResponseDto debit(@Valid @RequestBody TransactionRequestDto transactionRequestDto){
        return transactionService.debit(transactionRequestDto);
    }

    @PostMapping("/credit")
    public TransactionResponseDto credit(@Valid @RequestBody TransactionRequestDto transactionRequestDto){
        return transactionService.credit(transactionRequestDto);
    }
}
//TODO -- add response entity in Transaction controller