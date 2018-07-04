package com.cs246.EzBudget.mFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs246.EzBudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectViewFragment extends Fragment {


    public SelectViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_view, container, false);
    }

}
