package com.cs246.EzBudget;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.mRecycler.RecyclerViewHolder;


public class CategoryShowFragment extends DialogFragment {

    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    BackGroundCategory myBackGroundCat;
    Button myGetIncome;
    Button myGetOutcome;
    Button myGetInformative;


    DBHelper mydb;
    private ProgressBar myProgress=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fraglayout,container);

        myGetIncome = (Button) rootView.findViewById(R.id.buttonIncomes);
        myGetOutcome = (Button) rootView.findViewById(R.id.buttonOutcomes);
        myGetInformative = (Button) rootView.findViewById(R.id.buttonInfo);



        this.getDialog().setTitle("Categories");

        //myRecyclerView = (RecyclerView) rootView.findViewById(R.id.ReciclerViewCategory);
        myRecyclerView= (RecyclerView) rootView.findViewById(R.id.mRecyerID);
        myLayoutManager = new LinearLayoutManager(this.getActivity());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar) rootView.findViewById(R.id.myBar2);
        myProgress.setVisibility(View.INVISIBLE);
        mydb = new DBHelper(this.getActivity());
        myBackGroundCat = new BackGroundCategory(myRecyclerView,myProgress,this.getActivity(),  BackGroundCategory.CAT_ALL, RecyclerViewHolder.LAYOUT_TWO,this);


        myBackGroundCat.execute();

        myGetIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myBackGroundCat = new BackGroundCategory(myRecyclerView,myProgress,CategoryShowFragment.this.getActivity(),  BackGroundCategory.CAT_INC, RecyclerViewHolder.LAYOUT_TWO,this);
                myBackGroundCat.execute();
            }
        });
        myGetOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new BackGroundCategory(myRecyclerView,myProgress,CategoryShowFragment.this.getActivity(),  BackGroundCategory.CAT_OUT, RecyclerViewHolder.LAYOUT_TWO,this).execute();
                myBackGroundCat.execute();
            }
        });
        myGetInformative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new BackGroundCategory(myRecyclerView,myProgress,CategoryShowFragment.this.getActivity(),  BackGroundCategory.CAT_INFO, RecyclerViewHolder.LAYOUT_TWO, this).execute();
                myBackGroundCat.execute();
            }
        });
        return rootView;
    }
}