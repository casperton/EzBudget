package com.cs246.EzBudget.mFragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mBackGrounds.BackGroundBalData;
import com.cs246.EzBudget.mBackGrounds.BackGroundRecData;
import com.cs246.EzBudget.mRecycler.CommonBalData;
import com.cs246.EzBudget.mRecycler.CommonFragments;
import com.cs246.EzBudget.mRecycler.LIST_ACTION;

/**
 * This fragment is called by the ($) button to add data to the summary view
 */
public class ChooseRecBalDataDialogFrag extends DialogFragment {





    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private View myView;
    private FragmentManager myFagmentManager;
    private ProgressBar myProgress=null;
    private Button myAddButtonNewData;
    private Button myUpdateButton;
    private Button myAddToSummaryButton;
    private BackGroundRecData myBackGroundTask;


    @NonNull
    static public ChooseRecBalDataDialogFrag newInstance(){
        return new ChooseRecBalDataDialogFrag();
    }

    public ChooseRecBalDataDialogFrag() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        if (myBackGroundTask !=null) {

            if (myBackGroundTask.getStatus() != AsyncTask.Status.RUNNING) {
                // My AsyncTask is currently doing work in doInBackground()
                setup();
            }

        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_list_rec_bal_data, container, false);
        super.onCreate(savedInstanceState);


        myRecyclerView = (RecyclerView) myView.findViewById(R.id.listRecRecicler);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar) myView.findViewById(R.id.listRecBar);
        myProgress.setVisibility(View.INVISIBLE);
        myFagmentManager = getActivity().getSupportFragmentManager();

        myAddButtonNewData = (Button) myView.findViewById(R.id.listRecAddNew);
        myUpdateButton = (Button) myView.findViewById(R.id.listRecUpdate);
        myAddToSummaryButton = (Button) myView.findViewById(R.id.listRecAddToSummary);

        myAddButtonNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                Long myMessage = Long.valueOf(-1);
                bundle.putLong("id", myMessage );
                bundle.putBoolean("isRec", false ); //is not a recurrent data
                bundle.putBoolean("showRec", false); //do not show recurrence layout
                bundle.putBoolean("showStatus", false); //do not show status layout
                DispBalDataFragment fragInfo = DispBalDataFragment.newInstance();
                fragInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, fragInfo,"DISPLAY_BAL_DATA_FRAG");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        myUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BalanceData myBalData = CommonBalData.currentItem;

                if (myBalData != null) {

                    Long id_To_Search = myBalData.getID();
                    Toast.makeText(getActivity(), "ID to SEARCH: "+id_To_Search.toString(),
                            Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id_To_Search);
                    bundle.putBoolean("isRec", true);
                    bundle.putBoolean("showRec", true);
                    DispBalDataFragment fragInfo = DispBalDataFragment.newInstance();
                    CommonFragments.dispBalData = fragInfo;
                    fragInfo.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerID, fragInfo, "DISPLAY_BAL_DATA_FRAG");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            }
        });

        myAddToSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BalanceData myBalData = CommonBalData.currentItem;

                if (myBalData != null) {

                    Long id_To_Search = myBalData.getID();
                    Toast.makeText(getActivity(), "ID to SEARCH: "+id_To_Search.toString(),
                            Toast.LENGTH_SHORT).show();
                    //old action
                    //int theNewPosition = position-1;
                    //myRowIndex = position;
                    //CommonBalData.currentItem = myBalanceDataList.get(theNewPosition);
                    //BalanceData theRecord = myBalanceDataList.get(theNewPosition);
                    DBBalanceData theBalDataDatabase = new DBBalanceData(getActivity());
                    //the isFromRec indicates to the database classto make all calculations and add this
                    //record to the current view Period

                    if (theBalDataDatabase.insert( myBalData,true) > 0) {
                        Toast.makeText(getActivity(), "Added Successfully",
                                Toast.LENGTH_SHORT).show();
                        if (CommonFragments.summaryFrag!=null) CommonFragments.summaryFrag.onResume();

                    } else {
                        Toast.makeText(getActivity(), "Not Added",
                                Toast.LENGTH_SHORT).show();
                    }                }

            }
        });

        setup();

        return myView;
    }
    private void setup(){
        myBackGroundTask = new BackGroundRecData(myRecyclerView,myProgress,getActivity(),myFagmentManager, BackGroundRecData.BAL_ALL, LIST_ACTION.ACT_LIST_CHOOSE, myUpdateButton, myAddToSummaryButton );

        myBackGroundTask.execute();
    }
}


