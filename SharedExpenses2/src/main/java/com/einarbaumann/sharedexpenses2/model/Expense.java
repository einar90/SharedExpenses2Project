package com.einarbaumann.sharedexpenses2.model;

import android.provider.Contacts;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by Einar on 12.07.13.
 */
public class Expense {

    private long timestamp;
    private Person paidBy;
    private double amount;
    private String description;

    public Expense(Person paidBy, double amount, String description) {
        timestamp = Calendar.getInstance().getTimeInMillis();
        this.paidBy = paidBy;
        this.amount = amount;
        this.description = description;
    }

    /*
    The following methods are used to display the info in a list
     */
    public String getName() {
        return Names.getInstance().getName(paidBy);
    }

    public String getAmountString() {
        return Double.toString(amount);
    }

    public String getDescription() {
        return description;
    }

    public String getDateString() {
        String timestampString = DateFormat.format("dd", timestamp).toString();
        return timestampString;
    }

    public String getMonthString() {
        String timestampString = DateFormat.format("MMM", timestamp).toString();
        return timestampString;
    }

}
