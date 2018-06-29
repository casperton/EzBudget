package com.cs246.EzBudget;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
// Swipe menu libraries
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.mFragments.DispBalViewFragment;
import com.cs246.EzBudget.mFragments.ListBalDataFragment;
import com.cs246.EzBudget.mFragments.ListBalViewFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String DATE_PREF = "com.cs246.EzBudget.DATE_PREF";
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May",
                            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public String date_pref;
    private List<String> bills; // Temporary for testing the list view - Replace later with actual object for bills/income items
    private ArrayAdapter<String> arrayAdapter;
    //ListView listView;
    SwipeMenuListView listView;

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
        DBBalanceView myCurrentView = new DBBalanceView(this);
        BalanceView myBalanceView = myCurrentView.getCurrent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Print 3 month range title
        TextView textView = findViewById(R.id.textViewMonthRange);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthBegin = calendar.get(Calendar.MONTH);
        int yearBegin = calendar.get(Calendar.YEAR);
        int monthEnd = (monthBegin + 2);
        int yearEnd = yearBegin;
        if (monthEnd > 11) {
            monthEnd = (monthEnd - 12);
            yearEnd++;
        }

        String dateRange = MONTHS[monthBegin] + " " + yearBegin + " - " +
                           MONTHS[monthEnd] + " " + yearEnd;

        textView.setText(myBalanceView.getTitle() + " - "+dateRange);


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

        //MESSAGE TO KIRK
        // TO GET BILLS CURSOR USE THE METHOD getOutcomesCursor()
        // FROM THE CLASS DBBalanceData
        //SQLiteDatabase db = mydb.getReadableDatabase();
        // DBBalanceData theDatabase = new DBBalanceData(context)
        // cursor theCursor = theDatabase.getOutcomesCursor(db)
        //
        //to get incomes use getIncomesCursor(db)
        // (Salvatore)


        //  SWIPE MENU FOR BILL ITEMS
        listView = findViewById(R.id.listview_summary);

        bills = new ArrayList<>();
        bills.add("Phone bill: $65");
        bills.add("Power bill: $75");
        bills.add("Car payment: $330");
        bills.add("Rent: $850");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bills);

        listView.setAdapter(arrayAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "done" item
                SwipeMenuItem paidItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                paidItem.setBackground(new ColorDrawable(Color.rgb(0x2e,
                        0x75, 0x25)));
                // set item width
                paidItem.setWidth(180);
                paidItem.setTitle("Paid");
                paidItem.setTitleSize(18);
                paidItem.setTitleColor(Color.WHITE);
                // set a icon
                //paidItem.setIcon(R.drawable.ic_done);
                // add to menu
                menu.addMenuItem(paidItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // Mark item as paid
                        bills.remove(index);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        // set SwipeListener
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        listView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

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
            //MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterPlanetary.newInstance()).commit();

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;

        } else if (id == R.id.interstellar) {
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

        } else if (id == R.id.intergalactic) {
            //MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterGalactic.newInstance()).commit();
            Intent intent3 = new Intent(getApplicationContext(),BalanceViewTest.class);
            //intent2.putExtras(dataBundle);

            startActivity(intent3);
        } else if (id == R.id.interuniverse) {
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

        } else if (id == R.id.nav_send) {

        }

        //REFERENCE AND CLOSE DRAWER LAYOUT
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
