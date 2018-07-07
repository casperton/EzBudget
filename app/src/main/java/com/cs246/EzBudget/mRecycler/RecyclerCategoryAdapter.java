package com.cs246.EzBudget.mRecycler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mFragments.DispBalDataFragment;

import java.util.ArrayList;

/**
 * This class is the list View to show the categories in the screen
 */
public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerViewHolderCategory> {


    private ArrayList<Category> myCategoryList = new ArrayList<>();
    private Context myContext;
    private int myLayout;
    //indicate the selected row
    private int myRowIndex = -1;
    private Button myUpdateButton;

    private FragmentManager myFagmentManager;


    public RecyclerCategoryAdapter(ArrayList<Category> categoryList, Context theContext, int theLayOut, FragmentManager theFrag, Button theUpdateButton) {
        this.myCategoryList = categoryList;
        this.myContext = theContext;
        myLayout = theLayOut;
        myFagmentManager = theFrag;
        myUpdateButton = theUpdateButton;
    }

    // Initialize View
    @NonNull
    @Override
    public RecyclerViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_item, parent, false);

        return new RecyclerViewHolderCategory(view,myLayout);
    }

    //Bind Data
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderCategory holder, int position) {

        holder.name.setText(myCategoryList.get(position).getName());

        //set background color

        //set background color
        //set highlight color
        //set icon

        if (myCategoryList.get(position).isCredit()){
            holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_add_circle_green_24dp,myContext.getTheme()));
            if((myRowIndex==position)){
                holder.itemView.setBackgroundColor(Color.parseColor("#00FF40"));
            }else{
                if (position % 2 == 0)
                    holder.itemView.setBackgroundColor(Color.parseColor("#A9F5A9"));
                else
                    holder.itemView.setBackgroundColor(Color.parseColor("#58FA58"));
            }

        }else if(myCategoryList.get(position).isDebit()){
            holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_remove_circle_red_24dp,myContext.getTheme()));
            if((myRowIndex==position)){
                holder.itemView.setBackgroundColor(Color.parseColor("#DF3A01"));
            }else{
                if (position % 2 == 0)
                    holder.itemView.setBackgroundColor(Color.parseColor("#F78181"));
                else
                    holder.itemView.setBackgroundColor(Color.parseColor("#FA5858"));
            }
        }else if(myCategoryList.get(position).isInformative()){
            holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_info_outline_black_24dp,myContext.getTheme()));
            if((myRowIndex==position)){
                holder.itemView.setBackgroundColor(Color.parseColor("#CACACA"));
            }else{
                if (position % 2 == 0)
                    holder.itemView.setBackgroundColor(Color.parseColor("#F2F2F2"));
                else
                    holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }

        }else{
            holder.icon.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_warning_black_24dp,myContext.getTheme()));
        }

        if (holder.getAction() == RecyclerViewHolderCategory.ACTION_CHOOSE) {
            holder.setItemClickListener(new RecyclerClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {

                    int theNewPosition = position;

                    myRowIndex = position;
                    CommonCategory.currentItem = myCategoryList.get(position);
                    notifyDataSetChanged();
                    if(myUpdateButton !=null) {
                        myUpdateButton.setVisibility(View.INVISIBLE);

                    }




                }

            });
        }
        if (holder.getAction() == RecyclerViewHolderCategory.ACTION_ADD) {
            holder.setItemClickListener(new RecyclerClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {

                    //String id_To_Search = myCategoryList.get(position).getName();
                    myRowIndex = position;
                    CommonCategory.currentItem = myCategoryList.get(position);
                    notifyDataSetChanged();
                    if(myUpdateButton !=null) {
                        myUpdateButton.setVisibility(View.VISIBLE);

                    }
                    //DispBalDataFragment myFragment =  (DispBalDataFragment) myFagmentManager.findFragmentByTag("DISPLAY_BAL_DATA_FRAG");
                    //myFragment.setCateGoryText(id_To_Search);
                    // to do the same if using activity instead of fragment
                    // EditText myCat = (EditText) myFragment.getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.editBalDataCategory);
                    // myCat.setText(id_To_Search);

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
