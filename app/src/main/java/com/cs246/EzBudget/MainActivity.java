package com.cs246.EzBudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.cs246.EzBudget.mFragments.InterGalactic;
import com.cs246.EzBudget.mFragments.InterPlanetary;
import com.cs246.EzBudget.mFragments.InterStellar;
import com.cs246.EzBudget.mFragments.InterUniverse;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String DATE_PREF = "com.cs246.EzBudget.DATE_PREF";
    public String date_pref;
    private List<String> bills; // Temporary for testing the list view - Replace later with actual object for bills/income items
    private ArrayAdapter<String> arrayAdapter;
    ListView listView;


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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        bills = new ArrayList<>();
        bills.add("Phone bill: $65");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, bills);

        listView = findViewById(R.id.listview_summary);
        listView.setAdapter(arrayAdapter);

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
        if (id == R.id.interplanetary) {
            //PERFORM TRANSACTION TO REPLACE CONTAINER WITH FRAGMENT
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterPlanetary.newInstance()).commit();

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;

        } else if (id == R.id.interstellar) {
            //MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterStellar.newInstance()).commit();
            Intent intent2 = new Intent(getApplicationContext(),DispBalData.class);
            //intent2.putExtras(dataBundle);

            startActivity(intent2);

        } else if (id == R.id.intergalactic) {
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterGalactic.newInstance()).commit();

        } else if (id == R.id.interuniverse) {
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterUniverse.newInstance()).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //REFERENCE AND CLOSE DRAWER LAYOUT
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
