package org.fmi.plovdiv.steps;

import java.util.List;

import org.fmi.plovdiv.Account;
import org.fmi.plovdiv.AccountService;
import org.fmi.plovdiv.Transaction;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class ManageAccountsSteps {

    private AccountService accountService;
    private List<Account> accounts;
    private Account selectedAccount;
    private List<Transaction> filteredTransactions;
    private String errorMessage;

    public ManageAccountsSteps() {
        this.accountService = new AccountService();
    }

    @Given("потребителят влиза с потребителско име {string} и парола {string}")
    public void loginManageAccounts(String username, String password) {
        System.out.println("Потребителят влезе с потребителско име " + username + " и парола " + password);
    }

    @When("потребителят стига до секцията {string}")
    public void navigateToSectionAccounts(String section) {
        if (section.equals("Моите сметки")) {
            accounts = accountService.getAllAccounts();
        }
        System.out.println("Потребителят навигира до секцията " + section);
    }

    @Then("потребителят трябва да види списък с всички свои банкови сметки с текущите им салда")
    public void verifyAllAccountsListed() {
        for (Account account : accounts) {
            System.out.println("Сметка: " + account.getAccountNumber() + ", Салдо: " + account.getBalance());
        }
    }

    @When("потребителят избере сметка {string}")
    public void selectAccount(String accountNumber) {
        selectedAccount = accountService.getAccountByNumber(accountNumber);
        if (selectedAccount == null) {
            errorMessage = "Нямате достъп до тази сметка";
        }
        System.out.println("Потребителят избра сметка " + accountNumber);
    }

    @Then("потребителят трябва да види детайлите за сметката, включително номер на сметка, салдо, тип на сметката и история на транзакциите")
    public void verifyAccountDetails() {
        if (selectedAccount != null) {
            System.out.println("Номер на сметка: " + selectedAccount.getAccountNumber());
            System.out.println("Салдо: " + selectedAccount.getBalance());
            System.out.println("Тип на сметка: " + selectedAccount.getAccountType());
            for (Transaction transaction : selectedAccount.getTransactions()) {
                System.out.println("Транзакция: " + transaction.getDate() + ", Сума: " + transaction.getAmount() + ", Описание: " + transaction.getDescription());
            }
        }
    }

    @When("потребителят филтрира транзакциите по последните {string}")
    public void filterTransactionsByPeriod(String period) {
        if (selectedAccount != null) {
            filteredTransactions = accountService.filterTransactionsByPeriod(selectedAccount.getAccountNumber(), period);
        }
        System.out.println("Потребителят филтрира транзакциите по последните " + period);
    }

    @Then("потребителят трябва да види филтрирана история на транзакциите за последните {int} дни")
    public void verifyFilteredTransactions(Integer period) {
        if (filteredTransactions != null) {
            for (Transaction transaction : filteredTransactions) {
                System.out.println("Транзакция: " + transaction.getDate() + ", Сума: " + transaction.getAmount() + ", Описание: " + transaction.getDescription());
            }
        }
    }

    @Then("потребителят трябва да види съобщение за грешка при управление {string}")
    public void verifyManageAccountsErrorMessage(String message) {
        if (errorMessage != null) {
            System.out.println("Съобщение за грешка: " + errorMessage);
        }
    }

    @Then("потребителят трябва да види детайлите за сметката и съобщение {string}")
    public void verifyAccountDetailsWithMessage(String message) {
        if (selectedAccount != null) {
            verifyAccountDetails();
            System.out.println("Съобщение: " + message);
        }
    }
}