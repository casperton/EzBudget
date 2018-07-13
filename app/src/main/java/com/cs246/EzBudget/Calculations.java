package com.cs246.EzBudget;

import com.cs246.EzBudget.SummaryView.SummaryItem;
import com.cs246.EzBudget.mFragments.SummaryFragment;

import java.util.List;

/**
 * <p>
 * This class will be used to calculate expenses and allocate income
 * for given time periods.
 * A list containing the items for a given period should be passed to this class and
 * the resulting total will be calculated.
 */
public class Calculations {


    private double periodTotalNeeded = 0;
    private double periodPaidTotal = 0;
    private List<SummaryItem> list;

    /**
     * Default constructor for the calculations.
     */
    public Calculations() {
        list = SummaryFragment.bills;
    }

    /**
     * This class will calculate the total balance needed to pay bills during
     * this period.
     * @return  Returns the total for the period as a double value
     */
    public void getPeriodTotal(int start_position) {
        periodTotalNeeded = 0;
        for (int i = start_position + 1; i < list.size() && list.get(i).getType() != OPERATION.CREDIT; i++)  {
            SummaryItem currentItem = list.get(i);
            if (currentItem.getType() == OPERATION.DEBIT) {
                periodTotalNeeded += currentItem.getAmount();
                if (currentItem.isPaid()) periodPaidTotal += currentItem.getAmount();
            }
        }
    }

    public void updateTotals() {
        for (SummaryItem item: list) {
            if (item.getType() == OPERATION.CREDIT) {
                getPeriodTotal(list.indexOf(item));
                item.setTotal_needed(periodTotalNeeded);
                item.setTotal_paid(periodPaidTotal);
                periodPaidTotal = 0;
                periodPaidTotal = 0;
            }
        }
    }
}
