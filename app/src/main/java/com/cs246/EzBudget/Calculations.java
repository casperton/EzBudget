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
    public double getPeriodTotal(int start_position) {
        periodTotal = 0;
        for (int i = start_position + 1; i < list.size() && list.get(i).getType() != OPERATION.CREDIT; i++)  {
            if (list.get(i).getType() == OPERATION.DEBIT) {
                periodTotal += list.get(i).getAmount();
            }
        }
        return periodTotal;
    }

    public void updateTotals() {
        for (SummaryItem item: list) {
            if (item.getType() == OPERATION.CREDIT) {
                item.setTotal_needed(getPeriodTotal(list.indexOf(item)));
            }
        }
    }
}
