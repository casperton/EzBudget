package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.DateHandler;
import com.cs246.EzBudget.OPERATION;

/**
 * Class to Handle the Table BalanceDataRec (The Recurrent Data) of the Database
 */
public class DBBalanceDataRec{
    
    private DBHelper myDB;
    private final String TAG = "DB_BALANCE_DATA_REC";
    public DBBalanceDataRec(Context context) {
        myDB = DBHelper.getInstance(context);

    }
    static public boolean insertBalDataRec (SQLiteDatabase db , BalanceData theCat) {
        //todo: falta adicionar categoria
        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION, theCat.getDescription());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE, theCat.getDate());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_PERIOD, theCat.getRecPeriod());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_VALUE, theCat.getValue());
        db.insert(BalanceData.BALANCEDATAREC_TABLE_NAME, null, contentValues);


        return true;
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
                //contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_TIMESTAMP, DateHandler.getNow());
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
            cursor = db.query(BalanceData.BALANCEDATAREC_TABLE_NAME,Projections,null,null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    private String[] getProjections(){
        String[] Projections = {BalanceData.BALANCEDATAREC_COLUMN_ID,
                BalanceData.BALANCEDATAREC_COLUMN_CATEGORY,
                BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION,
                BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE,
                BalanceData.BALANCEDATAREC_COLUMN_PERIOD,
                BalanceData.BALANCEDATAREC_COLUMN_VALUE};
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
            String theTableArg = BalanceData.BALANCEDATAREC_TABLE_NAME +
                    " inner join " + Category.CATEGORY_TABLE_NAME +
                    " on " +Category.CATEGORY_TABLE_NAME+"."+Category.CATEGORY_COLUMN_ID +" = "+ BalanceData.BALANCEDATAREC_TABLE_NAME+"."+BalanceData.BALANCEDATAREC_COLUMN_CATEGORY;

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
            String theTableArg = BalanceData.BALANCEDATAREC_TABLE_NAME +
                    " inner join " + Category.CATEGORY_TABLE_NAME +
                    " on " +Category.CATEGORY_TABLE_NAME+"."+Category.CATEGORY_COLUMN_ID +" = "+ BalanceData.BALANCEDATAREC_TABLE_NAME+"."+BalanceData.BALANCEDATAREC_COLUMN_CATEGORY;

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
            String theTableArg = BalanceData.BALANCEDATAREC_TABLE_NAME +
                    " inner join " + Category.CATEGORY_TABLE_NAME +
                    " on " +Category.CATEGORY_TABLE_NAME+"."+Category.CATEGORY_COLUMN_ID +" = "+ BalanceData.BALANCEDATAREC_TABLE_NAME+"."+BalanceData.BALANCEDATAREC_COLUMN_CATEGORY;

            String theWhere = Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.INFORMATIVE).toString();
            cursor = db.query(theTableArg,Projections,theWhere,null,
                    null,null,null);
        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }
}