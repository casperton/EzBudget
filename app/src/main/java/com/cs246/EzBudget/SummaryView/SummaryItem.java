package com.cs246.EzBudget.SummaryView;

public class SummaryItem {
    private String name;
    private boolean paid;
    private double amount;
    private int type;
    private String date; // Due date of bill, or date income is received
    private double total_needed;

    public SummaryItem(String name, String date, double amount, boolean paid, int type) {
        this.name = name;
        this.paid = paid;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public boolean isPaid() {
        return paid;
    }

    public double getAmount() {
        return amount;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

}
