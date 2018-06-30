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

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mFragments.DispBalViewFragment;

import java.util.ArrayList;

/**
 * This class is the list View to show the categories in the screen
 */
public class RecyclerBalDataAdapter extends RecyclerView.Adapter<RecyclerViewHolderBalanceData> {


    private ArrayList<BalanceData> myBalanceDataList = new ArrayList<>();
    private Context myContext;
    private int myLayout;
    // private BalanceViewShowFragment teste;
    private FragmentManager myFagmentManager;
    public RecyclerBalDataAdapter(ArrayList<BalanceData> categoryList, Context theContext, FragmentManager theFrag/*, int theLayOut */) {
        this.myBalanceDataList = categoryList;
        this.myContext = theContext;
        //myLayout = theLayOut;
        myFagmentManager = theFrag;
    }

    // Initialize View
    @NonNull
    @Override
    public RecyclerViewHolderBalanceData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bal_view_item, parent, false);

        return new RecyclerViewHolderBalanceData(view,myFagmentManager/*,myLayout*/);
    }

    //Bind Data
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderBalanceData holder, int position) {
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

        holder.setItemClickListener(new RecyclerClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {

                Long id_To_Search = myBalanceDataList.get(position).getID();
                Bundle bundle = new Bundle();
                Long myMessage = id_To_Search;
                bundle.putLong("id", myMessage );
                DispBalViewFragment theFrag = DispBalViewFragment.newInstance();
                theFrag.setArguments(bundle);
                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, theFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }

        });
    }



    @Override
    public int getItemCount() {
        return myBalanceDataList.size();
    }

}
