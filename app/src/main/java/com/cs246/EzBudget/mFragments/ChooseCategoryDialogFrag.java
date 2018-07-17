package com.cs246.EzBudget.mFragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.support.v4.app.FragmentManager;

import com.cs246.EzBudget.mBackGrounds.BackGroundCategory;
import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mRecycler.RecyclerViewHolderCategory;

/**
 * This Class is used to open a Dialog used to choose Category records to be added to BalanceData/BalanceDataRec, edited or deleted
 */
public class ChooseCategoryDialogFrag extends DialogFragment {

    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    BackGroundCategory myBackGroundCat;
    Button myGetIncome;
    Button myGetOutcome;
    Button myGetInformative;
    FragmentManager myFragmentManager;

    DBHelper mydb;
    private ProgressBar myProgress=null;

    @Override
    public void onResume() {
        if(myBackGroundCat!=null){

            if (myBackGroundCat.getStatus() != AsyncTask.Status.RUNNING) {
                // My AsyncTask is currently doing work in doInBackground()
                setup();
            }
        }
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fraglayout,container);

        myGetIncome = (Button) rootView.findViewById(R.id.buttonIncomes);
        myGetOutcome = (Button) rootView.findViewById(R.id.buttonOutcomes);
        myGetInformative = (Button) rootView.findViewById(R.id.buttonInfo);
        myFragmentManager = getActivity().getSupportFragmentManager();


        this.getDialog().setTitle("Categories");


        myRecyclerView= (RecyclerView) rootView.findViewById(R.id.mRecyerID);
        myLayoutManager = new LinearLayoutManager(this.getActivity());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar) rootView.findViewById(R.id.myBar2);
        myProgress.setVisibility(View.INVISIBLE);
        mydb = DBHelper.getInstance(this.getActivity());



        myGetIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myBackGroundCat = new BackGroundCategory(myRecyclerView,myProgress,ChooseCategoryDialogFrag.this.getActivity(),  BackGroundCategory.CAT_INC, RecyclerViewHolderCategory.LAYOUT_TWO,this);
                myBackGroundCat.execute();
            }
        });
        myGetOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new BackGroundCategory(myRecyclerView,myProgress,ChooseCategoryDialogFrag.this.getActivity(),  BackGroundCategory.CAT_OUT, RecyclerViewHolderCategory.LAYOUT_TWO,this).execute();
                myBackGroundCat.execute();
            }
        });
        myGetInformative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new BackGroundCategory(myRecyclerView,myProgress,ChooseCategoryDialogFrag.this.getActivity(),  BackGroundCategory.CAT_INFO, RecyclerViewHolderCategory.LAYOUT_TWO, this).execute();
                myBackGroundCat.execute();
            }
        });

        setup();
        return rootView;
    }

    /**
     * Class The BackGround Activity and fill the list with Data
     */
    void setup(){
        myBackGroundCat = new BackGroundCategory(myRecyclerView,myProgress,getActivity(),  BackGroundCategory.CAT_ALL, RecyclerViewHolderCategory.ACTION_CHOOSE,myFragmentManager,null);
        myBackGroundCat.execute();
    }
}
