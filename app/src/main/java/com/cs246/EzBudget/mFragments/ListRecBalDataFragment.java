package com.cs246.EzBudget.mFragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.RECURRENT;
import com.cs246.EzBudget.mBackGrounds.BackGroundBalData;
import com.cs246.EzBudget.mBackGrounds.BackGroundRecData;
import com.cs246.EzBudget.mRecycler.CommonBalData;
import com.cs246.EzBudget.mRecycler.CommonBalView;
import com.cs246.EzBudget.mRecycler.CommonFragments;
import com.cs246.EzBudget.mRecycler.LIST_ACTION;

/**
 * This Class will display a RecyclerView with the Balance Data Recurrent records to allow several actions with the records
 */
public class ListRecBalDataFragment extends Fragment {






    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private View myView;
    private FragmentManager myFagmentManager;
    private ProgressBar myProgress=null;
    private Button myAddButton;
    private Button myUpdateButton;

    private BackGroundRecData myBackGroundTask;

    @NonNull
    static public ListRecBalDataFragment newInstance(){
        return new ListRecBalDataFragment();
    }

    public ListRecBalDataFragment() {
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
        //myProgress.setVisibility(View.INVISIBLE);
        myFagmentManager = getActivity().getSupportFragmentManager();

        myAddButton = (Button) myView.findViewById(R.id.listRecAddNew);
        myUpdateButton = (Button) myView.findViewById(R.id.listRecUpdate);

        myAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                Long myMessage = Long.valueOf(-1);
                bundle.putLong("id", myMessage );
                bundle.putBoolean("isRec", true );
                bundle.putBoolean("showRec", true);
                DispBalDataFragment fragInfo = DispBalDataFragment.newInstance();
                CommonFragments.dispBalData = fragInfo;
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

        myUpdateButton.setVisibility(View.INVISIBLE);


        setup();

        return myView;
    }

    void setup(){

        myBackGroundTask = new BackGroundRecData(myRecyclerView,myProgress,getActivity(),myFagmentManager,BackGroundRecData.BAL_ALL, LIST_ACTION.ACT_LIST_ADD , myUpdateButton,null);

        myBackGroundTask.execute();
    }
}
