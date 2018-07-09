package com.cs246.EzBudget.mBackGrounds;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.Database.DBBalanceDataRec;
import com.cs246.EzBudget.mRecycler.LIST_ACTION;
import com.cs246.EzBudget.mRecycler.RecyclerBalanceAdapter;

import java.util.ArrayList;


public class BackGroundRecData extends AsyncTask<Void,BalanceData,Void> {

    private RecyclerView mylistView;
    private ProgressBar myProgressBar;
    private Context context;
    private RecyclerBalanceAdapter myAdapter;
    private ArrayList<BalanceData> myBalanceData;
    public final static int BAL_ALL = 1;
    public final static int BAL_BILLS = 2;
    public final static int BAL_INCOMES = 3;


    private FragmentManager myFagmentManager;
    private int myConsultType;

    private int myAction;
    private Button myUpgradeButton;

    /**
     *
     * @param theView the view
     * @param theBar  the progress bar
     * @param context  the context
     * @param theFrag  the fragment manager
     * @param theConsult  BAL_ALL (everything) , BAL_INC (incomes) , BAL_BILLS (OUTCOMES)
     * @param theAction  ACT_LIST_CHOOSE to only choose a item, or ADD to be able to add and update itens
     * @param theUpgradeButton The Update Button
     */
    public BackGroundRecData(RecyclerView theView, ProgressBar theBar, Context context,  FragmentManager theFrag, int theConsult, int theAction, Button theUpgradeButton ) {
        this.context = context;
        this.mylistView = theView;
        this.myProgressBar = theBar;
        myBalanceData= new ArrayList<>();
        myFagmentManager = theFrag;
        myConsultType = theConsult;
        myAction = theAction;
        myUpgradeButton = theUpgradeButton;
    }

    @Override
    protected void onPreExecute() {
        myAdapter = new RecyclerBalanceAdapter(myBalanceData,context,myFagmentManager,true, myAction, myUpgradeButton);
        mylistView.setAdapter(myAdapter);
        myProgressBar.setVisibility(View.VISIBLE);


    }

    @Override
    protected Void doInBackground(Void... voids) {
        DBBalanceDataRec theBalanceData= new DBBalanceDataRec(context);

        Long theID;
        Double theValue;
        String theDescription;
        String theDueDate;
        Long catID;
        Integer thePeriod;
        Cursor cursor;
        switch (myConsultType) {
            case BAL_ALL:  cursor =  theBalanceData.getAllCursor();
                break;
            case  BAL_INCOMES:  cursor =  theBalanceData.getIncomesCursor();
                break;
            case BAL_BILLS: cursor =  theBalanceData.getOutcomesCursor();
                break;


            default:cursor =  theBalanceData.getAllCursor();
                break;
        }



        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            Integer size = cursor.getCount();
            Integer count = cursor.getColumnCount();
            //String index = cursor.getString(cursor.getColumnIndex("value"));
            // Log.i("CURSOR_BAL_DATA_INDEX", index);
            Log.i("CURSOR_BAL_DATA_INDEX_COUNT", count.toString());
            for (Integer i =0 ; i< count; i++)
            {
                String data = cursor.getString(i);
               // Log.i("CURSOR_BAL_DATA_INDEX", "col: " +i.toString()+" : " + data);
            }
            theID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_ID));
            theValue = cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
            theDescription = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION));
            theDueDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE));
            catID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_CATEGORY));
            thePeriod = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_PERIOD));
            BalanceData theData = new BalanceData();
            theData.setID(theID);
            theData.setValue(theValue);
            theData.setDescription(theDescription);
            theData.setCategory(catID);
            theData.setDueDateFromDatabase(theDueDate);
            theData.setRecurrent(thePeriod);
            publishProgress(theData);


        }
        cursor.close();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        myProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(BalanceData... values) {

        this.myBalanceData.add(values[0]);
        myAdapter.notifyDataSetChanged();
    }
}