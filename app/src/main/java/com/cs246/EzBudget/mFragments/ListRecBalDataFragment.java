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
import com.cs246.EzBudget.mBackGrounds.BackGroundBalData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRecBalDataFragment extends Fragment {






    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private View myView;
    private FragmentManager myFagmentManager;
    private ProgressBar myProgress=null;
    private Button myAddButton;
    @NonNull
    static public ListRecBalDataFragment newInstance(){
        return new ListRecBalDataFragment();
    }

    public ListRecBalDataFragment() {
        // Required empty public constructor
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

        myAddButton = (Button) myView.findViewById(R.id.listRecAddNew);

        myAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                Long myMessage = Long.valueOf(-1);
                bundle.putLong("id", myMessage );
                bundle.putBoolean("isRecurrent", true );
                DispBalDataFragment fragInfo = DispBalDataFragment.newInstance();
                fragInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, fragInfo,"DISPLAY_REC_DATA_FRAG");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


       // new BackGroundRecData(myRecyclerView,myProgress,getActivity(),myFagmentManager,BackGroundBalData.BAL_ALL ).execute();

        return myView;
    }

}
