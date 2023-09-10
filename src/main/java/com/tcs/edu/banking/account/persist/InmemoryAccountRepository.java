package com.tcs.edu.banking.account.persist;

import com.tcs.edu.banking.account.domain.Account;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Простейшая in-memory реализация.
 * @see java.util.Map
 * @see java.util.HashMap
 * @see java.util.Map#put
 * @see java.util.Map#get
 * @see java.util.Map#values
 */
public class InmemoryAccountRepository implements AccountRepository {
    private int idSequence;
    private final Map<Integer, Account> accounts = new HashMap<>();

    @Override
    public int create(Account account) {
        final var id = ++idSequence;

        account.setId(id);
        save(account);

        return id;
    }

    @Override
    public Account findById(int id) {
        return accounts.get(id);
    }

    @Override
    public Collection<Account> findAll() {
        return accounts.values();
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getId(), account);
    }
}
