package com.cs246.EzBudget.mBackGrounds;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.mRecycler.RecyclerBalViewAdapter;

import java.util.ArrayList;

public class BackGroundBalView extends AsyncTask<Void,BalanceView,Void> {

    private RecyclerView mylistView;
    private ProgressBar myProgressBar;
    private Context context;
    private RecyclerBalViewAdapter myAdapter;
    private ArrayList<BalanceView> myCategories;
    private FragmentManager myFagmentManager;



    public BackGroundBalView(RecyclerView theView, ProgressBar theBar, Context context,  FragmentManager theFrag/*, int theConsult */) {
        this.context = context;
        this.mylistView = theView;
        this.myProgressBar = theBar;
        myCategories= new ArrayList<>();
        myFagmentManager = theFrag;
    }

    @Override
    protected void onPreExecute() {
        myAdapter = new RecyclerBalViewAdapter(myCategories,context,myFagmentManager);
        mylistView.setAdapter(myAdapter);
        myProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        DBBalanceView mydb = new DBBalanceView(context);

        Integer id;
        String name;
        String description;
        Integer operation;
        Cursor cursor;

        cursor =  mydb.getAllCursor();




        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            int size = cursor.getCount();
           // Log.i("SALVATORE: SIZE: ", Integer.toString(size));
            do {
                Long theID = cursor.getLong(cursor.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_ID));
                String theName = cursor.getString(cursor.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_TITLE));
                String theDescription = cursor.getString(cursor.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION));
                String theInitialDate = cursor.getString(cursor.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_INI_DATE));
                String theFinalDate = cursor.getString(cursor.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE));
                String theKeyDate = cursor.getString(cursor.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_KEY_DATE));
                Integer theCurrent = cursor.getInt(cursor.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_IS_CURRENT));
                BalanceView theView = new BalanceView();
                theView.setID(theID);
                theView.setTitle(theName);
                theView.setDescription(theDescription);
                theView.setInitialDateFromDatabase(theInitialDate);
                theView.setFinalDateFromDatabase(theFinalDate);
                theView.setKeyDateFromDatabse(theKeyDate);
                publishProgress(theView);
                if (theCurrent == 1) theView.setCurrent();
                else theView.resetCurrent();
                //Log.i("SALVATORE", "Added new register");
            } while (cursor.moveToNext());
        }
        cursor.close();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        myProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(BalanceView... values) {

        myCategories.add(values[0]);
        myAdapter.notifyDataSetChanged();
    }
}