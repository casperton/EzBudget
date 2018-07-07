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
import com.cs246.EzBudget.mRecycler.RecyclerBalViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectViewFragment extends Fragment {



    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private View myView;
    private FragmentManager myFagmentManager;
    private ProgressBar myProgress=null;
    private  BackGroundBalView  myBachgroundAction;


    @NonNull
    static public SelectViewFragment newInstance(){
        return new SelectViewFragment();
    }

    public SelectViewFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {


        myBachgroundAction.execute();
        super.onResume();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_select_view, container, false);
        super.onCreate(savedInstanceState);


        myRecyclerView = (RecyclerView) myView.findViewById(R.id.listViewRecicler);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar) myView.findViewById(R.id.listViewBar);
        myProgress.setVisibility(View.INVISIBLE);
        myFagmentManager = getActivity().getSupportFragmentManager();

        myBachgroundAction = new BackGroundBalView(myRecyclerView,myProgress,getActivity(),myFagmentManager, RecyclerBalViewAdapter.ACTION_CHOOSE,null);

        myBachgroundAction.execute();
        return myView;
    }

}
