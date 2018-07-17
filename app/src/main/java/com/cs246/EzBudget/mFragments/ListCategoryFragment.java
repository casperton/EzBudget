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

import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mBackGrounds.BackGroundCategory;
import com.cs246.EzBudget.mRecycler.CommonCategory;
import com.cs246.EzBudget.mRecycler.CommonFragments;
import com.cs246.EzBudget.mRecycler.RecyclerViewHolderCategory;

/**
 * Used by the menu to on the mainActivity to list the Categories
 * Allow the user to add new, delete and update Categories
 */
public class ListCategoryFragment extends Fragment {

    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private ProgressBar myProgress=null;
    private View myView;

    private Button myAddButton;
    private Button myUpdateButton;
    private FragmentManager myFagmentManager;
    private BackGroundCategory myBackGroundAction;

    @NonNull
    static public ListCategoryFragment newInstance(){
        ListCategoryFragment theFrag = new ListCategoryFragment();

        return theFrag;
    }

    /**
     * Required empty public constructor
     */
    public ListCategoryFragment() {
        //
    }

    @Override
    public void onResume() {
        if (myBackGroundAction !=null) {
            if (myBackGroundAction.getStatus() == AsyncTask.Status.PENDING) {
                // My AsyncTask has not started yet
            }

            if (myBackGroundAction.getStatus() != AsyncTask.Status.RUNNING) {
                // My AsyncTask is currently doing work in doInBackground()
                setup();
            }

            if (myBackGroundAction.getStatus() == AsyncTask.Status.FINISHED) {
                // My AsyncTask is done and onPostExecute was called
            }
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_list_category, container, false);

        myRecyclerView = (RecyclerView) myView.findViewById(R.id.listCategoryRecicler);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar)  myView.findViewById(R.id.listCategoryBar);
        myProgress.setVisibility(View.INVISIBLE);

        myAddButton = (Button)  myView.findViewById(R.id.listCategoryAddNew);
        myUpdateButton = (Button)  myView.findViewById(R.id.listCategoryUpdate);
        myFagmentManager = getActivity().getSupportFragmentManager();

        myAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long id_To_Search = Long.valueOf(-1);
                Bundle bundle = new Bundle();
                bundle.putLong("id", id_To_Search );
                DispCategoryFragment fragInfo = DispCategoryFragment.newInstance();
                CommonFragments.dispCategory = fragInfo;
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

                Category theCategory = CommonCategory.currentItem;
                //Toast.makeText(getActivity(), "Clicked: ",
                  //      Toast.LENGTH_SHORT).show();
                if (theCategory != null) {
                    Long id_To_Search =theCategory.getID();
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id_To_Search);
                    DispCategoryFragment fragInfo = DispCategoryFragment.newInstance();
                    CommonFragments.dispCategory = fragInfo;
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

    /**
     * Calls the BackGround Thread and fills the list with data
     */
    void setup(){

        myBackGroundAction = new BackGroundCategory(myRecyclerView,myProgress,getActivity(),BackGroundCategory.CAT_ALL, RecyclerViewHolderCategory.ACTION_ADD,null,myUpdateButton);
        myBackGroundAction.execute();

    }

}
