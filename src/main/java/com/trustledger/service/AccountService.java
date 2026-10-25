
package com.trustledger.service;

import com.trustledger.entity.Account;
import com.trustledger.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    // Constructor injection
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {

        // Check whether this account number is already registered.
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Account number already exists"
            );
        }

        // Save the account only if the number is unique.
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

    public Account updateAccount(Long id, Account updatedAccount) {

        // Find the existing account or return 404.
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found"
                ));

        // Check whether another account uses the requested account number.
        boolean numberUsedByAnotherAccount =
                accountRepository.existsByAccountNumber(
                        updatedAccount.getAccountNumber()
                )
                        && !existingAccount.getAccountNumber()
                        .equals(updatedAccount.getAccountNumber());

        if (numberUsedByAnotherAccount) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Account number already exists"
            );
        }

        // Update the existing record rather than creating a new one.
        existingAccount.setAccountNumber(updatedAccount.getAccountNumber());
        existingAccount.setAccountHolderName(
                updatedAccount.getAccountHolderName()
        );
        existingAccount.setBankName(updatedAccount.getBankName());

        return accountRepository.save(existingAccount);
    }

}
