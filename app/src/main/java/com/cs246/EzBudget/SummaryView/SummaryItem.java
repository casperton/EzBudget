package com.cs246.EzBudget.SummaryView;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.PAY_STATUS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * This class is a temporary modal class created to store expense and income items
 * that are stored in the database. These are all added as objects in the list sent
 * to the summary view to be displayed
 */
public class SummaryItem implements Comparable<SummaryItem>{
    private BalanceData balData;
    private String name;
    private boolean paid;
    private double amount;
    private int type;
    private Date date; // Due date of bill, or date income is received
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
        // Convert string into date
        SimpleDateFormat foreignFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date converted_date = new Date() ;
        // Dates are stored initially as foreign format
        // Convert the strings to date in this format
        try {
            converted_date = foreignFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = converted_date;
    }

    public SummaryItem(BalanceData theBalData, int type) {
        this.balData = theBalData;

        this.name = theBalData.getDescription();
        this.paid = theBalData.isPaid();
        this.amount = theBalData.getValue();
        
        this.type = type;
        // Convert string into date
        SimpleDateFormat foreignFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date converted_date = new Date() ;
        // Dates are stored initially as foreign format
        // Convert the strings to date in this format
        try {
            converted_date = foreignFormat.parse(theBalData.getDueDateDatabase());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = converted_date;
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
     * Gets the date (due_date or date of payment)
     * @return  String date value for the item
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method will return the total needed to cover
     * bills during the period if the item is income
     *
     * @return
     */
    public double getTotal_needed() {
        return total_needed;
    }

    /**
     * This method will store the total needed for
     * expenses that will be taken out of this income
     * item for the given period
     *
     * @param total_needed
     */
    public void setTotal_needed(double total_needed) {
        this.total_needed = total_needed;
    }

    public String getUsDate() {
        SimpleDateFormat formatter =  new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(this.date);
    }

    public String getForeignDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(this.date);
    }

    @Override
    public int compareTo(SummaryItem o) {
        return getDate().compareTo(o.getDate());
    }
}
