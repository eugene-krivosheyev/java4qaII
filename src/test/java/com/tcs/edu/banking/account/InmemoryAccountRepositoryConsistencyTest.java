package com.tcs.edu.banking.account;

import com.tcs.edu.banking.account.domain.CreditAccount;
import com.tcs.edu.banking.account.persist.InmemoryRepository;
import com.tcs.edu.banking.error.ProcessingException;
import org.junit.jupiter.api.Test;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class InmemoryAccountRepositoryConsistencyTest {
    @Test
    public void shouldNotAccountDoublingWhenMutateId() throws ProcessingException {
        final var accountRepo = new InmemoryRepository();
        final var account = new CreditAccount();
        account.setId(1);
        accountRepo.save(account);
        assumeFalse(isNull(accountRepo.findById(1)));
        assumeTrue(accountRepo.findAll().size() == 1);

        account.setId(2);
        accountRepo.save(account);

        assertTrue(accountRepo.findAll().size() == 1);
    }

}
