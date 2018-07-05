package com.cs246.EzBudget;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentListTtitles = new ArrayList<>();


    private Map<Integer,String> myFragmentTAGS;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragmentTAGS = new HashMap<Integer,String>();
    }

    @Override
    public Fragment getItem(int position) {
        return  fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentListTtitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTtitles.get(position);
    }

    public void addFragment(Fragment theFrag, String theTitle){
        fragmentList.add(theFrag);
        FragmentListTtitles.add(theTitle);

    }




}
