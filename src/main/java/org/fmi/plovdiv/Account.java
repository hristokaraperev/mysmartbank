package org.fmi.plovdiv;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String accountType;
    private double balance;
    private List<Transaction> transactions;

    public Account(String accountNumber, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        balance += transaction.getAmount();
    }

    public void deductAmount(double amount) {
        this.balance -= amount;
    }
}