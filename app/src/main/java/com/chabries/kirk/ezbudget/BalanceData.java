package com.chabries.kirk.ezbudget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BalanceData {


    /**
     * FIELDS FOR DATABASE DESCRIPTION
     */
    public static final String BALANCEDATA_TABLE_NAME = "balanceData";
    public static final String BALANCEDATA_COLUMN_ID = "id";
    public static final String BALANCEDATA_COLUMN_DUE_DATE = "date";
    public static final String BALANCEDATA_COLUMN_PAYMENT_DATE = "paymentDate";
    public static final String BALANCEDATA_COLUMN_DESCRIPTION = "description";
    public static final String BALANCEDATA_COLUMN_CATEGORY = "idCategory";
    public static final String BALANCEDATA_COLUMN_VALUE = "value";
    public static final String BALANCEDATA_COLUMN_STATUS = "status";
    public static final String BALANCEDATA_COLUMN_TIMESTAMP = "modificationDateTime";


    /**
     * FIELDS FOR DATABASE DESCRIPTION OF THE RECURRENT TABLE
     */
    public static final String BALANCEDATAREC_TABLE_NAME = "balanceDataRec";
    public static final String BALANCEDATAREC_COLUMN_ID = "id";
    public static final String BALANCEDATAREC_COLUMN_DUE_DATE = "date";
    public static final String BALANCEDATAREC_COLUMN_DESCRIPTION = "description";
    public static final String BALANCEDATAREC_COLUMN_CATEGORY = "idCategory";
    public static final String BALANCEDATAREC_COLUMN_VALUE = "value";
    public static final String BALANCEDATAREC_COLUMN_PERIOD = "period";


    //
    // Fields
    //

    private Date myDate;  //due date if it is a bill or the day we eill receive if it is income
    private Date myPaymentDate; //the date of payment if it is a bill
    private String myDescription;
    private double myValue;
    private Category myCategory;
    private boolean isRecurrent;

    /**

     * to indicate that :
     * if it is a bill, it was already paid
     * if it is an income, it was already received
     * 0: unpaid/unreceived
     * 1: paid/received
     * */

    private int myStatus;

    //
    // Constructors
    //
    public BalanceData () {
        this.myStatus = PAY_STATUS.UNKNOWN;
    }

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of the payment date
     * @param newVar the new value of payment date
     */
    public void setPaymentDate (Date newVar) {
        myPaymentDate = newVar;
    }

     /**
     * Get the value of payment date in the format "dd/MM/yyyy
     * @return the value of payment date
     */
    public String getPaymentDate () {
        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        if(myPaymentDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(this.myPaymentDate);
        }
        return reportDate;


    }

     /** Set the value of myDate
     * @param newVar the new value of myDate
     */
    public void setDate (Date newVar) {
        myDate = newVar;
    }

    /**
     * Get the value of myDate in the String format dd/MM/yyyy
     * @return the value of myDate
     */
    public String getDate () {
        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        if(myDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(myDate);
        }
        return reportDate;
    }

    /**
     * Set the value of myDescription
     * @param newVar the new value of myDescription
     */
    public void setDescription (String newVar) {
        myDescription = newVar;
    }

    /**
     * Get the value of myDescription
     * @return the value of myDescription
     */
    public String getDescription () {
        return myDescription;
    }

    /**
     * Set the value of myValue
     * @param newVar the new value of myValue
     */
    public void setValue (double newVar) {
        myValue = newVar;
    }

    /**
     * Get the value of myValue
     * @return the value of myValue
     */
    public double getValue () {
        return myValue;
    }

    /**
     * Set the value of myCategory
     * @param newVar the new value of myCategory
     */
    public void setCategory (Category newVar) {
        myCategory = newVar;
    }

    /**
     * Get the value of myCategory
     * @return the value of myCategory
     */
    public Category getCategory () {
        return myCategory;
    }

    /**
     * Set the value of isRecurrent
     * @param newVar the new value of isRecurrent
     */
    public void setRecurrent (boolean newVar) {
        isRecurrent = newVar;
    }

    /**
     * Get the value of isRecurrent
     * @return the value of isRecurrent
     */
    public boolean IsRecurrent () {
        return isRecurrent;
    }

    /**
     * Set the value of status
     * to indicate that :
     * if it is a bill, it was already paid
     * if it is an income, it was already received
     * @param newVar the new value of the status
     */
    public void setStatus (Integer newVar) {
        this.myStatus = newVar;
    }

    /**
     * Get the value of status
     * to indicate that :
     * if it is a bill, it was already paid
     * if it is an income, it was already received
     * @return the value of isPaidReceived
     */
    public int getStatus () {
        return this.myStatus;
    }

    /**
     * To know if it is a bill, it was already paid
     * if it is an income, if it is already received
     */

    public boolean isPaidReceived(){

        return this.myStatus == PAY_STATUS.PAID_RECEIVED;
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

    public boolean isCredit() {
        return myCategory.isCredit();
    }

    public boolean isDebit(){
        return myCategory.isDebit();
    }


}

