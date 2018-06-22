package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs246.EzBudget.BalanceData;

/**
 * Class to Handle the Table BalanceData of the Database
 */
public class DBBalanceData extends DBHelper{
    public DBBalanceData(Context context) {
        super(context);
    }



    public boolean insert(BalanceData theData){
        SQLiteDatabase db = this.getWritableDatabase();
        return insert(db, theData);

    }

    private boolean insert (SQLiteDatabase db , BalanceData theData) {
        Long result;
        Log.i("DATABASE_OPERATION", Double.toString(theData.getValue()));
        Log.i("DATABASE_OPER",theData.getCategory().getName());
        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_CATEGORY, theData.getCategory().getID());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION, theData.getDescription());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_DUE_DATE, theData.getDate());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE, theData.getPaymentDate());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_VALUE, theData.getValue());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP, getNow());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_STATUS, theData.getStatus());
        result = db.insert(BalanceData.BALANCEDATA_TABLE_NAME, null, contentValues);
        if (result > -1 ) return true;
        else return false;
    }


    /**
     * This method will return the data corresponding to the selected id
     * @param id The id of the Balance Data to retrieve its data
     * @return The Cursor wirh the required Data
     */
    public Cursor getDataCursor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from balanceData where id="+id+"", null );
        return res;
    }

    /**
     * Return the number of Rows of the Balance Data table
     * @return the number of rows in the Balance Data table
     */
    public int getRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BalanceData.BALANCEDATA_TABLE_NAME);
        return numRows;
    }

    public boolean update (Integer id, BalanceData theData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_VALUE, theData.getValue());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION, theData.getDescription());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE,theData.getPaymentDate());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_DUE_DATE,theData.getDate());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_STATUS,theData.getStatus());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_CATEGORY,theData.getCategory().getID());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP, getNow());
        db.update(BalanceData.BALANCEDATA_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    /////////      END BALANCE DATA METHODS  ///////////////
}
