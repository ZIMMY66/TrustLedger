
package com.trustledger.service;

import com.trustledger.entity.Account;
import com.trustledger.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    // Constructor injection
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create and save a new account
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Retrieve all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Retrieve an account by its ID
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
}
