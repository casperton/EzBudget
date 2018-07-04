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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs246.EzBudget.BALANCE_ITEM;
import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.DateHandler;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelper;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelperListener;
import com.cs246.EzBudget.SummaryView.SummaryItem;
import com.cs246.EzBudget.SummaryView.SummaryListAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static SummaryFragment newInstance() {
        return new SummaryFragment();
    }



    public SummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =inflater.inflate(R.layout.fragment_summary, container, false);


        DBBalanceView myCurrentView = new DBBalanceView(getActivity());
        DBBalanceData myBalanceData = new DBBalanceData(getActivity());
        BalanceView myBalanceView = myCurrentView.getCurrent();


        // Print 3 month range title
        TextView textView = myView.findViewById(R.id.sumTextViewMonthRange);
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

        return myView;
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
