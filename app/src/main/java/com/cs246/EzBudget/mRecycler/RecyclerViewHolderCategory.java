package com.cs246.EzBudget.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mRecycler.RecyclerClickListener;


/**
 * The RecyclerView Holder for Category
 * Defined how the line will be
 */
public class RecyclerViewHolderCategory extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    TextView name;
    //TextView id;
    ImageView icon;
    private RecyclerClickListener myItemClickListener;
    private int myAction;
    // used when the user will choose the selected view for something
    public static final int ACTION_CHOOSE=0;
    //used when the user will choose the selected view for edit or add
    public static final int ACTION_ADD=1;

    /**
     * Constructor
     * @param itemView  The View
     * @param ActionType  ACTION_CHOOSE to choosea item only / ACTION_ADD to be able to add/delete/update
     */
    public RecyclerViewHolderCategory(View itemView, int ActionType) {
        super(itemView);
        //id = (TextView)itemView.findViewById(R.id.textViewCatID);
        name = (TextView) itemView.findViewById(R.id.textViewCatName);
        icon = (ImageView) itemView.findViewById(R.id.imgOper);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        myAction = ActionType;
    }

    public int getAction(){
        return myAction;
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