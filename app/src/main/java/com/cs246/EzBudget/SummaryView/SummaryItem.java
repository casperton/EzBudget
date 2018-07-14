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
    private double expenseTotal;
    private double unpaidTotal;
    private double overageTotal;
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
    public double getExpenseTotal() {
        return expenseTotal;
    }

    /**
     * This method will store the total needed for
     * expenses that will be taken out of this income
     * item for the given period
     *
     * @param expenseTotal
     */
    public void setExpenseTotal(double expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    /**
     * This method will return the total of
     * unpaid bill items for the given
     * period
     *
     * @param unpaidTotal
     */
    public void setUnpaidTotal(double unpaidTotal) {
        this.unpaidTotal = unpaidTotal;
    }

    /**
     * This method will return the total that has already
     * beend paid for bills during the period if the item
     * is income
     *
     * @return
     */
    public double getUnpaidTotal() {
        return unpaidTotal;
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

    /**
     * This will store overage amounts to be carried for
     * pay periods that do not have enough money to cover
     * bills for that period
     * @return The total amount
     */
    public double getOverageTotal() {
        return overageTotal;
    }

    /**
     * This method will store an overage amount for a
     * pay period that has an overage of expenses
     * for that pay period it cannot cover
     * @param overageTotal
     */
    public void setOverageTotal(double overageTotal) {
        this.overageTotal = overageTotal;
    }

    /**
     * This will return the total of expenses
     * (unpaid or paid) as well as the overage
     * total
     * @return The combined total
     */
    public double getTotalCombined() {
        return expenseTotal + overageTotal;
    }

    /**
     * This method is used to calculate the total
     * remaining bills that have not been paid
     * plus the overage amount from other pay
     * periods
     * @return The combined unpaid and overages
     */
    public double getTotalUnpaidCombined() {
        return unpaidTotal + overageTotal;
    }

}
