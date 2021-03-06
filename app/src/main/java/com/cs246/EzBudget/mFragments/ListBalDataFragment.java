package com.cs246.EzBudget.mFragments;


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

import com.cs246.EzBudget.R;
import com.cs246.EzBudget.RECURRENT;
import com.cs246.EzBudget.mBackGrounds.BackGroundBalData;
import com.cs246.EzBudget.mBackGrounds.BackGroundBalView;
import com.cs246.EzBudget.mRecycler.CommonBalData;
import com.cs246.EzBudget.mRecycler.CommonFragments;

/**
 * This Class will display a RecyclerView with the Balance Data records to allow several actions with the records
 */
public class ListBalDataFragment extends Fragment {






    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private View myView;
    private FragmentManager myFagmentManager;
    private ProgressBar myProgress=null;
    private Button myAddButton;
    private Button myUpdateButton;
    @NonNull
    static public ListBalDataFragment newInstance(){
        return new ListBalDataFragment();
    }

    public ListBalDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_list_bal_data, container, false);
        super.onCreate(savedInstanceState);


        myRecyclerView = (RecyclerView) myView.findViewById(R.id.listBalDataRecicler);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar) myView.findViewById(R.id.listBalDataBar);
        myProgress.setVisibility(View.INVISIBLE);
        myFagmentManager = getActivity().getSupportFragmentManager();

        myAddButton = (Button) myView.findViewById(R.id.listBalDataAddNew);
        myUpdateButton = (Button) myView.findViewById(R.id.listBalDataUpdate);
        myAddButton.setVisibility(View.GONE);
        myAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                Long myMessage = Long.valueOf(-1);
                bundle.putLong("id", myMessage );
                bundle.putBoolean("isRec", false );

                DispBalDataFragment theFrag = DispBalDataFragment.newInstance();
                CommonFragments.dispBalData = theFrag;
                theFrag.setArguments(bundle);
                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, theFrag,"DISPLAY_BAL_DATA_FRAG");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        myUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long id_To_Search = CommonBalData.currentItem.getID();
                Bundle bundle = new Bundle();
                bundle.putLong("id", id_To_Search);
                if (CommonBalData.currentItem.getRecPeriod()== RECURRENT.ONCE) bundle.putBoolean("isRec", false);
                else bundle.putBoolean("isRec", false);
                bundle.putBoolean("showRec", false);
                DispBalDataFragment theFrag = DispBalDataFragment.newInstance();
                theFrag.setArguments(bundle);
                CommonFragments.dispBalData = theFrag;
                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, theFrag, "DISPLAY_BAL_DATA_FRAG");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        new BackGroundBalData(myRecyclerView,myProgress,getActivity(),myFagmentManager,BackGroundBalData.BAL_ALL ,myUpdateButton).execute();

        return myView;
    }
}
