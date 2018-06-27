package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs246.EzBudget.BalanceData;

/**
 * Class to Handle the Table BalanceDataRec (The Recurrent Data) of the Database
 */
public class DBBalanceDataRec{
    
    private DBHelper myDB;
    private final String TAG = "DB_BALANCE_DATA_REC";
    public DBBalanceDataRec(Context context) {
        myDB = DBHelper.getInstance(context);

    }


    /**
     * Insert a register into the Balance Data Recurrent database
     * @param theData
     * @return the number of the row inserted or -1 if some error ocurred
     */
    private Long insert(BalanceData theData) {

        Long result;

        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_CATEGORY, theData.getCategory());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION, theData.getDescription());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE, theData.getDate());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_VALUE, theData.getValue());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_TIMESTAMP, myDB.getNow());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_PERIOD, theData.getStatus());
                ;
                result = db.insert(BalanceData.BALANCEDATAREC_TABLE_NAME, null, contentValues);
                if (result < 0) {
                    Log.e(TAG, "Insert forward failed");
                } else
                    db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } finally {
            myDB.myLock.writeLock().unlock();
        }

        myDB.notifyBalanceDataRecChanged();
        return result;
    }

    /**
     * This method will return the data corresponding to the selected id
     * @param id The id of the Balance Data to retrieve its data
     * @return The Cursor wirh the required Data
     */
    public Cursor getDataCursor(int id) {
        String theQuery = "select * from "+BalanceData.BALANCEDATAREC_TABLE_NAME+" where "+BalanceData.BALANCEDATAREC_COLUMN_ID+" = "+id+"";

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
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BalanceData.BALANCEDATAREC_TABLE_NAME);
        return numRows;
    }

    /**
     * Update Balance Data Recurrent register of the Database
     * @param id The id of the Balance Data Recurrent to change
     * @param theData
     * @return true if the update was a success, false otherwise
     */
    public boolean update (Integer id, BalanceData theData) {


        boolean retState = false;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_VALUE, theData.getValue());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION, theData.getDescription());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE,theData.getDate());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_PERIOD,theData.getRecPeriod());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_CATEGORY,theData.getCategory());
                contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_TIMESTAMP, myDB.getNow());
                String theWhere = BalanceData.BALANCEDATAREC_COLUMN_ID+" = ? ";
                //update returns the number of rows affected
                if (db.update(BalanceData.BALANCEDATAREC_TABLE_NAME, contentValues, theWhere, new String[] { Integer.toString(id) } ) != 1){
                    Log.e(TAG, "Update Balance Data failed");
                }else {
                    retState = true;
                    db.setTransactionSuccessful();
                }
            } finally {
                db.endTransaction();
            }
        } finally {
            myDB.myLock.writeLock().unlock();
        }

        myDB.notifyBalanceDataRecChanged();
        return retState;
    }

    /**
     * Delete a Balance Data Recurrent from the Database
     * @param id The id of the Balance Data to delete
     * @return true if the delete was a success, false otherwise
     */
    public boolean delete (Integer id) {


        boolean result = false;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                String theWhere = BalanceData.BALANCEDATAREC_COLUMN_ID+" = ? ";
                /**
                 * the number of rows affected if a whereClause is passed in, 0 otherwise.
                 * To remove all rows and get a count pass "1" as the whereClause.
                 */
                int theResult =  db.delete(BalanceData.BALANCEDATAREC_TABLE_NAME,
                        theWhere,
                        new String[] { Integer.toString(id) });
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

        myDB.notifyBalanceDataRecChanged();
        return result;
    }

    /////////      END BALANCE DATA METHODS  ///////////////
}