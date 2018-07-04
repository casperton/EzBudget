package com.cs246.EzBudget.mRecycler;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs246.EzBudget.MainActivity;
import com.cs246.EzBudget.R;



/**
 * The RecyclerView Holder for Category
 * Defined how the line will be
 */
public class RecyclerViewHolderBalanceData extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = RecyclerViewHolderBalanceData.class.getName();

    private static final int TYPE_HEAD=0;
    private static final int TYPE_LIST=1;
    TextView myDueDate;
    TextView myValue;
    TextView myDescription;
    TextView myTitleDate, myTitleValue, myTitleDescription;

    private RecyclerClickListener myItemClickListener;
    private FragmentManager myFagmentManager;
    public int myViewType;
    //private int myLayOut;
    //public final static int LAYOUT_ONE = 1;
    //public final static int LAYOUT_TWO = 2;



    public RecyclerViewHolderBalanceData(View itemView, FragmentManager theFrag, int theViewType /*, int LayoutType*/) {
        super(itemView);
        if(MainActivity.DEBUG) Log.i(TAG, "RecyclerViewHolderBalanceData()  // Constructor");
        if (theViewType == TYPE_LIST) {
            this.myDueDate = (TextView) itemView.findViewById(R.id.textViewBDDueDate);
            this.myValue = (TextView) itemView.findViewById(R.id.textViewBDValue);
            this.myDescription = (TextView) itemView.findViewById(R.id.textViewBDDescription);
            myViewType = 1;
        }
        else if (theViewType == TYPE_HEAD) {
            //variables for header
            this.myTitleDate = (TextView) itemView.findViewById(R.id.rowBalDataHeaderDate);
            this.myTitleValue = (TextView) itemView.findViewById(R.id.rowBalDataHeaderValue);
            this.myTitleDescription = (TextView) itemView.findViewById(R.id.rowBalDataHeaderDescription);
            myViewType = 0;
        }

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        myFagmentManager = theFrag;

        //myLayOut = LayoutType;
    }

    //public int getLayOut(){
    //    return myLayOut;
    //}
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