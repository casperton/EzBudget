package com.cs246.EzBudget;


import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.mFragments.ChooseRecBalDataDialogFrag;
import com.cs246.EzBudget.mFragments.DispCategoryFragment;
import com.cs246.EzBudget.mFragments.ListBalDataFragment;
import com.cs246.EzBudget.mFragments.ListBalViewFragment;
import com.cs246.EzBudget.mFragments.ListCategoryFragment;
import com.cs246.EzBudget.mFragments.ListRecBalDataFragment;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelper;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelperListener;
import com.cs246.EzBudget.SummaryView.SummaryItem;
import com.cs246.EzBudget.SummaryView.SummaryListAdapter;
import com.cs246.EzBudget.mFragments.SelectViewFragment;
import com.cs246.EzBudget.mFragments.SummaryFragment;
import com.cs246.EzBudget.mRecycler.CommonCategory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TabLayout myTabLayout;
    private ViewPager myViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Test for shared preferences to store date format
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences.Editor editor = sp.edit();
       // editor.putString("date_pref", "mm/dd/yyyy");
        //editor.apply();

        // Load date format
       // date_pref = sp.getString("date_pref", "");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        myViewPager = (ViewPager) findViewById(R.id.viewPager);

        final MainViewPagerAdapter viewAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        // Adding Fragments
        viewAdapter.addFragment(new SelectViewFragment(), "Select View");
        viewAdapter.addFragment(new SummaryFragment(),"Summary");
        //Adapter Setup
        myViewPager.setAdapter(viewAdapter);
        myTabLayout.setupWithViewPager(myViewPager);


        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * Update the Summary fragment whenever it is selected
             * @param position
             */
            @Override
            public void onPageSelected(int position) {

                Fragment myFrag = viewAdapter.getItem(position);
                myFrag.onResume();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager myFagmentManager = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                //Long myMessage = Long.valueOf(-1);
                //bundle.putLong("id", myMessage );
                //bundle.putBoolean("isRec", true );
                ChooseRecBalDataDialogFrag fragInfo = ChooseRecBalDataDialogFrag.newInstance();
                //fragInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, fragInfo,"LIST_BAL_DATA_DIALOG");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        //REFERENCE DRAWER,TOGGLE ITS INDICATOR
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //REFERENCE NAV VIEW AND ATTACH ITS ITEM SELECTION LISTENER
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    //CLOSE DRAWER WHEN BACK BTN IS CLICKED,IF OPEN
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //RAISED WHEN NAV VIEW ITEM IS SELECTED
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //OPEN APPROPRIATE FRAGMENT WHEN NAV ITEM IS SELECTED
        if (id == R.id.drawerListCat) {
            //PERFORM TRANSACTION TO REPLACE CONTAINER WITH FRAGMENT
            //MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterPlanetary.newInstance()).commit();


            FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerID, ListCategoryFragment.newInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
                //Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                //intent.putExtras(dataBundle);

               // startActivity(intent);
            // if you return true the menu do not close
                //return true;

        } else if (id == R.id.drawerListBalData) {
            //MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterStellar.newInstance()).commit();
            //Intent intent2 = new Intent(getApplicationContext(),DispBalData.class);
            //intent2.putExtras(dataBundle);

            //startActivity(intent2);
            FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // fragmentTransaction.replace(R.id.containerID, DispBalViewFragment.newInstance());
            fragmentTransaction.replace(R.id.containerID, ListBalDataFragment.newInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.drawerListRecurrent) {
            //startActivity(intent2);
            FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // fragmentTransaction.replace(R.id.containerID, DispBalViewFragment.newInstance());
            fragmentTransaction.replace(R.id.containerID, ListRecBalDataFragment.newInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.drawerListBalView) {
            FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // fragmentTransaction.replace(R.id.containerID, DispBalViewFragment.newInstance());
            fragmentTransaction.replace(R.id.containerID, ListBalViewFragment.newInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            //MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, BalViewEditFragment.newInstance()).commit();
            //Intent intent4 = new Intent(getApplicationContext(),DispBalView.class);
            //intent2.putExtras(dataBundle);

            //startActivity(intent4);
        } else if (id == R.id.nav_share) {
            Long id_To_Search = Long.valueOf(-1);
            Bundle bundle = new Bundle();
            bundle.putLong("id", id_To_Search );
            DispCategoryFragment fragInfo = DispCategoryFragment.newInstance();
            fragInfo.setArguments(bundle);
            FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerID, fragInfo,"CATEGORY_SHOW_FRAGMENT");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_send) {

        }

        //REFERENCE AND CLOSE DRAWER LAYOUT
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
