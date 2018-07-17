package com.cs246.EzBudget.mFragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Calculations;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.DateHandler;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelper;
import com.cs246.EzBudget.SummaryView.RecyclerItemTouchHelperListener;
import com.cs246.EzBudget.SummaryView.SummaryItem;
import com.cs246.EzBudget.SummaryView.SummaryListAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

/**
 * This Fragment Holds the Summary View for the Financial Data
 */
public class SummaryFragment extends Fragment
        implements RecyclerItemTouchHelperListener {

    public String date_pref;

    private RecyclerView recyclerView;
    public static List<SummaryItem> bills;
    private List<SummaryItem> summaryItems;
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
        bills = new ArrayList<>();
        summaryItems = new ArrayList<>();
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

        setup();

        //  SWIPE MENU FOR BILL ITEMS
        recyclerView = myView.findViewById(R.id.recycler_view);
        rootLayout  = myView.findViewById(R.id.sumRootLayout);

        adapter = new SummaryListAdapter(getActivity(), summaryItems);

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

    private void setup(){
        if (bills != null) bills.clear();
        if (summaryItems != null) summaryItems.clear();

        BalanceView myBalanceView = myCurrentView.getCurrent();
        String dateRange = "";
        String theTitle = "";


        int monthBegin = DateHandler.getMonthBegin();

        int yearBegin = DateHandler.getYearBegin();

        int monthEnd = DateHandler.getMonthEnd();

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

        //Cursor cursor = myBalanceData.getOutcomesCursor();
        ArrayList<BalanceData> theBalanceDataList = myBalanceData.getOutcomesArray();
        //Cursor cursor = myBalanceData.getAllCursor(myBalanceView);
        //Log.i("SALVADATABASE","CURSOR OUTCOMES SIZE: "+cursor.getCount());
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);


        //Get an object of ListIterator using listIterator() method
        ListIterator listIterator = theBalanceDataList.listIterator();


        while(listIterator.hasNext()) {
            BalanceData theActualData = (BalanceData)listIterator.next();
            Log.i("DBBalanceData", "Outcome ID: "+theActualData.getID());
            Log.i("DBBalanceData", "Status: "+(theActualData.getStatus()==0 ? "Paid" : "Unpaid"));

            // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen
            SummaryItem summaryItem = new SummaryItem(getActivity(),theActualData, OPERATION.DEBIT);
            bills.add(summaryItem);

        }

        // THIS CODE WILL ADD INCOMES FROM THE BalanceDATA table the are in the same period as the current one (mBalanceView)

        Cursor cursorIncomes = myBalanceData.getIncomesCursor();

        ArrayList<BalanceData> theBalanceDataIncList = myBalanceData.getIncomesArray();


        //Get an object of ListIterator using listIterator() method
        listIterator = theBalanceDataIncList.listIterator();


        while(listIterator.hasNext()) {
            BalanceData theActualData = (BalanceData)listIterator.next();

            // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen
            SummaryItem summaryItem = new SummaryItem(getActivity(),theActualData, OPERATION.CREDIT);
            bills.add(summaryItem);

        }

        Collections.sort(bills);
        // Calculations calc  = new Calculations(bills);
        Calculations calc  = new Calculations();
        calc.updateTotals();

        // Add on the items to the list that will show up in summary view
        // summaryItems = new ArrayList<>();

        // The following code segment will be used to filter
        // the summary view so that only items 2 weeks old
        // up to 2 months will be displayed rather than every
        // item in the database
        Date now = new Date();
        Calendar c = Calendar.getInstance();

        // Find date from 2 weeks ago to cutoff view
        // Set calendar to current time
        c.setTime(now);
        // Subtract 14 days from current date
        c.add(Calendar.DATE, -14);
        // Set the value of the date to the variable
        Date twoWeeksAgo = c.getTime();

        // Find the date 2 months from now
        // Set the calendar time to now
        c.setTime(now);
        // Add 2 months
        c.add(Calendar.MONTH, 2);
        // Set the value of the date to the variable
        Date twoMonthsFromNow = c.getTime();

        // Only add items to the summary list that are
        // 2 weeks ago up to 2 months from now for viewing
        for (SummaryItem item: bills) {
            Date itemDate = item.getDate();
            if (itemDate.after(twoWeeksAgo) && itemDate.before(twoMonthsFromNow)) {
                summaryItems.add(item);
            }
        }

        if (adapter != null) adapter.notifyDataSetChanged();
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SummaryListAdapter.MyViewHolder) {
            SummaryItem currentItem = bills.get(viewHolder.getAdapterPosition());
            String name = currentItem.getName();

            final SummaryItem deletedItem = bills.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            /**
             * Duration the snackbar item is displayed is controlled by setting
             * Snackbar.LENGTH_INDEFINITE = Indefinitely displayed
             * Snackbar.LENGTH_LONG = Long period
             * Snackbar.LENGTH_SHORT = Short period
             */
            Snackbar snackbar;
            if (currentItem.isPaid()) {
                snackbar = Snackbar.make(rootLayout, name + " marked as paid.", Snackbar.LENGTH_LONG);
            } else {
                snackbar = Snackbar.make(rootLayout, name + " marked as unpaid.", Snackbar.LENGTH_LONG);
            }
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
