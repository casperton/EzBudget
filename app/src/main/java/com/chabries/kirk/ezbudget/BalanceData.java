package com.chabries.kirk.ezbudget;

import java.util.Date;

public class BalanceData {

    //
    // Fields
    //

    private Date myDate;
    private String myDescription;
    private double myValue;
    private Category myCategory;
    private boolean isRecurrent;

    /**

     * to indicate that :
     * if it is a bill, it was already paid
     * if it is an income, it was already received   */

    private boolean isPaidReceived;

    //
    // Constructors
    //
    public BalanceData () { }

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of myDate
     * @param newVar the new value of myDate
     */
    private void setDate (Date newVar) {
        myDate = newVar;
    }

    /**
     * Get the value of myDate
     * @return the value of myDate
     */
    private Date getDate () {
        return myDate;
    }

    /**
     * Set the value of myDescription
     * @param newVar the new value of myDescription
     */
    private void setDescription (String newVar) {
        myDescription = newVar;
    }

    /**
     * Get the value of myDescription
     * @return the value of myDescription
     */
    private String getDescription () {
        return myDescription;
    }

    /**
     * Set the value of myValue
     * @param newVar the new value of myValue
     */
    private void setValue (double newVar) {
        myValue = newVar;
    }

    /**
     * Get the value of myValue
     * @return the value of myValue
     */
    private double getValue () {
        return myValue;
    }

    /**
     * Set the value of myCategory
     * @param newVar the new value of myCategory
     */
    private void setCategory (Category newVar) {
        myCategory = newVar;
    }

    /**
     * Get the value of myCategory
     * @return the value of myCategory
     */
    private Category getCategory () {
        return myCategory;
    }

    /**
     * Set the value of isRecurrent
     * @param newVar the new value of isRecurrent
     */
    private void setRecurrent (boolean newVar) {
        isRecurrent = newVar;
    }

    /**
     * Get the value of isRecurrent
     * @return the value of isRecurrent
     */
    private boolean IsRecurrent () {
        return isRecurrent;
    }

    /**
     * Set the value of isPaidReceived
     * to indicate that :
     * if it is a bill, it was already paid
     * if it is an income, it was already received
     * @param newVar the new value of isPaidReceived
     */
    private void setIsPaidReceived (boolean newVar) {
        isPaidReceived = newVar;
    }

    /**
     * Get the value of isPaidReceived
     * to indicate that :
     * if it is a bill, it was already paid
     * if it is an income, it was already received
     * @return the value of isPaidReceived
     */
    private boolean getIsPaidReceived () {
        return isPaidReceived;
    }

    //
    // Other methods
    //



    /**
     * Return the type of operation:
     * 0 = credit opration
     * 1 = debit operation
     * 2 = just informative (nor credit or debit)
     * @return       int
     */
    public Integer getOperation()
    {
        return myCategory.getOperation();
    }



}

