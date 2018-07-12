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
import android.widget.Toast;

import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mBackGrounds.BackGroundBalView;
import com.cs246.EzBudget.mRecycler.CommonBalView;
import com.cs246.EzBudget.mRecycler.RecyclerBalViewAdapter;

/**
 * This Class will display a RecyclerView with the Balance View records to allow several actions with the records
 */
public class ListBalViewFragment extends Fragment {


    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private View myView;
    private FragmentManager myFagmentManager;
    private ProgressBar myProgress=null;
    private Button myAddButton;
    private Button myUpdateButton;
    private BackGroundBalView myBackGroundAction;
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

        myUpdateButton = (Button) myView.findViewById(R.id.listViewUpdate);

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

        myUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BalanceView myBalView = CommonBalView.currentItem;

                if (myBalView != null) {
                    Long id_To_Search =myBalView.getID();
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id_To_Search);
                    DispBalViewFragment fragInfo = DispBalViewFragment.newInstance();
                    fragInfo.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerID, fragInfo);
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
        //AsyncTask instances can only be used one time.
    myBackGroundAction = new BackGroundBalView(myRecyclerView,myProgress,getActivity(),myFagmentManager , RecyclerBalViewAdapter.ACTION_ADD,myUpdateButton);
    myBackGroundAction.execute();


}



}
