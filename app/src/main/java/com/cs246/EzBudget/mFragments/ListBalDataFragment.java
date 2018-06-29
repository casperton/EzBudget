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
import com.cs246.EzBudget.mBackGrounds.BackGroundBalView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListBalDataFragment extends Fragment {






    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private View myView;
    private FragmentManager myFagmentManager;
    private ProgressBar myProgress=null;
    private Button myAddButton;
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

        myAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                Long myMessage = Long.valueOf(-1);
                bundle.putLong("id", myMessage );
                DispBalDataFragment fragInfo = DispBalDataFragment.newInstance();
                fragInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, fragInfo,"DISPLAY_BAL_DATA_FRAG");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        //new BackGroundBalData(myRecyclerView,myProgress,getActivity(),myFagmentManager ).execute();

        return myView;
    }
}
