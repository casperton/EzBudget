package com.cs246.EzBudget.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs246.EzBudget.MainActivity;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mRecycler.RecyclerClickListener;


/**
 * The RecyclerView Holder for Category
 * Defined how the line will be
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = RecyclerViewHolder.class.getName();

    TextView name;
    //TextView id;
    ImageView icon;
    private RecyclerClickListener myItemClickListener;
    private int myLayOut;
    public final static int LAYOUT_ONE = 1;
    public final static int LAYOUT_TWO = 2;



    public RecyclerViewHolder(View itemView, int LayoutType) {
        super(itemView);
        if(MainActivity.DEBUG) Log.i(TAG, "RecyclerViewHolder()  // Constructor");

        //id = (TextView)itemView.findViewById(R.id.textViewCatID);
        name = (TextView) itemView.findViewById(R.id.textViewCatName);
        icon = (ImageView) itemView.findViewById(R.id.imgOper);
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