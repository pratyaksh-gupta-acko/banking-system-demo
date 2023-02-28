package com.backend.bank.banking_system.repo;

import com.backend.bank.banking_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {
    List<Transaction> getAllByCreditedTo(int accountId);
    List<Transaction> getAllByDebitedFrom(int accountId);
}
