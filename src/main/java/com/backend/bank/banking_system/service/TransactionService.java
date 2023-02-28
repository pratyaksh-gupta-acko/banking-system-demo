package com.backend.bank.banking_system.service;

import com.backend.bank.banking_system.constants.TransactionConstants;
import com.backend.bank.banking_system.dtos.request.TransactionRequestDto;
import com.backend.bank.banking_system.dtos.response.TransactionResponseDto;
import com.backend.bank.banking_system.exception.*;
import com.backend.bank.banking_system.mapper.TransactionMapper;
import com.backend.bank.banking_system.model.Transaction;
import com.backend.bank.banking_system.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionMapper transactionMapper;

    public boolean validate(TransactionRequestDto transactionRequestDto){
        if((transactionRequestDto.getDebitFrom()==NULL && transactionRequestDto.getCreditTo()==NULL)
                || transactionRequestDto.getAmount()==NULL)
            throw new InvalidTransactionException();
        if(transactionRequestDto.getAmount()> TransactionConstants.TRANSACTION_CAP)
            throw new TransactionAmountTooHighException();
        return true;
    }

    public TransactionResponseDto getTransactionDetails(int id){
        Transaction transaction = getTransactionById(id);
        return transactionMapper.fromTransactionToTransactionResponseDto(transaction);
    }

    public Transaction getTransactionById(int id){
        return transactionRepo.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public TransactionResponseDto transfer(TransactionRequestDto transactionDto){
        validate(transactionDto);
        Transaction transaction=transactionMapper.transactionRequestToTransaction(transactionDto);
        accountService.debitAccount(transaction.getDebitedFrom(), transaction.getAmount());
        accountService.creditAccount(transaction.getCreditedTo(),transaction.getAmount());
        return transactionMapper.fromTransactionToTransactionResponseDto(transactionRepo.save(transaction));
    }

    public TransactionResponseDto credit(TransactionRequestDto transactionRequestDto){
        validate(transactionRequestDto);
        Transaction transaction=transactionMapper.transactionRequestToTransaction(transactionRequestDto);
        accountService.creditAccount(transaction.getCreditedTo(),transaction.getAmount());
        return transactionMapper.fromTransactionToTransactionResponseDto(transactionRepo.save(transaction));
    }

    public TransactionResponseDto debit(TransactionRequestDto transactionRequestDto){
        validate(transactionRequestDto);
        Transaction transaction=transactionMapper.transactionRequestToTransaction(transactionRequestDto);
        accountService.debitAccount(transaction.getDebitedFrom(), transaction.getAmount());
        return transactionMapper.fromTransactionToTransactionResponseDto(transactionRepo.save(transaction));
    }

    public List<TransactionResponseDto> fromTransactionListToTransactionResponseList(List<Transaction>transactionList){
        List<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        for(Transaction transaction:transactionList)
            transactionResponseDtoList.add(transactionMapper.fromTransactionToTransactionResponseDto(transaction));
        return transactionResponseDtoList;
    }
}
