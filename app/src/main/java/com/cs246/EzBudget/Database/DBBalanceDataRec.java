package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.cs246.EzBudget.BalanceData;

/**
 * Class to Handle the Table BalanceDataRec (The Recurrent Data) of the Database
 */
public class DBBalanceDataRec extends DBHelper{
    public DBBalanceDataRec(Context context) {
        super(context);
    }


    public boolean insert(BalanceData theData){
        SQLiteDatabase db = this.getWritableDatabase();
        return insert(db, theData);

    }

    private boolean insert(SQLiteDatabase db , BalanceData theData) {
        Long result;
        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_CATEGORY, theData.getCategory().getID());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION, theData.getDescription());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE, theData.getDate());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_VALUE, theData.getValue());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_TIMESTAMP, getNow());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_PERIOD, theData.getStatus());
        result = db.insert(BalanceData.BALANCEDATAREC_TABLE_NAME, null, contentValues);
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
        String query = "select * from "+BalanceData.BALANCEDATAREC_TABLE_NAME+" where id="+id+"";
        Cursor res =  db.rawQuery( query, null );
        return res;
    }

    /**
     * Return the number of Rows of the Balance Data table
     * @return the number of rows in the Balance Data table
     */
    public int getRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BalanceData.BALANCEDATAREC_TABLE_NAME);
        return numRows;
    }

    public boolean updateBalanceData (Integer id, BalanceData theData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_VALUE, theData.getValue());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION, theData.getDescription());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE,theData.getDate());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_PERIOD,theData.getRecPeriod());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_CATEGORY,theData.getCategory().getID());
        contentValues.put(BalanceData.BALANCEDATAREC_COLUMN_TIMESTAMP, getNow());
        db.update(BalanceData.BALANCEDATAREC_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    /////////      END BALANCE DATA METHODS  ///////////////
}