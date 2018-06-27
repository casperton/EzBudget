package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.OPERATION;

/**
 * Class to Handle the Table BalanceData of the Database
 */
public class DBBalanceData {

    private final String TAG = "DB_BALANCE_DATA";
    private DBHelper myDB;
    public DBBalanceData(Context context) {
        myDB = DBHelper.getInstance(context);

    }

    /**
     *  Insert a Register of Balance Data into the Database
     * @param theData
     * @return the number of the row inserted or -1 if failed
     */
    public Long insert (BalanceData theData) {
        Long result;

        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_CATEGORY, theData.getCategory());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION, theData.getDescription());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_DUE_DATE, theData.getDate());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE, theData.getPaymentDate());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_VALUE, theData.getValue());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP, myDB.getNow());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_STATUS, theData.getStatus());
                result = db.insert(BalanceData.BALANCEDATA_TABLE_NAME, null, contentValues);
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

        myDB.notifyBalanceDataChanged();
        return result;
    }


    /**
     * This method will return the data corresponding to the selected id
     * @param id The id of the Balance Data to retrieve its data
     * @return The Cursor wirh the required Data
     */
    public Cursor getDataCursor(int id) {
        String theQuery = "select * from "+BalanceData.BALANCEDATA_TABLE_NAME+ " where "+BalanceData.BALANCEDATA_COLUMN_ID+"="+id+"";
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
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BalanceData.BALANCEDATA_TABLE_NAME);
        return numRows;
    }

    public boolean update (Integer id, BalanceData theData) {
        boolean retState = false;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_VALUE, theData.getValue());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION, theData.getDescription());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE,theData.getPaymentDate());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_DUE_DATE,theData.getDate());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_STATUS,theData.getStatus());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_CATEGORY,theData.getCategory());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP, myDB.getNow());
                String theWhere = BalanceData.BALANCEDATA_COLUMN_ID+" = ? ";
                //update returns the number of rows affected
                if (db.update(BalanceData.BALANCEDATA_TABLE_NAME, contentValues, theWhere, new String[] { Integer.toString(id) } ) != 1){
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

        myDB.notifyBalanceDataChanged();
        return retState;
    }

    /**
     * Return a Cursor with all BalanceDatas in the database
     * @return the Cursor with Outcomes and Incomes and Informative data ion the database
     */
    public Cursor getAllCursor(){
        Cursor cursor;
        //return cursor;

        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();

            //return cursor;
            String[] Projections = getProjections();
            cursor = db.query(BalanceData.BALANCEDATA_TABLE_NAME,Projections,null,null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    private String[] getProjections(){
        String[] Projections = {BalanceData.BALANCEDATA_COLUMN_ID,
                BalanceData.BALANCEDATA_COLUMN_CATEGORY,
                BalanceData.BALANCEDATA_COLUMN_DESCRIPTION,
                BalanceData.BALANCEDATA_COLUMN_DUE_DATE,
                BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE,
                BalanceData.BALANCEDATA_COLUMN_STATUS,
                BalanceData.BALANCEDATA_COLUMN_VALUE,
                BalanceData.BALANCEDATA_COLUMN_TIMESTAMP};
        return Projections;
    }

    /**
     * Return a Cursor with all Income BalanceDatas in the database
     * @return TheCursor with all Incomes
     */
    public Cursor getIncomesCursor(){

        Cursor cursor;

        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            String[] Projections = getProjections();

            /* “inner” join.
        select *
from category
join balanceData
   on category.category_id = balanceData.category_id
where category.operation = 1

         */
            //SELECT id, idCategory, description, date, paymentDate, status, modificationDateTime
            // FROM category inner join balanceData on id = idCategory WHERE operation = 1
            String theTableArg = BalanceData.BALANCEDATA_TABLE_NAME +
                    " inner join " + Category.CATEGORY_TABLE_NAME +
                    " on " +Category.CATEGORY_TABLE_NAME+"."+Category.CATEGORY_COLUMN_ID +" = "+ BalanceData.BALANCEDATA_TABLE_NAME+"."+BalanceData.BALANCEDATA_COLUMN_CATEGORY;

            String theWhere = Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.CREDIT).toString();
            cursor = db.query(theTableArg,Projections,theWhere,null,
                    null,null,null);
        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Return a Cursor with all Outcomes BalanceDatas in the database
     * @return the Cursor with all Outcomes
     */
    public Cursor getOutcomesCursor(){

        Cursor cursor;

        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            String[] Projections = getProjections();

            /* “inner” join.
        select *
from category
join balanceData
   on category.category_id = balanceData.category_id
where category.operation = 1

         */
            //SELECT id, idCategory, description, date, paymentDate, status, modificationDateTime
            // FROM category inner join balanceData on id = idCategory WHERE operation = 1
            String theTableArg = BalanceData.BALANCEDATA_TABLE_NAME +
                    " inner join " + Category.CATEGORY_TABLE_NAME +
                    " on " +Category.CATEGORY_TABLE_NAME+"."+Category.CATEGORY_COLUMN_ID +" = "+ BalanceData.BALANCEDATA_TABLE_NAME+"."+BalanceData.BALANCEDATA_COLUMN_CATEGORY;

            String theWhere = Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.DEBIT).toString();
            cursor = db.query(theTableArg,Projections,theWhere,null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }
    /**
     * Return a Cursor with all Income BalanceDatas in the database
     * @return TheCursor with all Informative data
     */
    public Cursor getInformativesCursor(){

        Cursor cursor;

        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            String[] Projections = getProjections();

            /* “inner” join.
        select *
from category
join balanceData
   on category.category_id = balanceData.category_id
where category.operation = 1

         */
            //SELECT id, idCategory, description, date, paymentDate, status, modificationDateTime
            // FROM category inner join balanceData on id = idCategory WHERE operation = 1
            String theTableArg = BalanceData.BALANCEDATA_TABLE_NAME +
                    " inner join " + Category.CATEGORY_TABLE_NAME +
                    " on " +Category.CATEGORY_TABLE_NAME+"."+Category.CATEGORY_COLUMN_ID +" = "+ BalanceData.BALANCEDATA_TABLE_NAME+"."+BalanceData.BALANCEDATA_COLUMN_CATEGORY;

            String theWhere = Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.INFORMATIVE).toString();
            cursor = db.query(theTableArg,Projections,theWhere,null,
                    null,null,null);
        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Delete a Balance Data from the Database
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
                String theWhere = BalanceData.BALANCEDATA_COLUMN_ID+" = ? ";
                /**
                 * the number of rows affected if a whereClause is passed in, 0 otherwise.
                 * To remove all rows and get a count pass "1" as the whereClause.
                 */
                int theResult =  db.delete(BalanceData.BALANCEDATA_TABLE_NAME,
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

        myDB.notifyBalanceDataChanged();
        return result;
    }
    /////////      END BALANCE DATA METHODS  ///////////////
}
