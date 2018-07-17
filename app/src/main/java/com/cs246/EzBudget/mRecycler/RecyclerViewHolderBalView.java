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
public class RecyclerViewHolderBalView extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private static final int TYPE_HEAD=0;
    private static final int TYPE_LIST=1;

    int myViewType;

    TextView myTitle;
    TextView myIniDate;
    TextView myEndDate;
    TextView myTitleIniDate, myTitleEndDate,myTitleTitle;

    private RecyclerClickListener myItemClickListener;
    //private int myLayOut;
    //public final static int LAYOUT_ONE = 1;
    //public final static int LAYOUT_TWO = 2;


    /**
     * Constructor
     * @param itemView  The View
     * @param theViewType  the View Type
     */
    public RecyclerViewHolderBalView(View itemView, int theViewType/*, int LayoutType*/) {
        super(itemView);
        //id = (TextView)itemView.findViewById(R.id.textViewCatID);

        if (theViewType == TYPE_LIST) {
            myTitle = (TextView) itemView.findViewById(R.id.balViewTitle);
            myIniDate = (TextView) itemView.findViewById(R.id.balViewIniDate);
            myEndDate = (TextView) itemView.findViewById(R.id.balViewEndDate);
            myViewType = 1;
        }
        else if (theViewType == TYPE_HEAD) {
            //variables for header
            this.myTitleIniDate = (TextView) itemView.findViewById(R.id.rowBalViewHeaderIniDate);
            this.myTitleEndDate = (TextView) itemView.findViewById(R.id.rowBalViewHeaderEndDate);
            this.myTitleTitle = (TextView) itemView.findViewById(R.id.rowBalViewHeaderTitle);
            myViewType = 0;
        }
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        //myLayOut = LayoutType;
    }

    //public int getLayOut(){
        //return myLayOut;
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
