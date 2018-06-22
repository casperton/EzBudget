package com.cs246.EzBudget;

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

    public void setInitialDate(Date _date) {

    }

    public String getInitialDate() {
        String date = "";
        return date;
    }

    public void setFinalDate(Date _date) {

    }

    public Date getFinalDate() {
        return null;
    }

    public void setKeyDate(Date _date) {

    }

    public String getKeyDate() {

        String date = "";
        return date;
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

    public String getEndDate() {
        return null;
    }

}
