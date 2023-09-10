package com.tcs.edu.banking.account.domain;

/**
 * Инкапсулирует:
 * - id: идентификатор счета
 * - amount: состояние счета
 * - общую для счетов всех типов реализацию: мутабельное свойство id, чтение состояния, снятия и зачисления средств.
 */
public abstract class AbstractAccount implements Account {
    private int id;
    private double amount;

    public AbstractAccount(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public AbstractAccount(int id) {
        this(id, 0);
    }

    public AbstractAccount() {
        this(0, 0);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public void withdraw(double amount) {
        this.amount -= amount;
    }

    @Override
    public void deposit(double amount) {
        this.amount += amount;
    }
}
