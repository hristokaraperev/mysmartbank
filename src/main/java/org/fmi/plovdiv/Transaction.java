package org.fmi.plovdiv;

import java.util.Date;

public class Transaction {
    private Date date;
    private double amount;
    private String description;

    public Transaction(Date date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
