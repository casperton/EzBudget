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
import android.widget.EditText;

import com.cs246.EzBudget.BalanceView;
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
    private static final String TAG = RecyclerBalViewAdapter.class.getName();

    private static final int TYPE_HEAD=0;
    private static final int TYPE_LIST=1;

    private ArrayList<BalanceView> myBalanceViewList = new ArrayList<>();
    private Context myContext;
    private int myLayout;
   // private BalanceViewShowFragment teste;
    private FragmentManager myFagmentManager;
    public RecyclerBalViewAdapter(ArrayList<BalanceView> categoryList, Context theContext, FragmentManager theFrag/*, int theLayOut */) {
        this.myBalanceViewList = categoryList;
        this.myContext = theContext;
        //myLayout = theLayOut;
        myFagmentManager = theFrag;
    }

    // Initialize View
    @NonNull
    @Override
    public RecyclerViewHolderBalView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(MainActivity.DEBUG) Log.i(TAG, "onCreateViewHolder()");

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

            if (theNewPosition % 2 == 0)
                holder.itemView.setBackgroundColor(Color.parseColor("#F2F2F2"));
            else
                holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));

            holder.setItemClickListener(new RecyclerClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {
                    int theNewPosition = position - 1;
                    Long id_To_Search = myBalanceViewList.get(theNewPosition).getID();
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id_To_Search);
                    DispBalViewFragment fragInfo = DispBalViewFragment.newInstance();
                    fragInfo.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerID, fragInfo);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                }

            });
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

