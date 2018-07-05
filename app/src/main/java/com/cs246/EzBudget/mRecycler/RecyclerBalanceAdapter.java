package com.cs246.EzBudget.mRecycler;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.RECURRENT;
import com.cs246.EzBudget.mFragments.DispBalDataFragment;
import com.cs246.EzBudget.mFragments.DispBalViewFragment;
import com.cs246.EzBudget.mFragments.DispCategoryFragment;


import java.util.ArrayList;

/**
 * This class is the list View to show the categories in the screen
 */
public class RecyclerBalanceAdapter extends RecyclerView.Adapter<RecyclerViewHolderBalanceData> {

    private static final int TYPE_HEAD=0;
    private static final int TYPE_LIST=1;


    private ArrayList<BalanceData> myBalanceDataList = new ArrayList<>();
    private Context myContext;

    private int myAction;
    private FragmentManager myFagmentManager;
    private boolean isRecurrent;
    //indicate the selected row
    private int myRowIndex = -1;
    private Button myUpdateButton;

    public RecyclerBalanceAdapter(ArrayList<BalanceData> BalanceDataList, Context theContext, FragmentManager theFrag, boolean theRec , int theAction,Button theUpdateButton) {
        this.myBalanceDataList = BalanceDataList;
        this.myContext = theContext;
        myFagmentManager = theFrag;
        isRecurrent = theRec;
        myAction = theAction;
        myUpdateButton = theUpdateButton;

    }

    // Initialize View
    @NonNull
    @Override
    public RecyclerViewHolderBalanceData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewHolderBalanceData theViewHolder;
        View view = null;

        if (viewType == TYPE_HEAD){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_balance_data_header, parent, false);

        }
        else if (viewType == TYPE_LIST){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_balance_data_item, parent, false);

        }
        theViewHolder = new RecyclerViewHolderBalanceData(view,myFagmentManager, viewType);

        return theViewHolder;

    }

    //Bind Data
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderBalanceData holder, int position) {

        if(holder.myViewType==TYPE_LIST) {
                int theNewPosition = position-1;
            //set background color
            //set highlight color
            if((myRowIndex==position)){
                holder.itemView.setBackgroundColor(Color.parseColor("#CACACA"));
            }else{
                if (theNewPosition % 2 == 0)
                    holder.itemView.setBackgroundColor(Color.parseColor("#F2F2F2"));
                else
                    holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }

            if (isRecurrent)
                holder.myDueDate.setText(myBalanceDataList.get(theNewPosition).getDueDateRecurrentHuman());
            else
                holder.myDueDate.setText(myBalanceDataList.get(theNewPosition).getDueDateHuman());

            holder.myValue.setText(Double.toString(myBalanceDataList.get(theNewPosition).getValue()));
            holder.myDescription.setText(myBalanceDataList.get(theNewPosition).getDescription());
            int oper = myBalanceDataList.get(theNewPosition).getStatus();

            if (oper == PAY_STATUS.PAID_RECEIVED) {
                //todo: change color depending of the status payd or not
                //holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_add_circle_green_24dp,myContext.getTheme()));
            } else if (oper == PAY_STATUS.UNPAID_UNRECEIVED) {
                //holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_remove_circle_red_24dp, myContext.getTheme()));
            } else {
                //holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_warning_black_24dp, myContext.getTheme()));
            }

            if (myAction == LIST_ACTION.ACT_LIST_ADD) {
                holder.setItemClickListener(new RecyclerClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        int theNewPosition = position-1;
                        myRowIndex = position;
                        CommonBalData.currentItem = myBalanceDataList.get(theNewPosition);
                        notifyDataSetChanged();
                        if(myUpdateButton !=null) {
                            myUpdateButton.setVisibility(View.VISIBLE);

                        }
                        if(!isRecurrent) CommonBalData.currentItem.setRecPeriod(RECURRENT.NO_PERIODIC);

                    }

                });
            } else if (myAction == LIST_ACTION.ACT_LIST_CHOOSE) {
                //Todo:  add click listener to add the chosen Recurrent data into de DalanceData
                holder.setItemClickListener(new RecyclerClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        int theNewPosition = position-1;
                        myRowIndex = position;
                        CommonBalData.currentItem = myBalanceDataList.get(theNewPosition);
                        BalanceData theRecord = myBalanceDataList.get(theNewPosition);
                        DBBalanceData theBalDataDatabase = new DBBalanceData(myContext);
                        if (theBalDataDatabase.insert(theRecord, true) > 0) {
                            Toast.makeText(myContext.getApplicationContext(), "Added Successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(myContext.getApplicationContext(), "Not Added",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });
            }
        }else if (holder.myViewType==TYPE_HEAD){
        //holder.myTitleDate.setText("@string/");
        }
    }

    @Override
    public int getItemCount() {
        return myBalanceDataList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_HEAD;

        return TYPE_LIST;
    }
}