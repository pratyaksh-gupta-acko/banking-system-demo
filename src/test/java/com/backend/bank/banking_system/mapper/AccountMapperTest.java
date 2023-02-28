package com.backend.bank.banking_system.mapper;

import com.backend.bank.banking_system.dtos.request.AccountRequestDto;
import com.backend.bank.banking_system.dtos.response.AccountResponseDto;
import com.backend.bank.banking_system.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccountMapperTest {
    @InjectMocks
    AccountMapper accountMapper;

    @Test
    void testFromAccountRequestDtoToAccount_whenRequestIsValid_thenReturnAccount() {
        AccountRequestDto accountRequestDto = new AccountRequestDto("Pratyaksh", 100);
        Account account = accountMapper.toAccount(accountRequestDto);
        assertEquals(100, account.getBalance());
        assertEquals("Pratyaksh", account.getName());
    }

    @Test
    void fromAccountToAccountResponseDto_whenAccountIsValid_thenReturnAccountRequestDto() {
        Account account = new Account(1,true,"Pratyaksh",100);
        AccountResponseDto expectedAccountResponseDto = new AccountResponseDto(1, true, "Pratyaksh", 100);
        assertEquals(expectedAccountResponseDto, accountMapper.fromAccountToAccountResponse(account));
    }
}