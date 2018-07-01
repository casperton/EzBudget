package com.cs246.EzBudget;


import android.content.ClipData;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.view.animation.BounceInterpolator;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.mFragments.ChooseRecBalDataDialogFrag;
import com.cs246.EzBudget.mFragments.ListBalDataFragment;
import com.cs246.EzBudget.mFragments.ListBalViewFragment;
import com.cs246.EzBudget.mFragments.ListCategoryFragment;
import com.cs246.EzBudget.mFragments.ListRecBalDataFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerItemTouchHelperListener {

    public static final String DATE_PREF = "com.cs246.EzBudget.DATE_PREF";
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May",
                            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public String date_pref;

    private RecyclerView recyclerView;
    private List<BillItem> bills;
    private BillListAdapter adapter;
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



        String dateRange = MONTHS[monthBegin] + " " + yearBegin + " - " +
                           MONTHS[monthEnd] + " " + yearEnd;

        textView.setText(myBalanceView.getTitle() + " - "+dateRange);


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
                fragmentTransaction.replace(R.id.containerDialog, fragInfo,"LIST_BAL_DATA_DIALOG");
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

        if (cursor.moveToFirst()) {
            do {
                String description = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                Double amount =  cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                // TODO : Add boolean for marked as paid from database
                boolean paid = false;
                BillItem billItem = new BillItem(description, amount, paid);
                bills.add(billItem);
            } while (cursor.moveToNext());
        }

//
//        //  SWIPE MENU FOR BILL ITEMS

        recyclerView = findViewById(R.id.recycler_view);
        rootLayout  = findViewById(R.id.rootLayout);
        adapter = new BillListAdapter(this, bills);

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
        if (viewHolder instanceof BillListAdapter.MyViewHolder) {
            String name = bills.get(viewHolder.getAdapterPosition()).getName();

            final BillItem deletedItem = bills.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            Snackbar snackbar = Snackbar.make(rootLayout, name + " marked as paid!", Snackbar.LENGTH_LONG);
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
