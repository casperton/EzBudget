package com.cs246.EzBudget;

import com.cs246.EzBudget.Database.DBCategory;
import com.cs246.EzBudget.Database.DBHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    public static final String DB_REC_PAYMENT_DUEDATE = DateHandler.getNowDate();
    public static final BalanceData DB_REC_PAYMENT = new BalanceData(DB_REC_PAYMENT_DESCRIPTION,
            DB_REC_PAYMENT_VALUE,
            DB_REC_PAYMENT_STATUS,
            DB_REC_PAYMENT_DUEDATE,
            DB_REC_PAYMENT_RECURRENCE);

    /**
     Payment groceries.
     bI weekly
     */
    public static final String DB_REC_GROCERY_DESCRIPTION = "Grocery bill";
    public static final Integer DB_REC_GROCERY_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_GROCERY_VALUE = 15.00;
    public static final Integer DB_REC_GROCERY_RECURRENCE = RECURRENT.WEEKLY;
    public static final String DB_REC_GROCERY_DUEDATE = DateHandler.getNowDate();
    public static final BalanceData DB_REC_GROCERY = new BalanceData(DB_REC_GROCERY_DESCRIPTION,
            DB_REC_GROCERY_VALUE,
            DB_REC_GROCERY_STATUS,
            DB_REC_GROCERY_DUEDATE,
            DB_REC_GROCERY_RECURRENCE);

    /**
     Payment electric Energy.
     MONTHLY
     */
    public static final String DB_REC_ELETRICITY_DESCRIPTION = "Power bill";
    public static final Integer DB_REC_ELETRICITY_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_ELETRICITY_VALUE = 75.00;
    public static final Integer DB_REC_ELETRICITY_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_ELETRICITY_DUEDATE = DateHandler.getNowDate();
    public static final BalanceData DB_REC_ELETRICITY = new BalanceData(DB_REC_ELETRICITY_DESCRIPTION,
            DB_REC_ELETRICITY_VALUE,
            DB_REC_ELETRICITY_STATUS,
            DB_REC_ELETRICITY_DUEDATE,
            DB_REC_ELETRICITY_RECURRENCE);

    /**
     Payment Water.
     MONTHLY
     */
    public static final String DB_REC_WATER_DESCRIPTION = "Water bill";
    public static final Integer DB_REC_WATER_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_WATER_VALUE = 105.00;
    public static final Integer DB_REC_WATER_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_WATER_DUEDATE = DateHandler.getNowDate();
    public static final BalanceData DB_REC_WATER = new BalanceData(DB_REC_WATER_DESCRIPTION,
            DB_REC_WATER_VALUE,
            DB_REC_WATER_STATUS,
            DB_REC_WATER_DUEDATE,
            DB_REC_WATER_RECURRENCE);


    /**
     Payment Phone Bill.
     monthly
     */
    public static final String DB_REC_PHONE_DESCRIPTION = "Phone bill";
    public static final Integer DB_REC_PHONE_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_PHONE_VALUE = 65.00;
    public static final Integer DB_REC_PHONE_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_PHONE_DUEDATE = DateHandler.getNowDate();
    public static final BalanceData DB_REC_PHONE = new BalanceData(DB_REC_PHONE_DESCRIPTION,
            DB_REC_PHONE_VALUE,
            DB_REC_PHONE_STATUS,
            DB_REC_PHONE_DUEDATE,
            DB_REC_PHONE_RECURRENCE);

    /**
     Payment Car Bill.
     monthly
     */
    public static final String DB_REC_CAR_DESCRIPTION = "Car payment";
    public static final Integer DB_REC_CAR_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_CAR_VALUE = 330.00;
    public static final Integer DB_REC_CAR_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_CAR_DUEDATE = DateHandler.getNowDate();
    public static final BalanceData DB_REC_CAR = new BalanceData(DB_REC_CAR_DESCRIPTION,
            DB_REC_CAR_VALUE,
            DB_REC_CAR_STATUS,
            DB_REC_CAR_DUEDATE,
            DB_REC_CAR_RECURRENCE);

    /**
     Payment Rent.
     monthly
     */
    public static final String DB_REC_RENT_DESCRIPTION = "Rent";
    public static final Integer DB_REC_RENT_STATUS = PAY_STATUS.UNPAID_UNRECEIVED;
    public static final Double DB_REC_RENT_VALUE = 850.00;
    public static final Integer DB_REC_RENT_RECURRENCE = RECURRENT.MONTHLY;
    public static final String DB_REC_RENT_DUEDATE = DateHandler.getNowDate();
    public static final BalanceData DB_REC_RENT = new BalanceData(DB_REC_RENT_DESCRIPTION,
            DB_REC_RENT_VALUE,
            DB_REC_RENT_STATUS,
            DB_REC_RENT_DUEDATE,
            DB_REC_RENT_RECURRENCE);

    //
    // Fields
    //

    private Date myDate;  //due date if it is a bill or the day we eill receive if it is income
    private Date myPaymentDate; //the date of payment if it is a bill
    private String myDescription;
    private Double myValue;
    private Long myCategoryID;
    private boolean isRecurrent;
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
        this.myRecPeriod = RECURRENT.NO_PERIODIC;
    }
    public BalanceData (String theDescr, Double theValue, Integer theStatus, String theDueDate, Integer theRecurrence) {

        this.myStatus = theStatus;
        this.myRecPeriod = theRecurrence;
        setDate(theDueDate);
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
     * Set the period this balance data must repeat
     * @param theRecPeriod
     */
    public void setRecPeriod(int theRecPeriod) {
        this.myRecPeriod = theRecPeriod;
    }

    /**
     * Set the value of the payment date
     * @param newVar the new value of payment date
     */
    public void setPaymentDate (String newVar) {

        if (! newVar.isEmpty()) {
            DateFormat format = new SimpleDateFormat(DateHandler.DATE_FORMAT);
            Date date = null;
            try {
                date = format.parse(newVar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            myPaymentDate = date;
        }

    }

     /**
     * Get the value of payment date in the format "dd/MM/yyyy
     * @return the value of payment date
     */
    public String getPaymentDate () {
        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT);

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
    public void setDate (String newVar) {

       if (! newVar.isEmpty()) {
           DateFormat format = new SimpleDateFormat(DateHandler.DATE_FORMAT);
           Date date = null;
           try {
               date = format.parse(newVar);
           } catch (ParseException e) {
               e.printStackTrace();
           }

           myDate = date;
       }
    }

    /**
     * Get the value of myDate in the String format dd/MM/yyyy
     * @return the value of myDate
     */
    public String getDate () {
        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT);

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
     * Get the value of myValue
     * @return the value of myValue
     */
    public Double getValue () {
        return myValue;
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
     * Set this balance data to be Daily recurrent
     */
    public void setDAILYrec(){
        this.myRecPeriod = RECURRENT.DAILY;
    }

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
        this.myRecPeriod = RECURRENT.NO_PERIODIC;
    }


}

