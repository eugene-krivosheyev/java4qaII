package com.tcs.edu.banking.account.domain;

public class DepositAccount extends AbstractAccount {
    public void superPuperOp() {

    }

    @Override
    public void withdraw(double amount) {
        if (amount > getAmount()) throw new IllegalArgumentException("Not enough money");

        super.withdraw(amount);
    }
}
