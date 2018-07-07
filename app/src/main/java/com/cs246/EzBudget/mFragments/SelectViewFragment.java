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

import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mBackGrounds.BackGroundBalView;
import com.cs246.EzBudget.mRecycler.CommonFragments;
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
    private  BackGroundBalView  myBackGroundAction=null;
private DBHelper myDB;

    @NonNull
    static public SelectViewFragment newInstance(){
        return new SelectViewFragment();
    }

    public SelectViewFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {


        if(myBackGroundAction.getStatus() == AsyncTask.Status.PENDING){
            // My AsyncTask has not started yet
        }

        if(myBackGroundAction.getStatus() != AsyncTask.Status.RUNNING){
            // My AsyncTask is currently doing work in doInBackground()
        setup();
        }

        if(myBackGroundAction.getStatus() == AsyncTask.Status.FINISHED){
            // My AsyncTask is done and onPostExecute was called
        }
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
        myFagmentManager = getActivity().getSupportFragmentManager();
        myDB = DBHelper.getInstance(getActivity());
        /*
        myDB.addBalanceViewChangedListener(new DBHelper.BalanceViewChangedListener() {
            @Override
            public void onChanged() {


            }
        });
*/
        setup();
        return myView;
    }


    void setup(){
        //Toast.makeText(getActivity(), "Database Changed and setup executed",
        //        Toast.LENGTH_SHORT).show();
        myProgress.setVisibility(View.INVISIBLE);
        //AsyncTask instances can only be used one time.
        myBackGroundAction = new BackGroundBalView(myRecyclerView,myProgress,getActivity(),myFagmentManager, RecyclerBalViewAdapter.ACTION_CHOOSE,null);
        myBackGroundAction.execute();
    }
}
