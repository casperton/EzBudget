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

/**
 * Adapter used in the ViewPager of the main Window
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter{

    /**
     * List of Fragments used with this adapter
     */
    private final List<Fragment> fragmentList = new ArrayList<>();
    /**
     * List of titles used in the Tab
     */
    private final List<String> FragmentListTtitles = new ArrayList<>();

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);

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
