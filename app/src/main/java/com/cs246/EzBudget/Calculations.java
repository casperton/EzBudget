package com.cs246.EzBudget;

import com.cs246.EzBudget.SummaryView.SummaryItem;

import java.util.List;

/**
 * <p>
 * This class will be used to calculate expenses and allocate income
 * for given time periods.
 * A list containing the items for a given period should be passed to this class and
 * the resulting total will be calculated.
 */
public class Calculations {


    private double periodTotal = 0;
    private List<SummaryItem> list;

    /**
     * Default constructor for the calculations.
     * @param list  Requires the list of items for the summary view to be calculated
     */
    public Calculations(List<SummaryItem> list) {
        this.list = list;
    }

    /**
     * This class will calculate the total balance needed to pay bills during
     * this period.
     * @return  Returns the total for the period as a double value
     */
    public double getPeriodTotal() {
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
