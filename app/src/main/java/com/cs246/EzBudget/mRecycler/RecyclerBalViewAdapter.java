package com.cs246.EzBudget.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.MainActivity;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mFragments.DispBalDataFragment;
import com.cs246.EzBudget.mFragments.DispBalViewFragment;
import com.cs246.EzBudget.mFragments.ListBalViewFragment;


import java.util.ArrayList;


/**
 * This class is the list View to show the categories in the screen
 */
public class RecyclerBalViewAdapter extends RecyclerView.Adapter<RecyclerViewHolderBalView> {

    //used to indicate it is the header of the list
    private static final int TYPE_HEAD=0;
    //used to indicate it is not the header of the list
    private static final int TYPE_LIST=1;

    // used when the user will choose the selected view for something
    public static final int ACTION_CHOOSE=0;
    //used when the user will choose the selected view for edit or add
    public static final int ACTION_ADD=1;

    private int myActionType;
    private ArrayList<BalanceView> myBalanceViewList = new ArrayList<>();
    private Context myContext;
    private int myLayout;
    //indicate the selected row
    private int myRowIndex = -1;
    private FragmentManager myFagmentManager;
    private Button myUpdateButton;

    public RecyclerBalViewAdapter(ArrayList<BalanceView> categoryList, Context theContext, FragmentManager theFrag, int theActionType, Button theUpdateBtn) {
        this.myBalanceViewList = categoryList;
        this.myContext = theContext;
        myActionType = theActionType;
        myFagmentManager = theFrag;
        myUpdateButton = theUpdateBtn;
    }

    // Initialize View
    @NonNull
    @Override
    public RecyclerViewHolderBalView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerViewHolderBalView theViewHolder;



        if (viewType == TYPE_HEAD){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bal_view_header, parent, false);

        }
        else if (viewType == TYPE_LIST){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bal_view_item, parent, false);

        }
        theViewHolder = new RecyclerViewHolderBalView(view, viewType/*,myLayout*/);


        return theViewHolder;
    }

    //Bind Data
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderBalView holder, int position) {




        if (holder.myViewType == TYPE_LIST) {
            int theNewPosition = position - 1;
            holder.myTitle.setText(myBalanceViewList.get(theNewPosition).getTitle());
            holder.myIniDate.setText(myBalanceViewList.get(theNewPosition).getInitialDateToHuman());
            holder.myEndDate.setText(myBalanceViewList.get(theNewPosition).getFinalDateToHuman());

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

            if (myActionType == ACTION_ADD) {
                holder.setItemClickListener(new RecyclerClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        int theNewPosition = position - 1;

                        myRowIndex = position;
                        CommonBalView.currentItem = myBalanceViewList.get(theNewPosition);
                        notifyDataSetChanged();
                        if(myUpdateButton !=null) {
                            myUpdateButton.setVisibility(View.VISIBLE);

                        }

                    }

                });
            }
            if (myActionType == ACTION_CHOOSE) {
                holder.setItemClickListener(new RecyclerClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        int theNewPosition = position - 1;
                        myRowIndex = position;
                        CommonBalView.currentItem = myBalanceViewList.get(theNewPosition);
                        DBBalanceView theBalView = new DBBalanceView(myContext);
                        theBalView.setCurrent(Long.valueOf(position));
                        if(myUpdateButton !=null) {
                            myUpdateButton.setVisibility(View.INVISIBLE);

                        }
                        notifyDataSetChanged();
                        //Toast.makeText(myContext.getApplicationContext(), "Current Changed: "+Long.valueOf(position).toString(),
                        //        Toast.LENGTH_SHORT).show();

                    }

                });
            }



        }else{ //header initialization here if needed

        }

    }

    @Override
    public int getItemCount() {
        return myBalanceViewList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_HEAD;

        return TYPE_LIST;
    }
}

