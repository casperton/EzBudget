package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;



import com.cs246.EzBudget.BalanceView;

/**
 * Class to Handle the BalanceView Table of the database
 */
public class DBBalanceView extends DBHelper {

    public DBBalanceView(Context context) {
        super(context);
    }

    public boolean insert(BalanceView theData){
        SQLiteDatabase db = this.getWritableDatabase();
        return insert(db, theData);

    }

    private boolean insert (SQLiteDatabase db , BalanceView theData) {
        Long result;

        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_INI_DATE, theData.getInitialDate());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION, theData.getDescription());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE, theData.getEndDate());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_KEY_DATE, theData.getKeyDate());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_END_BALANCE, theData.getEndBalance());
        result = db.insert(BalanceView.BALANCEVIEW_TABLE_NAME, null, contentValues);
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
        String query = "select * from "+BalanceView.BALANCEVIEW_TABLE_NAME+" where id="+id+"";
        Cursor res =  db.rawQuery( query, null );
        return res;
    }

    /**
     * Return the number of Rows of the Balance Data table
     * @return the number of rows in the Balance Data table
     */
    public int getRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BalanceView.BALANCEVIEW_TABLE_NAME);
        return numRows;
    }

    public boolean update (Integer id, BalanceView theData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_INI_DATE, theData.getInitialDate());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION, theData.getDescription());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE,theData.getEndDate());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_KEY_DATE,theData.getKeyDate());
        contentValues.put(BalanceView.BALANCEVIEW_COLUMN_END_BALANCE,theData.getEndBalance());
        db.update(BalanceView.BALANCEVIEW_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

}
