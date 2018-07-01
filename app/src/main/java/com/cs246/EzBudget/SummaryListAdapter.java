package com.cs246.EzBudget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class SummaryListAdapter extends RecyclerView.Adapter<SummaryListAdapter.MyViewHolder> {

    private Context context;
    private List<SummaryItem> list;

    public SummaryListAdapter(Context context, List<SummaryItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SummaryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == 1) {
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
        df.setMinimumFractionDigits(2);
        holder.amount.setText("$" + df.format(bill.getAmount()).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (list.get(position).getType() == 1) viewType = 1;
        return viewType;
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(SummaryItem item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, amount;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            if (viewType != 1) viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

}

