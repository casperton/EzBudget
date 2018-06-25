package com.cs246.EzBudget;

import java.util.ArrayList;
import java.util.List;

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

    public Balance() {

    }

    private void setBalanceView(BalanceView _balanceView) {

    }

    private BalanceView getBalanceView() {
       return null;
    }

    private void addFinancialData(List<BalanceData> _balanceData) {

    }

    private List<BalanceData> getFinancialData() {
        return null;
    }

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
