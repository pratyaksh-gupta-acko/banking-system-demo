package com.backend.bank.banking_system.service;

import com.backend.bank.banking_system.dtos.request.AccountRequestDto;
import com.backend.bank.banking_system.dtos.response.AccountResponseDto;
import com.backend.bank.banking_system.dtos.response.TransactionResponseDto;
import com.backend.bank.banking_system.enums.TransactionType;
import com.backend.bank.banking_system.exception.AccountDeactivatedException;
import com.backend.bank.banking_system.exception.UserNotAvailableException;
import com.backend.bank.banking_system.mapper.AccountMapper;
import com.backend.bank.banking_system.model.Account;
import com.backend.bank.banking_system.model.Transaction;
import com.backend.bank.banking_system.repo.AccountRepo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static java.sql.Types.NULL;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepo mockAccountRepo;
    @Mock
    private AccountMapper mockAccountMapper;
    @Mock
    private TransactionService transactionService;

    private Account actualAccount, expectedAccount;
    private AccountRequestDto accountRequestDto;
    private AccountResponseDto accountResponseDto;
    private Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
    private List<TransactionResponseDto>expectedTransactionList = new ArrayList<>();
    List<Transaction>transactionList = new ArrayList<>();
    int transactionAmount = 1000;

    @BeforeEach
    public void initializeAccount(){
        actualAccount = new Account();
        actualAccount.setId(1);
        actualAccount.setBalance(3000);
        actualAccount.setName("Pratyaksh");
        actualAccount.setActivated(true);

        expectedAccount = new Account();
        expectedAccount.setId(1);
        expectedAccount.setBalance(3000);
        expectedAccount.setName("Pratyaksh");
        expectedAccount.setActivated(true);

        accountRequestDto = new AccountRequestDto("Pratyaksh", 1000);
        accountResponseDto = new AccountResponseDto(1,true,"Pratyaksh", 1000);
    }

    public void setAccountTransactionList(){
        Account testAccount = new Account(2, true, "Udit", 500);
        List<Transaction> debitList = new ArrayList<>();
        List<Transaction> creditList = new ArrayList<>();
        Transaction transfer1 = new Transaction(1, timestamp, actualAccount, testAccount, 100, TransactionType.TRANSFER);
        Transaction transfer2 = new Transaction(2, timestamp, testAccount, actualAccount, 50, TransactionType.TRANSFER);
        Transaction debit = new Transaction(3, timestamp, actualAccount, 200, TransactionType.DEBIT);
        Transaction credit = new Transaction(4, timestamp, testAccount, 150, TransactionType.CREDIT);
        debitList.add(transfer1);
        creditList.add(transfer2);
        debitList.add(debit);
        creditList.add(credit);
        actualAccount.setDebit(debitList);
        actualAccount.setCredit(creditList);
        transactionList.addAll(debitList);
        transactionList.addAll(creditList);


    }

    public void setExpectedTransactionList(){
        TransactionResponseDto transactionResponseDtoTransfer1 = new TransactionResponseDto(1, timestamp, 100, TransactionType.TRANSFER, 1, 2);
        TransactionResponseDto transactionResponseDtoTransfer2 = new TransactionResponseDto(1, timestamp, 50, TransactionType.TRANSFER, 2, 1);
        TransactionResponseDto transactionResponseDtoDebit = new TransactionResponseDto(1, timestamp, 100, TransactionType.DEBIT, 1, NULL);
        TransactionResponseDto transactionResponseDtoCredit = new TransactionResponseDto(1, timestamp, 100, TransactionType.CREDIT, NULL, 2);
        expectedTransactionList.add(transactionResponseDtoTransfer1);
        expectedTransactionList.add(transactionResponseDtoDebit);
        expectedTransactionList.add(transactionResponseDtoTransfer2);
        expectedTransactionList.add(transactionResponseDtoCredit);
    }

    @Test
    void testGetAccountById_whenUserIsInvalid_thenThrowException() {
        assertThrows(UserNotAvailableException.class, () -> {
            accountService.getAccountById(2);
        });
    }

    @Test
    void testGetAccountById_whenUserIsDeactivated_thenThrowException() {
        actualAccount.setActivated(false);
        when(mockAccountRepo.findById(1)).thenReturn(Optional.of(actualAccount));
        assertThrows(AccountDeactivatedException.class, () -> {
            accountService.getAccountById(1);
        });
    }

    @Test
    void testGetAccountById_whenUserIsValid_thenReturnAccount() {
        when(mockAccountRepo.findById(1)).thenReturn(Optional.of(actualAccount));
        assertEquals(expectedAccount, accountService.getAccountById(1));
    }

    @Test
    void testValidateAmount_whenAmountIsValid_thenReturnTrue() {
        assertTrue(accountService.validateAmount(actualAccount,2000));
    }

    @Test
    void testValidateAmount_whenAmountExceedsDebitAccountBalance_thenReturnFalse(){
        assertFalse(accountService.validateAmount(actualAccount,4000));
    }

    @Test
    void testAddAccount_whenAccountIsValid_thenReturnAccount() {
//        AccountResponseDto expectedAccountResponseDto = new AccountResponseDto(1, true, "Pratyaksh", 1000);

        when(mockAccountRepo.save(actualAccount)).thenReturn(actualAccount);
        when(mockAccountMapper.toAccount(accountRequestDto)).thenReturn(actualAccount);
        when(mockAccountMapper.fromAccountToAccountResponse(actualAccount)).thenReturn(accountResponseDto);

        assertEquals(accountResponseDto, accountService.addAccount(accountRequestDto));
    }

    @Test
    void testGetTransaction_whenAccountIsValid_thenReturnTransactionList(){
        setAccountTransactionList();
        setExpectedTransactionList();

        AccountService spyAccountService = Mockito.spy(accountService);
        Mockito.doReturn(actualAccount).when(spyAccountService).getAccountById(1);

        when(transactionService.fromTransactionListToTransactionResponseList(transactionList)).thenReturn(expectedTransactionList);

        assertEquals(expectedTransactionList, spyAccountService.getTransactions(1));
    }

    @Test
    void testDeactivateAccount_whenAccountIsValid() {
        Account changedAccount = actualAccount;
        changedAccount.setActivated(false);
        expectedAccount.setActivated(false);

        AccountService spyAccountService = Mockito.spy(accountService);
        Mockito.doReturn(actualAccount).when(spyAccountService).getAccountById(1);
        when(mockAccountRepo.save(changedAccount)).then(AdditionalAnswers.returnsFirstArg());
        when(mockAccountMapper.fromAccountToAccountResponse(changedAccount)).thenReturn(accountResponseDto);

        assertEquals(accountResponseDto, spyAccountService.deactivateAccount(1));
    }

    @Test
    void testCreditAccount_whenAccountIsValid_thenReturnAccount() {
        expectedAccount.setBalance(4000);
        when(mockAccountRepo.save(expectedAccount)).then(AdditionalAnswers.returnsFirstArg());
        assertEquals(expectedAccount, accountService.creditAccount(actualAccount, transactionAmount));
    }

    @Test
    void testDebitAccount_whenAccountIsValid_thenReturnAccount() {
        expectedAccount.setBalance(2000);
        when(mockAccountRepo.save(expectedAccount)).then(AdditionalAnswers.returnsFirstArg());
        assertEquals(expectedAccount, accountService.debitAccount(actualAccount, transactionAmount));
    }
}