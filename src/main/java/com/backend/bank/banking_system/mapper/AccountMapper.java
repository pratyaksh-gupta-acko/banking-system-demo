package com.backend.bank.banking_system.mapper;

import com.backend.bank.banking_system.dtos.request.AccountRequestDto;
import com.backend.bank.banking_system.dtos.response.AccountResponseDto;
import com.backend.bank.banking_system.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toAccount(AccountRequestDto accountRequestDto){
        Account account = new Account();
        account.setName(accountRequestDto.getName());
        account.setBalance(accountRequestDto.getBalance());
        return account;
    }

    public AccountResponseDto fromAccountToAccountResponse(Account account){
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(account.getId());
        accountResponseDto.setBalance(account.getBalance());
        accountResponseDto.setActivated(account.isActivated());
        accountResponseDto.setName(account.getName());
        return accountResponseDto;
    }
}
