package com.cs246.EzBudget.mFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cs246.EzBudget.R;
import com.cs246.EzBudget.mBackGrounds.BackGroundCategory;
import com.cs246.EzBudget.mRecycler.RecyclerViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCategoryFragment extends Fragment {

    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private ProgressBar myProgress=null;
    private View myView;

    @NonNull
    static public ListCategoryFragment newInstance(){
        ListCategoryFragment theFrag = new ListCategoryFragment();

        return theFrag;
    }

    public ListCategoryFragment() {
        // Required empty public constructor
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

        new BackGroundCategory(myRecyclerView,myProgress,getActivity(),BackGroundCategory.CAT_ALL, RecyclerViewHolder.LAYOUT_ONE,null).execute();


        return myView;
    }

}
