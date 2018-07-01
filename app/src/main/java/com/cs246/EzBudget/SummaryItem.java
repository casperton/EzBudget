package com.cs246.EzBudget;

public class SummaryItem {
    private String name;
    private boolean paid;
    private double amount;
    private int type;


    public SummaryItem(String name, double amount, boolean paid, int type) {
        this.name = name;
        this.paid = paid;
        this.amount = amount;
        this.type = type;
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
}
