package com.cs246.EzBudget;

public class BillItem {
    private String name;
    private boolean paid;
    private double amount;


    public BillItem(String name, double amount, boolean paid) {
        this.name = name;
        this.paid = paid;
        this.amount = amount;
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
}
