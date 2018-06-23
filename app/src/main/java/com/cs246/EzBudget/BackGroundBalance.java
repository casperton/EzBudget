package com.cs246.EzBudget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.mRecycler.RecyclerBalanceAdapter;

import java.util.ArrayList;

public class BackGroundBalance extends AsyncTask<Void,BalanceData,Void> {

    private RecyclerView mylistView;
    private ProgressBar myProgressBar;
    private Context context;
    private RecyclerBalanceAdapter myAdapter;
    private ArrayList<BalanceData> myBalanceData;
    //private CategoryShowFragment teste;
    public final static int BAL_ALL = 1;
    public final static int BAL_BILLS = 2;
    public final static int BAL_INCOMES = 3;


    private int myConsultType;
    private int myLayOut;

    public BackGroundBalance(RecyclerView theView, ProgressBar theBar, Context context, int theConsult, int theLayOut) {
        this.context = context;
        this.mylistView = theView;
        this.myProgressBar = theBar;
        myConsultType = theConsult;
        myBalanceData= new ArrayList<>();
        myLayOut = theLayOut;

    }

    @Override
    protected void onPreExecute() {
        myAdapter = new RecyclerBalanceAdapter(myBalanceData,context,myLayOut);
        mylistView.setAdapter(myAdapter);
        myProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        DBBalanceData theBalanceData= new DBBalanceData(context);
        SQLiteDatabase db = theBalanceData.getReadableDatabase();

        Integer theID;
        double theValue;
        String theDescription;
        String theDueDate;
        Integer catID;
        Cursor cursor;
        switch (myConsultType) {
            case BAL_ALL:  cursor =  theBalanceData.getAllCursor(db);
                break;
            case  BAL_INCOMES:  cursor =  theBalanceData.getIncomesCursor(db);
                break;
            case BAL_BILLS: cursor =  theBalanceData.getOutcomesCursor(db);
                break;


            default:cursor =  theBalanceData.getAllCursor(db);
                break;
        }



        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            int size = cursor.getCount();
            do {
                theID = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_ID));
                //theValue = cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                theDescription = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                theDueDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                catID = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
                BalanceData theData = new BalanceData();
                theData.setID(theID);
                theData.setValue(22.34);
                theData.setDescription(theDescription);
                theData.setCategory(catID);
                theData.setDate(theDueDate);
                publishProgress(theData);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
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
