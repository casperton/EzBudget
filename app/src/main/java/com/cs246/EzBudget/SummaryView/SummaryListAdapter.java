package com.cs246.EzBudget.SummaryView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs246.EzBudget.BALANCE_ITEM;
import com.cs246.EzBudget.Calculations;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This is the adapter class for the summary list
 * that is displayed on the main activity view.
 * It contains all the expense and income items
 * to be shown for the RecyclerView with the
 * swipeable items.
 *
 * <p>
 * This class was largely based off the demo from:
 * https://www.androidhive.info/2017/09/android-recyclerview-swipe-delete-undo-using-itemtouchhelper/
 * https://www.youtube.com/watch?v=bWyQlZGMrXM
 */
public class SummaryListAdapter extends RecyclerView.Adapter<SummaryListAdapter.MyViewHolder> {

    private Context context;
    private List<SummaryItem> list;

    /**
     * Default constructor for the class
     *
     * @param context   Context
     * @param list      List containing the summary items
     */
    public SummaryListAdapter(Context context, List<SummaryItem> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * This method creates the viewholder for the summary item
     * It will create the viewholder based on the item type
     * (expense or income) and create the viewholder for it
     *
     * @param parent    The parent viewgroup for the items
     * @param viewType  The item type (expense or income)
     * @return          Returns the viewHolder for the item
     */
    @Override
    public SummaryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == OPERATION.CREDIT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_list_item, parent, false);
            holder = new IncomeViewHolder(itemView, viewType);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);
            holder = new ExpenseViewHolder(itemView, viewType);
        }
        return holder;
    }

    /**
     * This method sets the data for the item in the view
     * based on its item type (expense or income).
     *
     * @param holder    The viewHolder for the summary item
     * @param position  The position index of the item in the list
     */
    @Override
    public void onBindViewHolder(SummaryListAdapter.MyViewHolder holder, int position) {
        SummaryItem bill = list.get(position);
        holder.name.setText(bill.getName());
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        holder.amount.setText("$" + df.format(bill.getAmount()).toString());
        // TODO : Format date for locale
        String date = bill.getUsDate();
        if (bill.getType() == OPERATION.CREDIT) {
            // Set values for expense items
            IncomeViewHolder incomeHolder = (IncomeViewHolder) holder;
            incomeHolder.date.setText("Date: " + date);
            // TODO : Replace with calculated total for only this period
            Double total_needed = bill.getTotal_needed();
            incomeHolder.total_needed.setText("Needed: $" + String.valueOf(df.format(total_needed)));
        } else {
            // Expense items have a due date only
            holder.date.setText("Due: " + date);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date now = new Date();
            if (bill.getDate().before(now)) {
                ExpenseViewHolder expenseHolder = (ExpenseViewHolder) holder;
                expenseHolder.past_due.setText("(Past Due)");
            }
        }

    }

    /**
     * This method returns the number of items in the list
     * @return  The size (number of items) of the list
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * This method determins if the item is an expense
     * or income item for the item view
     *
     * @param position  The position index of the item in the list
     * @return          Returns the type of the item (expense or income)
     */
    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    /**
     * This method is called when an expense item is swiped
     * and marked as paid. The item will be removed from
     * the list and marked as paid in the database. The view
     * will then be updated to reflect the item as complete.
     *
     * @param position  The position index of the item in the list
     */
    public void removeItem(int position) {
        list.remove(position);
        // TODO : Recalculate total needed for this period once item is paid
        Calculations calc  = new Calculations(list);
        calc.updateTotals();
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    /**
     * This method is called when "Undo" is clicked after
     * marking an item as paid. It will add the object
     * back into the list into the previous position
     * and then update the view to show it again.
     *
     * @param item      The expense item from the list to be restored
     * @param position  The position index of the item in the summary view
     */
    public void restoreItem(SummaryItem item, int position) {
        list.add(position, item);
        Calculations calc  = new Calculations(list);
        calc.updateTotals();
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    /**
     *  This is the superclass for the viewholder. It must contain
     *  a viewForeground Relativelayout for the RecylclerTouchHelper
     *  class. This base class must have a name, amount, and date
     *  since that will be displayed for each item in the summary view.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, amount, date;
        public RelativeLayout viewForeground;

        /**
         *
         * @param itemView The summar item for the view
         * @param viewType The viewtype (expense or income)
         */
        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
        }
    }

    /**
     * This class creates the viewholder for the expense items
     * on the summary screen. This viewholder is created separately
     * from the income items to format them differently with the
     * ability to swipe them as paid.
     */

    public class ExpenseViewHolder extends MyViewHolder {
        public RelativeLayout viewBackground;
        public TextView past_due;

        /**
         * The constructor method sets up the items in the
         * TextView needed to display the data for the
         * expense item
         *
         * @param itemView The summary item in the view
         * @param viewType The viewtype (expense or income)
         */
        public ExpenseViewHolder(View itemView, int viewType) {
            super(itemView, viewType);

            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            past_due = itemView.findViewById(R.id.past_due);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

    /**
     * This class creates the viewholder for the income items
     * on the summary screen. This viewholder is created separately
     * from the expense items to format them differently without
     * the ability to swipe them away as paid.
     */
    public class IncomeViewHolder extends MyViewHolder {
        public TextView total_needed;

        /**
         * The constructor method sets up the items in the
         * TextView needed to display the data for the
         * income item.
         *
         * @param itemView The summary item in the view
         * @param viewType The viewtype (expense or income)
         */
        public IncomeViewHolder(View itemView, int viewType) {
            super(itemView, viewType);

            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            total_needed = itemView.findViewById(R.id.total_needed);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

}

