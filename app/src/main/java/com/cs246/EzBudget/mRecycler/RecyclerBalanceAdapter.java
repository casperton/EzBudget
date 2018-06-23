package com.cs246.EzBudget.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.R;


import java.util.ArrayList;

/**
 * This class is the list View to show the categories in the screen
 */
public class RecyclerBalanceAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {


    private ArrayList<BalanceData> myBalanceDataList = new ArrayList<>();
    private Context myContext;
    private int myLayout;

    public RecyclerBalanceAdapter(ArrayList<BalanceData> BalanceDataList, Context theContext, int theLayOut) {
        this.myBalanceDataList = BalanceDataList;
        this.myContext = theContext;
        myLayout = theLayOut;

    }

    // Initialize View
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_item, parent, false);

        return new RecyclerViewHolder(view,myLayout);
    }

    //Bind Data
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.name.setText(myBalanceDataList.get(position).getValue().toString());
        //holder.id.setText(Integer.toString(myBalanceDataList.get(position).getID()));
        int oper = myBalanceDataList.get(position).getOperation();
        if (oper == OPERATION.CREDIT)
            holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_add_circle_green_24dp,myContext.getTheme()));
        else if (oper == OPERATION.DEBIT)
            holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_remove_circle_red_24dp,myContext.getTheme()));
        else if (oper == OPERATION.INFORMATIVE)
            holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_info_outline_black_24dp,myContext.getTheme()));
        else
            holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_warning_black_24dp,myContext.getTheme()));

        //set background color

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2F2"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        if (holder.getLayOut() == RecyclerViewHolder.LAYOUT_ONE) {
            /*
            holder.setItemClickListener(new RecyclerClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {

                    int id_To_Search = myBalanceDataList.get(position).getID();

                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id_To_Search);

                    Intent intent = new Intent(myContext.getApplicationContext(), DispBalanceData.class);
                    intent.putExtras(dataBundle);
                    myContext.startActivity(intent);
                }

            });
            */
        }
        if (holder.getLayOut() == RecyclerViewHolder.LAYOUT_TWO) {
            /*
            holder.setItemClickListener(new RecyclerClickListener() {

                @Override
                public void OnClick(View view, int position, boolean isLongClick) {

                    String id_To_Search = myBalanceDataList.get(position).getName();


                    EditText myCat = (EditText) teste.getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.editBalDataBalanceData);
                    myCat.setText(id_To_Search);

                    //Todo: Close the fragment

                }

            });
        */
        }
    }

    @Override
    public int getItemCount() {
        return myBalanceDataList.size();
    }

}