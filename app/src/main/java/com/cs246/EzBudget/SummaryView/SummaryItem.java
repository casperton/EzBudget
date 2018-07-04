package com.cs246.EzBudget.SummaryView;

/**
 * <p>
 * This class is a temporary modal class created to store expense and income items
 * that are stored in the database. These are all added as objects in the list sent
 * to the summary view to be displayed
 */
public class SummaryItem {
    private String name;
    private boolean paid;
    private double amount;
    private int type;
    private String date; // Due date of bill, or date income is received
    private double total_needed;

    /**
     * Default contsructor method
     * @param name      The name of the item
     * @param date      The due_date of the item
     * @param amount    The amount of the item
     * @param paid      For expense items only to mark if paid or not
     * @param type      Indicates whether it is an expense (0) or income (1)
     */
    public SummaryItem(String name, String date, double amount, boolean paid, int type) {
        this.name = name;
        this.paid = paid;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    /**
     * Gets the name of the item
     * @return  Returns the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets whether or not the expense item is paid
     * @return  Boolean value true if the expense is marked as paid
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Gets the amount of the item
     * @return Double value of the amount for the item
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the type (expense or income) for the item
     * @return  Int value 0 for expenses, or 1 for income items
     */
    public int getType() {
        return type;
    }

    /**
     * G et the date (due_date or date of payment)
     * @return  String date value for the item
     */
    public String getDate() {
        return date;
    }

}
