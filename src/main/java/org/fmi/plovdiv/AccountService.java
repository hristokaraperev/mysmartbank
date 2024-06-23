package org.fmi.plovdiv;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountService {
    private List<Account> accounts;

    public AccountService() {
        this.accounts = new ArrayList<>();
        accounts.add(new Account("BG12STSA93000000012345", "Текуща", 5000));
        accounts.add(new Account("BG12STSA93000000054321", "Спестовна", 2000));
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getAccountByNumber(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public void addTransaction(String accountNumber, Transaction transaction) {
        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            account.addTransaction(transaction);
        }
    }

    public void transferAmount(String sourceAccountNumber, String destinationAccountNumber, double amount) {
        Account sourceAccount = getAccountByNumber(sourceAccountNumber);
        Account destinationAccount = getAccountByNumber(destinationAccountNumber);
        if (sourceAccount != null && destinationAccount != null && sourceAccount.getBalance() >= amount) {
            sourceAccount.deductAmount(amount);
            destinationAccount.addTransaction(new Transaction(new Date(), amount, "Transfer from " + sourceAccountNumber));
        }
    }

    public List<Transaction> filterTransactionsByPeriod(String accountNumber, String period) {
        Account account = getAccountByNumber(accountNumber);
        if (account == null) {
            return new ArrayList<>();
        }

        Date startDate = getStartDateForPeriod(period);
        return account.getTransactions().stream()
                .filter(transaction -> transaction.getDate().after(startDate))
                .collect(Collectors.toList());
    }

    private Date getStartDateForPeriod(String period) {
        Calendar calendar = Calendar.getInstance();
        switch (period.toLowerCase()) {
            case "30 дни":
                calendar.add(Calendar.DAY_OF_YEAR, -30);
                break;
            case "3 месеца":
                calendar.add(Calendar.MONTH, -3);
                break;
            default:
                calendar.add(Calendar.DAY_OF_YEAR, -30);
                break;
        }
        return calendar.getTime();
    }
}