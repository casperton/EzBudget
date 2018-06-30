package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Category;

/**
 * Class to Handle the BalanceView Table of the database
 */
public class DBBalanceView  {

    private static final String TAG = "DB_BALANCE_VIEW";
    private DBHelper myDB;

    public DBBalanceView(Context context) {
        myDB = DBHelper.getInstance(context);

    }


    /**
     *  Insert Test Data in the Database
     * @param db The Database
     * @param theData the Data to Add
     * @return the number of the inserted row or -1 on error
     */
    static public Long insertBalView (SQLiteDatabase db , BalanceView theData) {
        Long status;
        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_INI_DATE, theData.getInitialDateToDatabase());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE, theData.getFinalDateToDatabase());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_KEY_DATE, theData.getKeyDateToDatabase());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_TITLE, theData.getTitle());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION, theData.getDescription());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_IS_CURRENT, theData.isCurrent());
        status = db.insert(BalanceView.BALANCEVIEW_TABLE_NAME, null, contentValues);
        Log.i(DBBalanceView.TAG,"Data Inserted: "+ theData.getTitle());
        return status;
    }


        /**
         * Insert data into the Balance View Database
         * @param theData The BalanceView to be inserted
         * @return the number of the inserted row or -1 if some error ocurred
         */
    public Long insert (BalanceView theData) {
        Long result;

        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_INI_DATE, theData.getInitialDateToDatabase());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION, theData.getDescription());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE, theData.getFinalDateToDatabase());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_KEY_DATE, theData.getKeyDateToDatabase());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_END_BALANCE, theData.getEndBalance());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_TITLE,theData.getTitle());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_IS_CURRENT,theData.isCurrent());

                result = db.insert(BalanceView.BALANCEVIEW_TABLE_NAME, null, contentValues);
                if (result < 0){
                    Log.e(TAG, "Insert forward failed");
                }else
                    db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } finally {
            myDB.myLock.writeLock().unlock();
        }

        myDB.notifyBalanceViewChanged();
        return result;
    }


    /**
     * This method will return the data corresponding to the selected id
     * @param id The id of the Balance View to retrieve its data
     * @return The Cursor wirh the required Data
     */
      public Cursor getDataCursor(Long id) {
        String theQuery = "select * from "+BalanceView.BALANCEVIEW_TABLE_NAME+" where "+BalanceView.BALANCEVIEW_COLUMN_ID+" = "+id+"";

        Cursor res;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            res =  db.rawQuery( theQuery, null );

        } finally {
            myDB.myLock.readLock().unlock();
        }

        return res;
    }
    /**
     * Return the number of Rows of the Balance Data table
     * @return the number of rows in the Balance Data table
     */
    public int getRows(){
        SQLiteDatabase db = myDB.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BalanceView.BALANCEVIEW_TABLE_NAME);
        return numRows;
    }

    public boolean update (Long id, BalanceView theData) {

        boolean retState = false;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_INI_DATE, theData.getInitialDateToDatabase());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION, theData.getDescription());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE,theData.getFinalDateToDatabase());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_KEY_DATE,theData.getKeyDateToDatabase());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_END_BALANCE,theData.getEndBalance());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_TITLE,theData.getTitle());
                contentValues.put(BalanceView.BALANCEVIEW_COLUMN_IS_CURRENT,theData.isCurrent());
                String theWhere = BalanceView.BALANCEVIEW_COLUMN_ID+" = ? ";
                //update returns the number of rows affected
                if (db.update(BalanceView.BALANCEVIEW_TABLE_NAME, contentValues, theWhere, new String[] { Long.toString(id) } ) != 1){
                    Log.e(TAG, "Update Balance View failed");
                }else retState = true;


                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } finally {
            myDB.myLock.writeLock().unlock();
        }

        myDB.notifyBalanceViewChanged();
        return retState;
    }

    /**
     * Delete a Balance View from the Database
     * @param id The id of the Balance View to delete
     * @return true if the delete was a success, false otherwise
     */
    public boolean delete (Long id) {


        boolean result = false;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                String theWhere = BalanceView.BALANCEVIEW_COLUMN_ID+" = ? ";
                /**
                 * the number of rows affected if a whereClause is passed in, 0 otherwise.
                 * To remove all rows and get a count pass "1" as the whereClause.
                 */
                int theResult =  db.delete(BalanceView.BALANCEVIEW_TABLE_NAME,
                        theWhere,
                        new String[] { Long.toString(id) });
                if (theResult == 1) {
                    db.setTransactionSuccessful();
                    result = true;
                }else result =false;
            } finally {
                db.endTransaction();
            }
        } finally {
            myDB.myLock.writeLock().unlock();
        }

        myDB.notifyBalanceViewChanged();
        return result;
    }

    /**
     * Get Current View
     * @return The View in the Cursor format
     */
    public Cursor getCurrentCursor() {
        String theQuery = "select * from "+BalanceView.BALANCEVIEW_TABLE_NAME+" where "+BalanceView.BALANCEVIEW_COLUMN_IS_CURRENT+" = 1";

        Cursor res;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            res =  db.rawQuery( theQuery, null );

        } finally {
            myDB.myLock.readLock().unlock();
        }

        return res;
    }

    //Todo: What if there is no current setted?
    public BalanceView getCurrent(){
        BalanceView theView = new BalanceView();
        Cursor rs =  getCurrentCursor();
        rs.moveToFirst();
        //column values
        Long theID = rs.getLong(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_ID));
        String theName = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_TITLE));
        String theDescription = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION));
        String theInitialDate = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_INI_DATE));
        String theFinalDate = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE));
        String theKeyDate = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_KEY_DATE));

        theView.setID(theID);
        theView.setTitle(theName);
        theView.setDescription(theDescription);
        theView.setInitialDateFromDatabase(theInitialDate);
        theView.setFinalDateFromDatabase(theFinalDate);
        theView.setKeyDateFromDatabse(theKeyDate);
        theView.setCurrent();
        return theView;
    }

    String[] getProjections(){
        String[] Projections = {
                BalanceView.BALANCEVIEW_COLUMN_ID,
                BalanceView.BALANCEVIEW_COLUMN_TITLE,
                BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION,
                BalanceView.BALANCEVIEW_COLUMN_INI_DATE,
                BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE,
                BalanceView.BALANCEVIEW_COLUMN_KEY_DATE,
                BalanceView.BALANCEVIEW_COLUMN_IS_CURRENT
        };
        return Projections;
    }
    /**
     * Return a Cursor with all Balance Views in the database
     * @return All Data in The Database in the Cursor Format
     */
    public Cursor getAllCursor(){
        Cursor cursor;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();

            //return cursor;
            String[] Projections = getProjections();
            cursor = db.query(BalanceView.BALANCEVIEW_TABLE_NAME,Projections,null,null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }
}

