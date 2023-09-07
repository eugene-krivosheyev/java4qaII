package com.tcs.edu.banking.account.service;

import com.tcs.edu.banking.account.domain.Account;
import com.tcs.edu.banking.account.persist.AccountRepository;

public class AccountService {
    private final AccountRepository accounts;

    public AccountService(AccountRepository accounts) {
        this.accounts = accounts;
    }

    public int create(Account account) {
        return accounts.create(account);
    }

    public void transfer(int from, int to, double amount) {
        final var fromAccount = accounts.findById(from);
        final var toAccount = accounts.findById(to);

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        accounts.save(fromAccount);
        accounts.save(toAccount);
    }
}
