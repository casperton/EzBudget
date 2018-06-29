package com.cs246.EzBudget.mBackGrounds;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.mRecycler.RecyclerBalDataAdapter;
import com.cs246.EzBudget.mRecycler.RecyclerBalanceAdapter;

import java.util.ArrayList;



public class BackGroundBalData extends AsyncTask<Void,BalanceData,Void> {

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



    public BackGroundBalData(RecyclerView theView, ProgressBar theBar, Context context,  FragmentManager theFrag, int theConsult ) {
        this.context = context;
        this.mylistView = theView;
        this.myProgressBar = theBar;
        myBalanceData= new ArrayList<>();
        myFagmentManager = theFrag;
        myConsultType = theConsult;
    }

    @Override
    protected void onPreExecute() {
        myAdapter = new RecyclerBalanceAdapter(myBalanceData,context,myFagmentManager);
        mylistView.setAdapter(myAdapter);
        myProgressBar.setVisibility(View.VISIBLE);


    }

    @Override
    protected Void doInBackground(Void... voids) {
        DBBalanceData theBalanceData= new DBBalanceData(context);

        Long theID;
        Double theValue;
        String theDescription;
        String theDueDate;
        Integer theStatus;
        Long catID;
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
                Log.i("CURSOR_BAL_DATA_INDEX", "col: " +i.toString()+" : " + data);
            }
            theID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_ID));
            theValue = cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
            theDescription = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
            theDueDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
            catID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
            theStatus = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));
            BalanceData theData = new BalanceData();
            theData.setID(theID);
            theData.setValue(theValue);
            theData.setDescription(theDescription);
            theData.setCategory(catID);
            theData.setDate(theDueDate);
            theData.setStatus(theStatus);
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