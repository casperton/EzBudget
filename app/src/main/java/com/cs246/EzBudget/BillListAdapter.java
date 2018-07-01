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

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.MyViewHolder> {

    private Context context;
    private List<BillItem> list;

    public BillListAdapter(Context context, List<BillItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BillListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BillListAdapter.MyViewHolder holder, int position) {
        BillItem bill = list.get(position);
        holder.name.setText(bill.getName());
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        holder.amount.setText("$" + df.format(bill.getAmount()).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(BillItem item, int position) {
        list.add(position, item);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, amount;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

