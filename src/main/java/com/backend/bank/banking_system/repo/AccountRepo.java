package com.backend.bank.banking_system.repo;

import com.backend.bank.banking_system.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,Integer> {
}
