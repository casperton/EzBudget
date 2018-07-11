package com.cs246.EzBudget.mFragments;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cs246.EzBudget.BALANCE_ITEM;
import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Calculations;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.DateHandler;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelper;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelperListener;
import com.cs246.EzBudget.SummaryView.SummaryItem;
import com.cs246.EzBudget.SummaryView.SummaryListAdapter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment
        implements RecyclerItemTouchHelperListener {

    public String date_pref;

    private RecyclerView recyclerView;
    private List<SummaryItem> bills;
    private SummaryListAdapter adapter;
    private ConstraintLayout rootLayout;
    private View myView;
    private DBBalanceView myCurrentView;
    private DBBalanceData myBalanceData;
    private TextView myTextView;

    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May",
            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static SummaryFragment newInstance() {
        return new SummaryFragment();
    }



    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {

        setup();

        super.onResume();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =inflater.inflate(R.layout.fragment_summary, container, false);


        myCurrentView = new DBBalanceView(getActivity());
        myBalanceData = new DBBalanceData(getActivity());


        // Print 3 month range title
        myTextView = myView.findViewById(R.id.sumTextViewMonthRange);



        bills = new ArrayList<>();

        //  SWIPE MENU FOR BILL ITEMS

        recyclerView = myView.findViewById(R.id.recycler_view);
        rootLayout  = myView.findViewById(R.id.sumRootLayout);
        adapter = new SummaryListAdapter(getActivity(), bills);

        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator((new DefaultItemAnimator()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack
                = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);

        setup();
        return myView;
    }

    private void setup(){
        if (bills != null) bills.clear();
        BalanceView myBalanceView = myCurrentView.getCurrent();
        String dateRange = "";
        String theTitle = "";

        //  Kirk
        // I moved this function to the DataHandler so I can reuse it in the Database in order to calculate the recurrenciveness.
        // PLEASE, IF YOU NEED TO CHAGE IT> CHANGE IT THERE. SO the effect will be the same in the database calculations
        //Salvatore
        //Date date = new Date();
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        //int monthBegin = calendar.get(Calendar.MONTH);
        int monthBegin = DateHandler.getMonthBegin();
        // int yearBegin = calendar.get(Calendar.YEAR);
        int yearBegin = DateHandler.getYearBegin();
        // int monthEnd = (monthBegin + 2);
        int monthEnd = DateHandler.getMonthEnd();
        //int yearEnd = yearBegin;
        //if (monthEnd > 11) {
        //    monthEnd = (monthEnd - 12);
        //    yearEnd++;
        //}
        int yearEnd =  DateHandler.getYearEnd();
        /*
        //todo: what to do when there is no cuurent
        if (myBalanceView == null){
            //if there are any data in table set the first as current
            //otherwise insert the current month and set it as current

        }else{
            //get the dates from the current view
            theTitle = myBalanceView.getTitle();
            dateRange = DateHandler.getShortName(myBalanceView.getInitialDate())+" - "+
                    DateHandler.getShortName(myBalanceView.getFinalDate());
        }
        */
        //myTextView.setText(theTitle + " - " + dateRange);

        dateRange = MONTHS[monthBegin] + " " + yearBegin + " - " +
                MONTHS[monthEnd] + " " + yearEnd;
        //String testDate = "This is a test date range.";
        myTextView.setText(dateRange);

        Cursor cursor = myBalanceData.getOutcomesCursor();
        //Cursor cursor = myBalanceData.getAllCursor(myBalanceView);
        //Log.i("SALVADATABASE","CURSOR OUTCOMES SIZE: "+cursor.getCount());
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);

        if (cursor !=null) {
            if (cursor.moveToFirst()) {
                do {
                    String description = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                    String due_date = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                    Double amount = cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                    Long theCat = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
                    //Thestatus can be:
                    //UNKNOWN = -1;
                    //PAID_RECEIVED = 0;
                    //UNPAID_UNRECEIVED = 1;
                    Integer Status = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));
                    boolean paid=false;
                    Integer theStatus = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));
                    if (theStatus== PAY_STATUS.UNKNOWN) paid = false;
                    if (theStatus== PAY_STATUS.UNPAID_UNRECEIVED) paid = false;
                    if (theStatus== PAY_STATUS.PAID_RECEIVED) paid = true;
                    // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen
                    SummaryItem summaryItem = new SummaryItem(description, due_date, amount, paid, OPERATION.DEBIT);
                    bills.add(summaryItem);
                    //Log.i("SALVADATABASE","ADDED OUTCOMES: "+description);

                    // TODO : Replace total with correct values for amount needed during this period
                  } while (cursor.moveToNext());
            }
        }
        // THIS CODE WILL ADD INCOMES FROM THE BalanceDATA table the are in the same period as the current one (mBalanceView)

        Cursor cursorIncomes = myBalanceData.getIncomesCursor();
        //Log.i("SALVADATABASE","CURSOR INCOMES SIZE: "+cursorIncomes.getCount());
        if (cursorIncomes !=null) {
            if (cursorIncomes.moveToFirst()) {
                do {
                    String description = cursorIncomes.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                    String due_date = cursorIncomes.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                    Double amount = cursorIncomes.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                    Long theCat = cursorIncomes.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
                    //Thestatus can be:
                    //UNKNOWN = -1;
                    //PAID_RECEIVED = 0;
                    //UNPAID_UNRECEIVED = 1;
                    Integer Status = cursorIncomes.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));
                    boolean paid=false;
                    Integer theStatus = cursorIncomes.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));
                    if (theStatus== PAY_STATUS.UNKNOWN) paid = false;
                    if (theStatus== PAY_STATUS.UNPAID_UNRECEIVED) paid = false;
                    if (theStatus== PAY_STATUS.PAID_RECEIVED) paid = true;

                    // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen
                    SummaryItem payItem = new SummaryItem(description, due_date, amount, paid, OPERATION.CREDIT);
                    //

                    bills.add(payItem);
                    //Log.i("SALVADATABASE","ADDED INCOMES: "+description);
                    // TODO : Replace total with correct values for amount needed during this period
                } while (cursorIncomes.moveToNext());
            }
        }


        // Add a test paycheck
        //SummaryItem payItem = new SummaryItem("My First Paycheck", "2018-06-01", 1200, false, OPERATION.CREDIT);
        //bills.add(payItem);
        //payItem = new SummaryItem("My Second Paycheck", "2018-06-15", 1200, false, OPERATION.CREDIT);
        //bills.add(payItem);

        Collections.sort(bills);
        Calculations calc  = new Calculations(bills);
        calc.updateTotals();

        adapter.notifyDataSetChanged();

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
