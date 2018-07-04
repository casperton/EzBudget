package com.cs246.EzBudget;

import android.util.Log;

import com.cs246.EzBudget.SummaryView.SummaryItem;

import java.util.List;

public class Calculations {
    // This class will be used to calculate expenses and allocate income
    // for given time periods.
    // A list containing the items for a given period should be passed to this class and
    // the resulting total will be calculated.

    private static final String TAG = Calculations.class.getName();

    private double periodTotal = 0;
    private List<SummaryItem> list;

    public Calculations(List<SummaryItem> list) {
        this.list = list;
    }

    public double getPeriodTotal() {
        if(MainActivity.DEBUG) Log.i(TAG, "getPeriodTotal()");

        periodTotal = 0;
        for (SummaryItem listItem : list) {
            ////////////////////////////////////////////////////////////////////////
            // MEssage from Salvatore. You should use the class OPERATION for that
            // OPERATION.CREDIT = BALANCE_ITEM.INCOME
            // OPERATION.DEBIT = BALANCE_ITEM.EXPENSE
            // OPERATION.INFORMATIVE =>  used to store the cash flow.which is only informative
            /////////////////////////////////////////////////////////////////////
            if (listItem.getType() == BALANCE_ITEM.EXPENSE) {
                periodTotal += listItem.getAmount();
            }
        }
        return periodTotal;
    }

}
