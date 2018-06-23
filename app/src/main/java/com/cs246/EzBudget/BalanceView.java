package com.cs246.EzBudget;

import com.cs246.EzBudget.Database.DBHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BalanceView{

    /**
     * FIELDS FOR DATABASE DESCRIPTION
     */
    public static final String BALANCEVIEW_TABLE_NAME = "balanceView";
    public static final String BALANCEVIEW_COLUMN_ID = "id";
    public static final String BALANCEVIEW_COLUMN_INI_DATE = "initialDate";
    public static final String BALANCEVIEW_COLUMN_FINAL_DATE = "finalDate";
    public static final String BALANCEVIEW_COLUMN_KEY_DATE = "finalDate";
    public static final String BALANCEVIEW_COLUMN_DESCRIPTION = "description";
    public static final String BALANCEVIEW_COLUMN_END_BALANCE = "finalBalance";

    private Date myInitialDate;
    private Date myFinalDate;
    private Date myKeyDate;
    private String myDescription;
    private double myEndBalance;
    private Balance myBalance;

    public BalanceView() {

    }

    public void setInitialDate(String newVar) {
        if (! newVar.isEmpty()) {
            DateFormat format = new SimpleDateFormat(DBHelper.DATE_FORMAT);
            Date date = null;
            try {
                date = format.parse(newVar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            this.myInitialDate = date;
        }

    }

    public String getInitialDate() {

        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);

        if(this.myInitialDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(this.myInitialDate);
        }
        return reportDate;
    }





    public void setKeyDate(String newVar) {
        if (! newVar.isEmpty()) {
            DateFormat format = new SimpleDateFormat(DBHelper.DATE_FORMAT);
            Date date = null;
            try {
                date = format.parse(newVar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            this.myKeyDate = date;
        }

    }

    public String getKeyDate() {

        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);

        if(this.myKeyDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(this.myKeyDate);
        }
        return reportDate;
    }

    public void setDescription(String _description){

    }

    public String getDescription() {
        return null;
    }

    public void setEndBalance(double _balance) {

    }

    public double getEndBalance() {
        return 0;
    }

    private void Balance(Balance _balance) {

    }

    public void setFinalDate(String newVar) {
        if (! newVar.isEmpty()) {
            DateFormat format = new SimpleDateFormat(DBHelper.DATE_FORMAT);
            Date date = null;
            try {
                date = format.parse(newVar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            this.myFinalDate = date;
        }

    }
    public String getFinalDate() {

        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);

        if(this.myFinalDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(this.myFinalDate);
        }
        return reportDate;
    }

}
