package com.cs246.EzBudget;

import com.cs246.EzBudget.SummaryView.SummaryItem;
import com.cs246.EzBudget.mFragments.SummaryFragment;

import java.util.List;
import static java.lang.Math.abs;

/**
 * <p>
 * This class will be used to calculate expenses and allocate income
 * for given time periods.
 * A list containing the items for a given period should be passed to this class and
 * the resulting total will be calculated.
 */
public class Calculations {


    private double periodTotalNeeded = 0;
    private double periodUnPaidTotal = 0;
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
                // Total for this pay period
                periodTotalNeeded += currentItem.getAmount();
                // Track unpaid items for pay period
                if (!currentItem.isPaid()) periodUnPaidTotal += currentItem.getAmount();
            }
        }
    }

    /**
     * This method will parse the list and update the totals
     * for each period
     */
    public void updateTotals() {
        for (SummaryItem item: list) {
            if (item.getType() == OPERATION.CREDIT) {
                getPeriodTotal(list.indexOf(item));
                item.setExpenseTotal(periodTotalNeeded);
                item.setUnpaidTotal(periodUnPaidTotal);
                item.setOverageTotal(0); // Reset this each time items are changed
                periodTotalNeeded = 0;
                periodUnPaidTotal = 0;
            }
        }

        adjustPayments(); // Balance payments if there are overages
    }

    /**
     * This method finds the previous income item in the list
     * and returns the index to be used for balancing the budget
     * over multiple income periods
     * @param startPosition Starting position index
     * @return              The index of the last income item
     */
    public int getLastIncomeItem(int startPosition) {
        for (int i = startPosition - 1; i >= 0; i--)  {
            SummaryItem currentItem = list.get(i);
            if (currentItem.getType() == OPERATION.CREDIT) {
                return i;
            }
        }
        return -1;
    }

//    public int getLastIncomeItemWithBalance(int startPosition, double balanceNeeded) {
//        int index = -1;
//        Date now = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(now);
//        c.add(Calendar.MONTH, -1);
//        Date oneMonthAgo = c.getTime();
//        do {
//            index = getLastIncomeItem(startPosition);
//            if (index >= 0)
//                if ((list.get(index).getAmount() - list.get(index).getTotalCombined()) > balanceNeeded
//                        && list.get(index).getDate().after(oneMonthAgo)) {
//                    return index;
//                } else {
//                startPosition = index;
//            }
//        } while (index >=0);
//        return -1;
//    }

//    public int getMoveableItem(int startPosition, double balanceLimit) {
//        for (int x = startPosition + 1; x < list.size() && list.get(x).getType() != OPERATION.CREDIT; x++) {
//            if (list.get(x).getAmount() < balanceLimit) return x;
//        }
//        return -1;
//    }
//
//    public void swapItems(int itemIndex, int endPosition) {
//        if (itemIndex > 0 && endPosition >= 0) {
//            for (int i = itemIndex; i > endPosition; i--) {
//                Collections.swap(list, i, i-1);
//            }
//        }
//    }

    /**
     * This method will determine if each pay period has enough
     * money to cover the expenses. If it does not, the overage
     * will be carried to the previous pay period.
     */
    public void adjustPayments() {
        // Start at the end of the list
        for (int currentIncomeIndex = list.size() - 1; currentIncomeIndex >= 0; currentIncomeIndex--) {

            // Check if the item is an income item
            if (list.get(currentIncomeIndex).getType() == OPERATION.CREDIT) {
                //  Check if the total needed exceeds the amount of the income item
                double periodBalance = list.get(currentIncomeIndex).getAmount() - list.get(currentIncomeIndex).getTotalUnpaidCombined();

                int lastIncomePosition = -1;
                // If the periodBalance is < 0, there isn't enough to cover expenses
                if (periodBalance < 0) {
                    // Move the overage amount to the previous billing period to be covered
                    double overageAmount = abs(periodBalance);
                    lastIncomePosition = getLastIncomeItem(currentIncomeIndex);
                    if (lastIncomePosition >= 0) {
                        list.get(currentIncomeIndex).setUnpaidTotal(list.get(currentIncomeIndex).getUnpaidTotal() - overageAmount);
                        list.get(lastIncomePosition).setOverageTotal(overageAmount);
                    }
                }
            }
        }
    }
}