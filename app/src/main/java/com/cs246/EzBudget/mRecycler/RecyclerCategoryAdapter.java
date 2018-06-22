package com.cs246.EzBudget.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.DispCategory;
import com.cs246.EzBudget.TVShowFragment;

import java.util.ArrayList;

/**
 * This class is the list View to show the categories in the screen
 */
public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {


    private ArrayList<Category> myCategoryList = new ArrayList<>();
    private Context myContext;
    private int myLayout;
    private TVShowFragment teste;
    public RecyclerCategoryAdapter(ArrayList<Category> categoryList, Context theContext, int theLayOut, TVShowFragment theFrag) {
        this.myCategoryList = categoryList;
        this.myContext = theContext;
        myLayout = theLayOut;
        teste = theFrag;
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

        holder.name.setText(myCategoryList.get(position).getName());
        //holder.id.setText(Integer.toString(myCategoryList.get(position).getID()));
        int oper = myCategoryList.get(position).getOperation();
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
            holder.setItemClickListener(new RecyclerClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {

                    int id_To_Search = myCategoryList.get(position).getID();

                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id_To_Search);

                    Intent intent = new Intent(myContext.getApplicationContext(), DispCategory.class);
                    intent.putExtras(dataBundle);
                    myContext.startActivity(intent);
                }

            });
        }
        if (holder.getLayOut() == RecyclerViewHolder.LAYOUT_TWO) {
            holder.setItemClickListener(new RecyclerClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {

                    String id_To_Search = myCategoryList.get(position).getName();


                    EditText myCat = (EditText) teste.getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.editBalDataCategory);
                        myCat.setText(id_To_Search);

                        //Todo: Close the fragment

                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return myCategoryList.size();
    }

}
