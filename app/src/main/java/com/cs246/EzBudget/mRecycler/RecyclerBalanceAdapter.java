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
import android.widget.Toast;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mFragments.DispBalDataFragment;
import com.cs246.EzBudget.mFragments.DispBalViewFragment;
import com.cs246.EzBudget.mFragments.DispCategoryFragment;


import java.util.ArrayList;

/**
 * This class is the list View to show the categories in the screen
 */
public class RecyclerBalanceAdapter extends RecyclerView.Adapter<RecyclerViewHolderBalanceData> {


    private ArrayList<BalanceData> myBalanceDataList = new ArrayList<>();
    private Context myContext;

    private int myAction;
    private FragmentManager myFagmentManager;
    private boolean isRecurrent;

    public RecyclerBalanceAdapter(ArrayList<BalanceData> BalanceDataList, Context theContext, FragmentManager theFrag, boolean theRec , int theAction) {
        this.myBalanceDataList = BalanceDataList;
        this.myContext = theContext;
        myFagmentManager = theFrag;
        isRecurrent = theRec;
        myAction = theAction;

    }

    // Initialize View
    @NonNull
    @Override
    public RecyclerViewHolderBalanceData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_balance_data_item, parent, false);

        return new RecyclerViewHolderBalanceData(view,myFagmentManager);
    }

    //Bind Data
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderBalanceData holder, int position) {
        if(isRecurrent)
            holder.myDueDate.setText(myBalanceDataList.get(position).getDueDateRecurrentHuman());
        else
            holder.myDueDate.setText(myBalanceDataList.get(position).getDueDateHuman());
        holder.myValue.setText(Double.toString(myBalanceDataList.get(position).getValue()));
        holder.myDescription.setText(myBalanceDataList.get(position).getDescription());
        int oper = myBalanceDataList.get(position).getStatus();
        if (oper == PAY_STATUS.PAID_RECEIVED) {
            //todo: change color depending of the status payd or not
            //holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_add_circle_green_24dp,myContext.getTheme()));
        }else if (oper == PAY_STATUS.UNPAID_UNRECEIVED) {
            //holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_remove_circle_red_24dp, myContext.getTheme()));
        }else {
            //holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_warning_black_24dp, myContext.getTheme()));
        }
        //set background color

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2F2"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        if(myAction == LIST_ACTION.ACT_LIST_ADD) {
            holder.setItemClickListener(new RecyclerClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {

                    Long id_To_Search = myBalanceDataList.get(position).getID();
                    Bundle bundle = new Bundle();
                    Long myMessage = id_To_Search;
                    bundle.putLong("id", myMessage);
                    if (isRecurrent) bundle.putBoolean("isRec", true);
                    else bundle.putBoolean("isRec", false);
                    bundle.putBoolean("showRec", true);
                    DispBalDataFragment theFrag = DispBalDataFragment.newInstance();
                    theFrag.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerID, theFrag, "DISPLAY_BAL_DATA_FRAG");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                }

            });
        }else if(myAction == LIST_ACTION.ACT_LIST_CHOOSE){
            //Todo:  add click listener to add the chosen Recurrent data into de DalanceData
            holder.setItemClickListener(new RecyclerClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {
                    //Long id_To_Search = myBalanceDataList.get(position).getID();
                    BalanceData theRecord = myBalanceDataList.get(position);
                    DBBalanceData theBalDataDatabase = new DBBalanceData(myContext);
                    if(theBalDataDatabase.insert(theRecord,true)>0){
                        Toast.makeText(myContext.getApplicationContext(), "Added Successfully",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(myContext.getApplicationContext(), "Not Added",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            });
        }

    }

    @Override
    public int getItemCount() {
        return myBalanceDataList.size();
    }

}