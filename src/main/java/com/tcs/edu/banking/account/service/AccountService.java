package com.tcs.edu.banking.account.service;

import com.tcs.edu.banking.account.domain.Account;
import com.tcs.edu.banking.account.persist.AccountRepository;
import com.tcs.edu.banking.error.ProcessingException;

public class AccountService {
    private final AccountRepository accounts;

    public AccountService(AccountRepository accounts) {
        this.accounts = accounts;
    }

    /**
     * @param account to create
     * @return id of created account
     */
    public int create(Account account) throws ProcessingException {
        return accounts.create(account);
    }


    public void transfer(int from, int to, double amount) {
        //region find accounts
        final var fromAccount = accounts.findById(from);
        final var toAccount = accounts.findById(to);

        //region update account states
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        //region save updated accounts
        accounts.save(fromAccount);
        accounts.save(toAccount);
    }
}
