package com.cs246.EzBudget;

import com.cs246.EzBudget.SummaryView.SummaryItem;
import com.cs246.EzBudget.mFragments.SummaryFragment;

import java.util.Collections;
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
        int incomeItemIndex = -1;
        boolean adjustExpenses = false;
        String name;
        for (SummaryItem item: list) {
            if (item.getType() == OPERATION.CREDIT) {
                getPeriodTotal(list.indexOf(item));
                item.setTotal_needed(periodTotalNeeded);
                item.setTotal_paid(periodPaidTotal);
                // Check to see if the total needed exceeds the amount available
                if (periodTotalNeeded > item.getAmount()) {
                    adjustExpenses = true;
                }
                periodTotalNeeded = 0;
                periodPaidTotal = 0;
            }
        }

        if (adjustExpenses) {
//            adjustPayments();
        }
    }

    public int getLastIncomeItem(int startPosition) {
        for (int i = startPosition - 1; i >= 0; i--)  {
            SummaryItem currentItem = list.get(i);
            if (currentItem.getType() == OPERATION.CREDIT) {
                return i;
            }
        }
        return -1;
    }

    public void adjustPayments() {
        boolean swapChange = false;
        // Start at the end of the list
        for (int i = list.size() - 1; i >= 0; i--) {
            int lastIncomePosition = -1;
            // Check if the item is an income item and if the total needed exceeds the amount
            if (list.get(i).getType() == OPERATION.CREDIT && list.get(i).getTotal_needed() > list.get(i).getAmount()) {
                // Find the last income item
                if (i > 0) lastIncomePosition = getLastIncomeItem(i);
                if (lastIncomePosition > 0) {
                    // Check to see if any items can be moved to this income item
                    for(int x = lastIncomePosition + 1; x < list.size() && list.get(x).getType() != OPERATION.CREDIT; i++) {
                        // Check if this item is less than the balance of the previous item
                        if (list.get(x).getAmount() < (list.get(lastIncomePosition).getAmount() - list.get(lastIncomePosition).getTotal_needed())) {
                            // Swap the item until it's in the position behind the last income item
                            for (int y = x; y >= lastIncomePosition - 1; i--) {
                                Collections.swap(list, y, x);
                            }
                        }
                        swapChange = true;
                    }
                }
            }
        }

        if (swapChange) {
            updateTotals();
            adjustPayments();
        }
    }
}
