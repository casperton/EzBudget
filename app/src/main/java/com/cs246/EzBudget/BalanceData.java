package com.cs246.EzBudget;

import android.util.Log;

import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.Database.DBCategory;
import com.cs246.EzBudget.Database.DBHelper;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This class holds data from two database tables: BalanceData and BalanceDataRec
 */
public class BalanceData {



    /**
     * FIELDS FOR DATABASE DESCRIPTION
     */
    public static final String BALANCEDATA_TABLE_NAME = "balanceData";
    public static final String BALANCEDATA_COLUMN_ID = "idBalData";
    public static final String BALANCEDATA_COLUMN_DUE_DATE = "dueDate";
    public static final String BALANCEDATA_COLUMN_PAYMENT_DATE = "paymentDate";
    public static final String BALANCEDATA_COLUMN_DESCRIPTION = "description";
    public static final String BALANCEDATA_COLUMN_CATEGORY = "idCategory";
    public static final String BALANCEDATA_COLUMN_VALUE = "value";
    public static final String BALANCEDATA_COLUMN_STATUS = "status";
    public static final String BALANCEDATA_COLUMN_TIMESTAMP = "modificationDateTime";
    public static final String BALANCEDATA_COLUMN_REC_ID = "balRecID";

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



    ////////////////DATA FOR TESTING //////////////////////////////

    //Most common budget payments
    /**
     Payment income.
     bu weekly
     */
    public static final String DB_REC_PAYMENT_DESCRIPTION = "Payment";
    public static final Integer DB_REC_PAYMENT_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_PAYMENT_VALUE = 1500.00;
    public static final Integer DB_REC_PAYMENT_RECURRENCE = RECURRENT.BI_WEEKLI;
    public static final String DB_REC_PAYMENT_DUEDAY = "10";
    public static final BalanceData DB_REC_PAYMENT = new BalanceData(DB_REC_PAYMENT_DESCRIPTION,
            DB_REC_PAYMENT_VALUE,
            DB_REC_PAYMENT_STATUS,
            Integer.parseInt(DB_REC_PAYMENT_DUEDAY),0,0,
            DB_REC_PAYMENT_RECURRENCE);

    /**
     Payment groceries.
     bI weekly
     */
    public static final String DB_REC_GROCERY_DESCRIPTION = "Grocery bill";
    public static final Integer DB_REC_GROCERY_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_GROCERY_VALUE = 15.00;
    public static final Integer DB_REC_GROCERY_RECURRENCE = RECURRENT.WEEKLY;
    public static final String DB_REC_GROCERY_DUEDAY = "15";
    public static final BalanceData DB_REC_GROCERY = new BalanceData(DB_REC_GROCERY_DESCRIPTION,
            DB_REC_GROCERY_VALUE,
            DB_REC_GROCERY_STATUS,
            Integer.parseInt(DB_REC_GROCERY_DUEDAY),0,0,
            DB_REC_GROCERY_RECURRENCE);

    /**
     Payment electric Energy.
     MONTHLY
     */
    public static final String DB_REC_ELETRICITY_DESCRIPTION = "Power bill";
    public static final Integer DB_REC_ELETRICITY_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_ELETRICITY_VALUE = 75.00;
    public static final Integer DB_REC_ELETRICITY_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_ELETRICITY_DUEDAY = "27";
    public static final BalanceData DB_REC_ELETRICITY = new BalanceData(DB_REC_ELETRICITY_DESCRIPTION,
            DB_REC_ELETRICITY_VALUE,
            DB_REC_ELETRICITY_STATUS,
            Integer.parseInt(DB_REC_ELETRICITY_DUEDAY),0,0,
            DB_REC_ELETRICITY_RECURRENCE);

    /**
     Payment Water.
     MONTHLY
     */
    public static final String DB_REC_WATER_DESCRIPTION = "Water bill";
    public static final Integer DB_REC_WATER_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_WATER_VALUE = 105.00;
    public static final Integer DB_REC_WATER_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_WATER_DUEDAY = "09";
    public static final BalanceData DB_REC_WATER = new BalanceData(DB_REC_WATER_DESCRIPTION,
            DB_REC_WATER_VALUE,
            DB_REC_WATER_STATUS,
            Integer.parseInt(DB_REC_WATER_DUEDAY),0,0,
            DB_REC_WATER_RECURRENCE);


    /**
     Payment Phone Bill.
     monthly
     */
    public static final String DB_REC_PHONE_DESCRIPTION = "Phone bill";
    public static final Integer DB_REC_PHONE_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_PHONE_VALUE = 65.00;
    public static final Integer DB_REC_PHONE_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_PHONE_DUEDAY = "16";
    public static final BalanceData DB_REC_PHONE = new BalanceData(DB_REC_PHONE_DESCRIPTION,
            DB_REC_PHONE_VALUE,
            DB_REC_PHONE_STATUS,
            Integer.parseInt(DB_REC_PHONE_DUEDAY),0,0,
            DB_REC_PHONE_RECURRENCE);

    /**
     Payment Car Bill.
     monthly
     */
    public static final String DB_REC_CAR_DESCRIPTION = "Car payment";
    public static final Integer DB_REC_CAR_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_CAR_VALUE = 330.00;
    public static final Integer DB_REC_CAR_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_CAR_DUEDAY = "22";
    public static final BalanceData DB_REC_CAR = new BalanceData(DB_REC_CAR_DESCRIPTION,
            DB_REC_CAR_VALUE,
            DB_REC_CAR_STATUS,
            Integer.parseInt(DB_REC_CAR_DUEDAY),0,0,
            DB_REC_CAR_RECURRENCE);

    /**
     Payment Rent.
     monthly
     */
    public static final String DB_REC_RENT_DESCRIPTION = "Rent";
    public static final Integer DB_REC_RENT_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_RENT_VALUE = 850.00;
    public static final Integer DB_REC_RENT_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_RENT_DUEDAY = "05";
    public static final BalanceData DB_REC_RENT = new BalanceData(DB_REC_RENT_DESCRIPTION,
            DB_REC_RENT_VALUE,
            DB_REC_RENT_STATUS,
            Integer.parseInt(DB_REC_RENT_DUEDAY),0,0,
            DB_REC_RENT_RECURRENCE);

    //
    // Fields
    //

    private Date myDate;  //due date if it is a bill or the day we eill receive if it is income
    private Date myPaymentDate; //the date of payment if it is a bill
    private String myDescription;
    private Double myValue;
    private Long myCategoryID;
    //private boolean isRecurrent;
    private int myRecPeriod;   //the peior of recurrence (daily, weekly,bi-weekly,monthly)
    private Long myID; //the ID of this cat in the database

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
        this.myRecPeriod = RECURRENT.UNKNOWN;

    }


    public BalanceData (String theDescr, Double theValue, Integer theStatus, Integer theDueDay,Integer theMonth, Integer theYear, Integer theRecurrence) {
        boolean isRecurrent;
        this.myStatus = theStatus;
        this.myRecPeriod = theRecurrence;
        if(myRecPeriod == RECURRENT.UNKNOWN || myRecPeriod == RECURRENT.ONCE) isRecurrent=false;
        else isRecurrent = true;
        if(!isRecurrent && theMonth == 0) theMonth=Integer.parseInt(DateHandler.getActualMonth());
        if(!isRecurrent && theYear == 0) theYear = Integer.parseInt(DateHandler.getActualYear());
        if(theDueDay == 0 ) theDueDay =  Integer.parseInt(DateHandler.getActualDay());
        //setDueDateWithFormat(theDueDate, theDateFormat);
        setDueDate(theDueDay, theMonth,theYear);
        myValue = theValue;
        myDescription = theDescr;
    }
    //
    // Methods
    //


    //
    // Accessor methods
    //


    public void setID(Long theID) {
        this.myID = theID;
    }

    public Long getID() {
        return myID;
    }

    /**
     * Get the period this balance data mus repeat
     * @return
     */
    public int getRecPeriod() {
        return myRecPeriod;
    }



    /**
     * Set the value of the payment date
     * @param theDate the new value of payment date in the Human readable Format
     */
    public void setPaymentDateFromHuman (String theDate) {
        if (theDate == null) return;
        if (! theDate.isEmpty()) {

            myPaymentDate = DateHandler.getDateFromHumanString(theDate);
        }

    }

    /**
     * Set the value of the payment date
     * @param theDate the new value of payment date in Database Format
     */
    public void setPaymentDateFromDatabase (String theDate) {
        if (theDate == null) return;
        if (! theDate.isEmpty()) {

            myPaymentDate = DateHandler.getDateFromDatabaseString(theDate);
        }

    }

     /**
     * Get the value of payment date in the human readable format
     * @return the value of payment date
     */
    public String getPaymentDateHuman () {
        if (this.myPaymentDate != null)
            return DateHandler.getStrDateInHumanFormat(this.myPaymentDate);
        else return "";

    }

    /**
     * Get the value of payment date in the database format
     * @return the value of payment date
     */
    public String getPaymentDateDatabase() {
        if (this.myPaymentDate != null)
            return DateHandler.getStrDateInDatabaseFormat(this.myPaymentDate);
        else return "";


    }
    /** Set the value of myDate
     * @param theDate the new value of myDate in the human readebleFormat
     */
    public void setDueDateWithFormat (String theDate, String theFormat) {
        if (theDate == null) return;
        if (! theDate.isEmpty()) {
            DateFormat format = new SimpleDateFormat(theFormat);
            Date date = null;
            try {
                date = format.parse(theDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            myDate = date;
        }
    }


    public void setDueDate(Date theDate){
        myDate = theDate;
    }

    public void setDueDate (Integer theDay, Integer theMonth, Integer theYear) {
        
        myDate  = DateHandler.getDate(theDay, theMonth, theYear);

    }

     /** Set the value of myDate
     * @param theDate the new value of myDate in the human readebleFormat
     */
    public void setDueDateFromHuman (String theDate) {

       if (! theDate.isEmpty()) {

           myDate = DateHandler.getDateFromHumanString(theDate);
       }
    }

    /** Set the value of myDate
     * @param theDate the new value of myDate in the Database Format
     */
    public void setDueDateFromDatabase (String theDate) {

        if (! theDate.isEmpty()) {
              myDate = DateHandler.getDateFromDatabaseString(theDate);
        }
    }

    public Date getDueDate(){
        if (myDate !=null)
        return myDate;
        else return null;
    }
    /**
     * Get the value of myDate in the String fin the Human readeble format
     * @return the value of myDate
     */
    public String getDueDateHuman () {
        if (this.myDate != null)
            return DateHandler.getStrDateInHumanFormat(this.myDate);
        else return "";
    }
    /**
     * Get the value of myDate in the String in the Database format
     * @return the value of myDate
     */
    public String getDueDateDatabase () {
        if (this.myDate != null)
            return DateHandler.getStrDateInDatabaseFormat(this.myDate);
        else return "";
    }

    /**
     * Get the value of Due Date in the String without the year and month
     * @return the value of day field of the Due Date
     */
    public String getDueDateDay () {
        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DateHandler.RECURRENT_DATE_FORMAT);

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
    public void setValue (Double newVar) {
        myValue = newVar;
    }

    /**
     * Get the Ammount of myValue
     * @return the the Ammount
     */
    public Double getAmount () {
        return myValue;
    }

    /**
     * Get the ammountformatted with 2 decimals
     * @return he ammountformatted with 2 decimals
     */
    public String getAmmountFormated () {

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        return df.format(getAmount().toString());

    }
    /**
     * Set the value of myCategory
     * @param newVar the new value of myCategory
     */
    public void setCategory (Long newVar) {
        myCategoryID = newVar;
    }


    /**
     * Get the value of myCategory
     * @return the value of myCategory
     */
    public Long getCategory () {
        return myCategoryID;
    }


    public boolean isRecurrent(){
        if (myRecPeriod == RECURRENT.ONCE) return false;
        else if (myRecPeriod == RECURRENT.WEEKLY) return true;
        else if (myRecPeriod == RECURRENT.BI_WEEKLI) return true;
        else if (myRecPeriod == RECURRENT.MONTHLY) return true;
        else return false;
    }

    /**
     * Set the Recurrent Period
     * @param thePeriod the Period to be setted
     */
    public void setRecurrent (int thePeriod) {
        if (thePeriod == RECURRENT.ONCE) myRecPeriod = RECURRENT.ONCE;
        else if (thePeriod == RECURRENT.WEEKLY) myRecPeriod = RECURRENT.WEEKLY;
        else if (thePeriod == RECURRENT.BI_WEEKLI) myRecPeriod = RECURRENT.BI_WEEKLI;
        else if (thePeriod == RECURRENT.MONTHLY) myRecPeriod = RECURRENT.MONTHLY;
        else myRecPeriod = RECURRENT.UNKNOWN;
    }

    /**
     * set the Recurrent Periond top Once. This indicates the this is not recurrent
     */
    public void resetRecurrent () {
       myRecPeriod = RECURRENT.ONCE;
    }

    /**
     * Get the value of isRecurrent
     * @return the value of isRecurrent
     */
    public boolean IsRecurrent () {
        if (myRecPeriod == RECURRENT.ONCE || myRecPeriod == RECURRENT.UNKNOWN) return false;
        else return true;
    }

    /**
     * return true if it is paid
     * @return true if it is paid
     */
    public boolean isPaid(){
        if (myStatus==PAY_STATUS.PAID_RECEIVED) return true;
        else return false;
    }

    /**
     * Set the status to Paid
     */
    public void setPaid(){
        myStatus=PAY_STATUS.PAID_RECEIVED;
    }

    /**
     * Set the Status to unpaid
     */
    public void resetPaid(){
        myStatus=PAY_STATUS.UNPAID_UNRECEIVED;
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
     * Set this balance data to be Weekly Recurrent
     */
    public void setWEEKLYrec(){
        this.myRecPeriod = RECURRENT.WEEKLY;
    }

    /**
     * Set this Balance Data to be Bi weekly recurrent
     */
    public void setBIWEEKLYrec(){
        this.myRecPeriod = RECURRENT.BI_WEEKLI;
    }

    /**
     * Set this Balance Data to be monthly recurrent
     */
    public void setMONTHLYrec(){
        this.myRecPeriod = RECURRENT.MONTHLY;
    }

    /*
    Set this Balance data to be not recurrent
     */
    public void setNOrec(){
        this.myRecPeriod = RECURRENT.ONCE;

    }


    public BalanceData getCopy(){
        BalanceData thecopy = new BalanceData();

        thecopy.myDate = this.myDate;  //due date if it is a bill or the day we eill receive if it is income
        thecopy.myPaymentDate = this.myPaymentDate; //the date of payment if it is a bill
        thecopy.myDescription = this.myDescription;
        thecopy.myValue = this.myValue;
        thecopy.myCategoryID = this.myCategoryID;
        //private boolean isRecurrent;
        thecopy.myRecPeriod = this.myRecPeriod;   //the peior of recurrence (daily, weekly,bi-weekly,monthly)
        thecopy.myID = this.myID; //the
        thecopy.myStatus = this.myStatus;

        return  thecopy;

    }

    /**
     * Return true if DueDate if before now
     * @return true if DueDate if before now
     */
    public boolean isPastDue(){

        return this.myDate.before(DateHandler.getNowDate());

    }

}

