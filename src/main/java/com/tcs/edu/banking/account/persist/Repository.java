package com.tcs.edu.banking.account.persist;

import com.tcs.edu.banking.account.domain.Account;

import java.util.Collection;

public interface Repository<T ,K> {
    K create(T account);
    T findById(K fromId);
    Collection<T> findAll();
    void save(T account);
}
