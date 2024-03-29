package com.tcs.edu.banking.account.service;

import com.tcs.edu.banking.account.domain.Account;
import com.tcs.edu.banking.account.domain.CreditAccount;
import com.tcs.edu.banking.account.persist.Repository;

public class ReportingService {
    private final Repository accountRepository;

    public ReportingService(Repository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * @return report according Requirements.
     */
    public String generate() {
        var report = new StringBuilder();
        for (Account account : accountRepository.findAll()) {
            var type = account instanceof CreditAccount ? "CREDIT" : "DEPOSIT";

            report.append(account.getId())
                .append(": ")
                .append(type)
                .append(" ")
                .append(account.getAmount())
                .append("\n");
        }
        return report.toString();
    }
}
