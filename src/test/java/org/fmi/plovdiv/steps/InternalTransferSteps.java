package org.fmi.plovdiv.steps;

import org.fmi.plovdiv.Account;
import org.fmi.plovdiv.AccountService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class InternalTransferSteps {

    private AccountService accountService;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private double amount;
    private String errorMessage;

    public InternalTransferSteps() {
        this.accountService = new AccountService();
    }

    @Given("потребителят е влязъл с потребителско име {string} и парола {string}")
    public void login(String username, String password) {
        System.out.println("Потребителят влезе с потребителско име " + username + " и парола " + password);
    }

    @When("потребителят навигира до секцията {string}")
    public void navigateToSection(String section) {
        System.out.println("Потребителят навигира до секцията " + section);
    }

    @When("потребителят избере изходяща сметка {string}")
    public void selectSourceAccount(String accountNumber) {
        this.sourceAccountNumber = accountNumber;
        System.out.println("Потребителят избра изходяща сметка " + accountNumber);
    }

    @When("потребителят избере получателска сметка {string}")
    public void selectDestinationAccount(String accountNumber) {
        this.destinationAccountNumber = accountNumber;
        System.out.println("Потребителят избра получателска сметка " + accountNumber);
    }

    @When("потребителят въведе сума {string}")
    public void enterAmount(String amount) {
        try {
            this.amount = Double.parseDouble(amount.replace(" BGN", ""));
        } catch (NumberFormatException e) {
            this.errorMessage = "Невалидна сума";
        }
        System.out.println("Потребителят въведе сума " + amount);
    }

    @When("потребителят потвърди превода")
    public void confirmTransfer() {
        Account sourceAccount = accountService.getAccountByNumber(sourceAccountNumber);
        Account destinationAccount = accountService.getAccountByNumber(destinationAccountNumber);

        if (sourceAccount == null) {
            this.errorMessage = "Невалиден номер на сметка";
            return;
        }

        if (destinationAccount == null) {
            this.errorMessage = "Невалиден номер на сметка";
            return;
        }

        if (sourceAccount.getBalance() < amount) {
            this.errorMessage = "Недостатъчна наличност";
            return;
        }

        accountService.transferAmount(sourceAccountNumber, destinationAccountNumber, amount);
        System.out.println("Потребителят потвърди превода");
    }

    @Then("потребителят трябва да види съобщение за потвърждение с детайлите на превода")
    public void verifyTransferConfirmation() {
        if (errorMessage == null) {
            System.out.println("Потребителят вижда съобщение за потвърждение с детайлите на превода");
        }
    }

    @Then("потребителят трябва да види съобщение за грешка {string}")
    public void verifyTransferErrorMessage(String message) {
        if (errorMessage != null && errorMessage.equals(message)) {
            System.out.println("Потребителят вижда съобщение за грешка: " + message);
        } else {
            System.out.println("Неочаквано съобщение за грешка: " + errorMessage);
        }
    }
}
