package com.cs246.EzBudget;

import java.util.List;

public class Balance {

    /**
     * FIELDS FOR DATABASE DESCRIPTION
     */
    public static final String BALANCE_TABLE_NAME = "balance";
    public static final String BALANCE_COLUMN_ID = "id";
    public static final String BALANCE_COLUMN_NAME = "name";
    public static final String BALANCE_COLUMN_VIEW = "view_id";



    private BalanceView new_attribute;
    private List<BalanceData> myFinancialData;

    public Balance() {

    }

    private void setNew_attribute(BalanceView _balanceView) {

    }

    private BalanceView getNew_attribute() {
       return null;
    }

    private void addmyFinancialData(List<BalanceData> _balanceData) {

    }

    private List<BalanceData> getMyFinancialData() {
        return null;
    }

    public double getEndBalance() {
        return 0;
    }
}
