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
import com.cs246.EzBudget.mBackGrounds.BackGroundCategory;
import com.cs246.EzBudget.mRecycler.RecyclerBalViewAdapter;
import com.cs246.EzBudget.mRecycler.RecyclerViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListBalViewFragment extends Fragment {


    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private View myView;
    private FragmentManager myFagmentManager;
    private ProgressBar myProgress=null;
    private Button myAddButton;

    @NonNull
    static public ListBalViewFragment newInstance(){
        return new ListBalViewFragment();
    }

    public ListBalViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_list_bal_view, container, false);
        super.onCreate(savedInstanceState);


        myRecyclerView = (RecyclerView) myView.findViewById(R.id.listViewRecicler);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar) myView.findViewById(R.id.listViewBar);
        myProgress.setVisibility(View.INVISIBLE);
        myFagmentManager = getActivity().getSupportFragmentManager();

        myAddButton = (Button) myView.findViewById(R.id.listViewAddNew);

        myAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long id_To_Search = Long.valueOf(-1);
                Bundle bundle = new Bundle();
                Long myMessage = id_To_Search;
                bundle.putLong("id", myMessage );
                DispBalViewFragment fragInfo = DispBalViewFragment.newInstance();
                fragInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, fragInfo);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        new BackGroundBalView(myRecyclerView,myProgress,getActivity(),myFagmentManager , RecyclerBalViewAdapter.ACTION_ADD).execute();

       return myView;
    }





}
