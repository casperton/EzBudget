package com.cs246.EzBudget.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs246.EzBudget.R;



/**
 * The RecyclerView Holder for Category
 * Defined how the line will be
 */
public class RecyclerViewHolderBalanceData extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    TextView myDueDate;
    TextView myValue;
    TextView myDescription;

    private RecyclerClickListener myItemClickListener;
    private int myLayOut;
    public final static int LAYOUT_ONE = 1;
    public final static int LAYOUT_TWO = 2;



    public RecyclerViewHolderBalanceData(View itemView, int LayoutType) {
        super(itemView);

        this.myDueDate = (TextView) itemView.findViewById(R.id.textViewBDDueDate);
        this.myValue = (TextView) itemView.findViewById(R.id.textViewBDValue);
        this.myDescription = (TextView) itemView.findViewById(R.id.textViewBDDescription);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        myLayOut = LayoutType;
    }

    public int getLayOut(){
        return myLayOut;
    }
    public void setItemClickListener(RecyclerClickListener itemClickListener){

        this.myItemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View view) {

        myItemClickListener.OnClick(view,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View view) {
        myItemClickListener.OnClick(view,getAdapterPosition(),true);
        return true;
    }
}