package com.cs246.EzBudget;

import java.util.ArrayList;
import java.util.List;

/**
 * This class should be used to hold calculations in the database.
 * It is now for future use
 */
public class Balance {

    /**
     * FIELDS FOR DATABASE DESCRIPTION
     */
    public static final String BALANCE_TABLE_NAME = "balance";
    public static final String BALANCE_COLUMN_ID = "idBal";
    public static final String BALANCE_COLUMN_NAME = "name";
    public static final String BALANCE_COLUMN_VIEW = "view_id";



    private BalanceView myBalanceView;
    private List<BalanceData> myFinancialData;

    /**
     * Constructor
     */
    public Balance() {

    }

    /**
     * Setter
     * @param _balanceView
     */
    private void setBalanceView(BalanceView _balanceView) {

    }

    /**
     * getter
     * @return
     */
    private BalanceView getBalanceView() {
       return null;
    }

    private List<BalanceData> getFinancialData() {
        return null;
    }

    /**
     * Get the End Balance
     * @return the end balance
     */
    public double getEndBalance() {
        return 0;
    }

    /**
     * Return the Bills
     * @return
     */
    public ArrayList<BalanceData> getBills(){
        ArrayList<BalanceData> theList= new ArrayList<>();

        return theList;
    }

    /**
     * Return the Incomes
     * @return
     */
    public ArrayList<BalanceData> getIncomes(){
        ArrayList<BalanceData> theList= new ArrayList<>();

        return theList;
    }


}
