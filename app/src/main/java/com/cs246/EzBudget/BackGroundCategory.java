package com.cs246.EzBudget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cs246.EzBudget.Database.DBCategory;
import com.cs246.EzBudget.mRecycler.RecyclerCategoryAdapter;

import java.util.ArrayList;

/**
 * Class to load the categories using asyncTask
 *
 */
public class BackGroundCategory extends AsyncTask<Void,Category,Void>{

    private RecyclerView mylistView;
    private ProgressBar myProgressBar;
    private Context context;
    private RecyclerCategoryAdapter myAdapter;
    private ArrayList<Category> myCategories;
    private CategoryShowFragment teste;
    public final static int CAT_ALL = 1;
    public final static int CAT_INC = 2;
    public final static int CAT_OUT = 3;
    public final static int CAT_INFO = 4;

    private int myConsultType;
    private int myLayOut;

    public BackGroundCategory(RecyclerView theView, ProgressBar theBar, Context context, int theConsult, int theLayOut, CategoryShowFragment theFrag) {
        this.context = context;
        this.mylistView = theView;
        this.myProgressBar = theBar;
        myConsultType = theConsult;
        myCategories= new ArrayList<>();
        myLayOut = theLayOut;
        teste = theFrag;
    }

    @Override
    protected void onPreExecute() {
        myAdapter = new RecyclerCategoryAdapter(myCategories,context,myLayOut,teste);
        mylistView.setAdapter(myAdapter);
        myProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        DBCategory mydb = new DBCategory(context);
        SQLiteDatabase db = mydb.getReadableDatabase();

        Integer id;
        String name;
        String description;
        Integer operation;
        Cursor cursor;
        switch (myConsultType) {
            case CAT_ALL:  cursor =  mydb.getAllCursor(db);
                break;
            case  CAT_INC:  cursor =  mydb.getIncomesCursor(db);
                break;
            case CAT_OUT: cursor =  mydb.getOutcomesCursor(db);
                break;
            case CAT_INFO:  cursor =  mydb.getInformativesCursor(db);
                break;

            default:cursor =  mydb.getAllCursor(db);
                break;
        }



        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            int size = cursor.getCount();
            Log.i("SALVATORE: SIZE: ", Integer.toString(size));
            do {
                id = cursor.getInt(cursor.getColumnIndex(Category.CATEGORY_COLUMN_ID));
                name = cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_NAME));
                description = cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_DESCRIPTION));
                operation = cursor.getInt(cursor.getColumnIndex(Category.CATEGORY_COLUMN_OPERATION));
                Category theCat = new Category();
                theCat.setID(id);
                theCat.setName(name);
                theCat.setDescription(description);
                theCat.setOperation(operation);
                publishProgress(theCat);
                Log.i("SALVATORE", "Added new register");
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
    protected void onProgressUpdate(Category... values) {

        myCategories.add(values[0]);
        myAdapter.notifyDataSetChanged();
    }
}
