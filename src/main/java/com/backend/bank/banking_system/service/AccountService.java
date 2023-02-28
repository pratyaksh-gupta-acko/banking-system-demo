package com.backend.bank.banking_system.service;

import com.backend.bank.banking_system.dtos.request.AccountRequestDto;
import com.backend.bank.banking_system.dtos.request.AccountUpdateDto;
import com.backend.bank.banking_system.dtos.response.AccountResponseDto;
import com.backend.bank.banking_system.dtos.response.TransactionResponseDto;
import com.backend.bank.banking_system.exception.AccountDeactivatedException;
import com.backend.bank.banking_system.exception.InsufficientBalanceException;
import com.backend.bank.banking_system.exception.UserNotAvailableException;
import com.backend.bank.banking_system.mapper.AccountMapper;
import com.backend.bank.banking_system.model.Account;
import com.backend.bank.banking_system.model.Transaction;
import com.backend.bank.banking_system.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    @Lazy
    private TransactionService transactionService;

    public Account getAccountById(int id){
        Account account = accountRepo.findById(id).orElseThrow(() -> new UserNotAvailableException(id));
        if(!account.isActivated())
            throw new AccountDeactivatedException(id);
        return account;
    }

    public boolean validateAmount(Account account, int amount){
        return account.getBalance() >= amount;
    }

    public AccountResponseDto addAccount(AccountRequestDto accountRequestDto){
        Account account = accountMapper.toAccount(accountRequestDto);
        return accountMapper.fromAccountToAccountResponse(accountRepo.save(account));
    }

    public List<TransactionResponseDto> getTransactions(int id){
        Account account=getAccountById(id);
        List<Transaction> transactionList=new ArrayList<>();
        transactionList.addAll(account.getDebit());
        transactionList.addAll(account.getCredit());
        return transactionService.fromTransactionListToTransactionResponseList(transactionList);
    }

    public AccountResponseDto updateAccount(int id, AccountUpdateDto accountUpdateDto){
        Account account = getAccountById(id);
        account.setName(accountUpdateDto.getName());
        return accountMapper.fromAccountToAccountResponse(accountRepo.save(account));
    }

    public AccountResponseDto deactivateAccount(int id){
        Account account=getAccountById(id);
        account.setActivated(false);
        return accountMapper.fromAccountToAccountResponse(accountRepo.save(account));
    }

    public AccountResponseDto getAccountDetails(int id){
        Account account = getAccountById(id);
        return accountMapper.fromAccountToAccountResponse(account);
    }

    public Account creditAccount(Account account, int amount){
        account.setBalance(account.getBalance()+amount);
        return accountRepo.save(account);
    }

    public Account debitAccount(Account account, int amount){
        if(!validateAmount(account,amount))
            throw new InsufficientBalanceException(account.getId());
        account.setBalance(account.getBalance()-amount);
        return accountRepo.save(account);
    }
}
