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
import com.cs246.EzBudget.R;

import java.text.DecimalFormat;
import java.util.List;

public class SummaryListAdapter extends RecyclerView.Adapter<SummaryListAdapter.MyViewHolder> {

    private Context context;
    private List<SummaryItem> list;
    private double total = 0;

    public SummaryListAdapter(Context context, List<SummaryItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SummaryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == BALANCE_ITEM.INCOME) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_list_item, parent, false);
            holder = new MyViewHolder(itemView, viewType);
            holder.itemView.setTag("income");
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);
            holder = new MyViewHolder(itemView, viewType);
            holder.itemView.setTag("expense");
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(SummaryListAdapter.MyViewHolder holder, int position) {
        SummaryItem bill = list.get(position);
        holder.name.setText(bill.getName());
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        holder.amount.setText("$" + df.format(bill.getAmount()).toString());
        // TODO : Format date for locale
        String date = bill.getDate();
        if (bill.getType() == BALANCE_ITEM.INCOME) {
            holder.date.setText("Date: " + date);
            // TODO : Replace with calculated total for only this period
            total = new Calculations(list).getPeriodTotal();
            holder.total_needed.setText("Needed: $" + String.valueOf(df.format(total)));
        } else {
            holder.date.setText("Due: " + date);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    public void removeItem(int position) {
        list.remove(position);
        // TODO : Recalculate total needed for this period once item is paid
        total = new Calculations(list).getPeriodTotal();
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void restoreItem(SummaryItem item, int position) {
        list.add(position, item);
        total = new Calculations(list).getPeriodTotal();
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, amount, date, total_needed;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            if (viewType != BALANCE_ITEM.INCOME) {
                viewBackground = itemView.findViewById(R.id.view_background);
            } else {
                total_needed = itemView.findViewById(R.id.total_needed);
            }
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

}

