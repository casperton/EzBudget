package com.cs246.EzBudget;


import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
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
import com.cs246.EzBudget.mFragments.ListBalDataFragment;
import com.cs246.EzBudget.mFragments.ListBalViewFragment;
import com.cs246.EzBudget.mFragments.ListCategoryFragment;
import com.cs246.EzBudget.mFragments.ListRecBalDataFragment;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelper;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelperListener;
import com.cs246.EzBudget.SummaryView.SummaryItem;
import com.cs246.EzBudget.SummaryView.SummaryListAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerItemTouchHelperListener {

    // Turn logging on/off
    public static final boolean DEBUG = true;
    private static final String TAG = MainActivity.class.getName();

    public static final String DATE_PREF = "com.cs246.EzBudget.DATE_PREF";
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May",
                            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public String date_pref;

    private RecyclerView recyclerView;
    private List<SummaryItem> bills;
    private SummaryListAdapter adapter;
    private ConstraintLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Test for shared preferences to store date format
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences.Editor editor = sp.edit();
       // editor.putString("date_pref", "mm/dd/yyyy");
        //editor.apply();

        // Load date format
       // date_pref = sp.getString("date_pref", "");

        if(DEBUG) Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBBalanceView myCurrentView = new DBBalanceView(this);
        DBBalanceData myBalanceData = new DBBalanceData(this);
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

        //get the dates from the current view
        String dateRange2 = DateHandler.getShortName(myBalanceView.getInitialDate())+" - "+
        DateHandler.getShortName(myBalanceView.getFinalDate());

        //String dateRange = MONTHS[monthBegin] + " " + yearBegin + " - " +
          //                 MONTHS[monthEnd] + " " + yearEnd;

        textView.setText(myBalanceView.getTitle() + " - "+dateRange2);


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
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentManager myFagmentManager = getSupportFragmentManager();
                                //Bundle bundle = new Bundle();
                                //Long myMessage = Long.valueOf(-1);
                                //bundle.putLong("id", myMessage );
                                //bundle.putBoolean("isRec", true );
                                ChooseRecBalDataDialogFrag fragInfo = ChooseRecBalDataDialogFrag.newInstance();
                                //fragInfo.setArguments(bundle);
                                FragmentTransaction fragmentTransaction = myFagmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.containerID, fragInfo,"DISPLAY_BAL_DATA_DIALOG_FRAG");
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }).show();
            }
        });*/

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
        Cursor cursor = myBalanceData.getOutcomesCursor(myBalanceView);
        //Cursor cursor = myBalanceData.getAllCursor(myBalanceView);

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);

        // TODO : Replace with actual expense total code
        double total = 0;

        if (cursor.moveToFirst()) {
            do {
                String description = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                String due_date = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                Double amount =  cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                // TODO : Add boolean for marked as paid from database
                boolean paid = false;
                // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen
                    SummaryItem summaryItem = new SummaryItem(description, due_date, amount, paid, BALANCE_ITEM.EXPENSE);
                bills.add(summaryItem);
                // TODO : Replace total with correct values for amount needed during this period
                total += amount;
            } while (cursor.moveToNext());
        }

        // TODO : Add code to load income items - Set SummaryType.Income for income

        // Add a test paycheck
        SummaryItem payItem = new SummaryItem("My First Paycheck of the Month", "07/08/18", 1200, false, BALANCE_ITEM.INCOME);
        bills.add(0, payItem);

        //  SWIPE MENU FOR BILL ITEMS

        recyclerView = findViewById(R.id.recycler_view);
        rootLayout  = findViewById(R.id.rootLayout);
        adapter = new SummaryListAdapter(this, bills);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator((new DefaultItemAnimator()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack
                = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
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

        } else if (id == R.id.nav_send) {

        }

        //REFERENCE AND CLOSE DRAWER LAYOUT
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SummaryListAdapter.MyViewHolder) {
            String name = bills.get(viewHolder.getAdapterPosition()).getName();

            final SummaryItem deletedItem = bills.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            Snackbar snackbar = Snackbar.make(rootLayout, name + " marked as paid!", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("UNDO", new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    adapter.restoreItem(deletedItem, deleteIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
