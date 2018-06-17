package com.chabries.kirk.ezbudget;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class is the list View to show the categories in the screen
 */
public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.myViewHolder> {


    private ArrayList<Category> myCategoryList = new ArrayList<>();
    private Context myContext;

    public RecyclerCategoryAdapter(ArrayList<Category> categoryList, Context theContext) {
        this.myCategoryList = categoryList;
        this.myContext = theContext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_category_item,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.name.setText(myCategoryList.get(position).getName());
        holder.id.setText(Integer.toString(myCategoryList.get(position).getID()));

        holder.setItemClickListener(new RecyclerClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {

                int id_To_Search = myCategoryList.get(position).getID();

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(myContext.getApplicationContext(),DispCategory.class);

                intent.putExtras(dataBundle);
                myContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCategoryList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView id,name;
        private RecyclerClickListener myItemClickListener;

        public myViewHolder(View itemView) {
            super(itemView);
            id = (TextView)itemView.findViewById(R.id.textViewCatID);
            name = (TextView) itemView.findViewById(R.id.textViewCatName);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

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
}
