package com.cs246.EzBudget.SummaryView;

import android.content.Context;
import android.util.Log;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.DateHandler;
import com.cs246.EzBudget.PAY_STATUS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * This class is stores expense and income items from the database
 * These are all added as BalanceData objects in the list sent
 * to the summary view to be displayed
 */
public class SummaryItem implements Comparable<SummaryItem>{
    private BalanceData balData;
    private String name;
    private double amount;
    private int type;
    private double total_needed;
    private double total_paid;
    Context myContext;

    /**
     * Default constructor method
     * @param theContext The Context to be used by Database Operations
     * @param theBalData The BalanceData (Transactions Table)
     * @param type Credit or Debit
     */
    public SummaryItem(Context theContext,BalanceData theBalData, int type) {

        this.balData = theBalData;
        this.name = theBalData.getDescription();
        this.amount = theBalData.getAmount();
        this.myContext = theContext;
        this.type = type;




    }


    /**
     * Gets the BalanceData Record
     * @return the Balance Data Record.
     */
    public BalanceData getBalData(){
        return this.balData;
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
        return balData.isPaid();
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
        return balData.getDueDate();
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

    /**
     * This method will store the total paid for
     * bills out of this income item for the given
     * period
     *
     * @param total_paid
     */
    public void setTotal_paid(double total_paid) {
        this.total_paid = total_paid;
    }

    /**
     * This method will return the total that has already
     * beend paid for bills during the period if the item
     * is income
     *
     * @return
     */
    public double getTotal_paid() {
        return total_paid;
    }

    /**
     * This method will return the date in US format mm/dd/yyyy
     * @return Formatted date as string
     */
    public String getUsDate() {
        return balData.getDueDateHuman();
    }

    /**
     * This method will return the date in non-US format yyyy-mm-dd (database Format)
     * @return Formatted date as string
     */
    public String getForeignDate() {
        return balData.getDueDateDatabase();
    }

    /**
     * This method is used for sorting the objects in a list
     * by date
     * @param o The item to compare against
     * @return  The item sorted by date
     */
    @Override
    public int compareTo(SummaryItem o) {
        return getDate().compareTo(o.getDate());
    }

    /**
     * Set this BalanceData item as paid in the database
     * and in this object
     */
    public void setPaid() {
        balData.setPaid();
        DBBalanceData myDB = new DBBalanceData(myContext);
        myDB.update(balData.getID(),balData);
        Log.d("SummaryItem", "Marked the following item as paid: " + this.name);

    }

    /**
     * Set this BalanceData item as unpaid in the database
     * and in this object
     */
    public void resetPaid() {
        balData.resetPaid();
        DBBalanceData myDB = new DBBalanceData(myContext);
        myDB.update(balData.getID(),balData);

    }
}
