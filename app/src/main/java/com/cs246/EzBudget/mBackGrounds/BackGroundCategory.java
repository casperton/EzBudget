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

import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.Database.DBCategory;
import com.cs246.EzBudget.mRecycler.RecyclerCategoryAdapter;

import java.util.ArrayList;

/**
 * Class to load the table category from the database to the RecyclerView using asyncTask
 *
 */
public class BackGroundCategory extends AsyncTask<Void,Category,Void>{

    private RecyclerView mylistView;
    private ProgressBar myProgressBar;
    private Context context;
    private RecyclerCategoryAdapter myAdapter;
    private ArrayList<Category> myCategories;
    //private ChooseCategoryDialogFrag teste;
    private FragmentManager myFragmentManager;
    public final static int CAT_ALL = 1;
    public final static int CAT_INC = 2;
    public final static int CAT_OUT = 3;
    public final static int CAT_INFO = 4;

    private int myConsultType;
    private int myLayOut;
    private Button myUpdateButton;

    /**
     * Constructor
     * @param theView  The RecyclerView Object
     * @param theBar   The Progress Bar Object
     * @param context the Context
     * @param theConsult   The consult type
     * @param theLayOut  The LayOut of the Dialog
     * @param theFrag  The Fragment Manager
     * @param theUpdateButton  The UpdateButton Object
     */
    public BackGroundCategory(RecyclerView theView, ProgressBar theBar, Context context, int theConsult, int theLayOut, FragmentManager theFrag, Button theUpdateButton) {
        this.context = context;
        this.mylistView = theView;
        this.myProgressBar = theBar;
        myConsultType = theConsult;
        myCategories= new ArrayList<>();
        myLayOut = theLayOut;
        myFragmentManager = theFrag;
        myUpdateButton = theUpdateButton;
    }

    @Override
    protected void onPreExecute() {
        myAdapter = new RecyclerCategoryAdapter(myCategories,context,myLayOut,myFragmentManager, myUpdateButton);
        mylistView.setAdapter(myAdapter);
        myProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        DBCategory mydb = new DBCategory(context);

        Long id;
        String name;
        String description;
        Integer operation;
        Cursor cursor;
        switch (myConsultType) {
            case CAT_ALL:  cursor =  mydb.getAllCursor();
                break;
            case  CAT_INC:  cursor =  mydb.getIncomesCursor();
                break;
            case CAT_OUT: cursor =  mydb.getOutcomesCursor();
                break;
            case CAT_INFO:  cursor =  mydb.getInformativesCursor();
                break;

            default:cursor =  mydb.getAllCursor();
                break;
        }



        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            int size = cursor.getCount();
            //Log.i("SALVATORE: SIZE: ", Integer.toString(size));
            do {
                id = cursor.getLong(cursor.getColumnIndex(Category.CATEGORY_COLUMN_ID));
                name = cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_NAME));
                description = cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_DESCRIPTION));
                operation = cursor.getInt(cursor.getColumnIndex(Category.CATEGORY_COLUMN_OPERATION));
                Category theCat = new Category();
                theCat.setID(id);
                theCat.setName(name);
                theCat.setDescription(description);
                theCat.setOperation(operation);
                publishProgress(theCat);
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
    protected void onProgressUpdate(Category... values) {

        myCategories.add(values[0]);
        myAdapter.notifyDataSetChanged();
    }
}
