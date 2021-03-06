package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.DateHandler;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.RECURRENT;
import com.cs246.EzBudget.SummaryView.SummaryItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Class to Handle the Table BalanceData of the Database
 * BalanceData represents the financial transactions
 */
public class DBBalanceData {

    private final String TAG = "DB_BALANCE_DATA";
    private DBHelper myDB;
    private Context myContex;


    /**
    Class Constructor
     */
    public DBBalanceData(Context context) {
        myDB = DBHelper.getInstance(context);
        myContex =context;

    }

    /**
     * Static method to insert a record in the Database
     * @param db The SQLDatabase
     * @param theData the Data to be inserted
     * @param theCatID the Category Id of the data
     * @param theRecID the BalanceDataRec   id of the relationship
     * @return true if it a success
     */
    static public boolean insertBalData (SQLiteDatabase db , BalanceData theData, Long theCatID, Long theRecID) {
        boolean isFromRecurrent = theData.IsRecurrent();

        String theDate;
        /**
         * WheN THe Balance Data is FROM THE Recurrent record, there is no Year and Month in the date.
         */
        if(isFromRecurrent) {
            String theDay = theData.getDueDateDay();
            String theMonth = DateHandler.getActualMonth();
            String theYear = DateHandler.getActualYear();
            theDate = theYear + "-" + theMonth + "-" + theDay; //database format
            theData.resetRecurrent();
        }else

        theDate = theData.getDueDateDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION, theData.getDescription());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_DUE_DATE, theDate);
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_STATUS, theData.getStatus());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_VALUE, theData.getAmount());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP, DateHandler.getNowDatabaseFormat());
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_CATEGORY, theCatID);
        contentValues.put(BalanceData.BALANCEDATA_COLUMN_REC_ID, theRecID);
        db.insert(BalanceData.BALANCEDATA_TABLE_NAME, null, contentValues);


        return true;
    }

    /**
     *  Insert a Register of Balance Data into the Database
     * @param theData
     * @return the number of the row inserted or -1 if failed
     */
    private Long insert (BalanceData theData) {
        Long result;
        Long recID = Long.valueOf(-1);
        if (theData.getID() > 0) recID = theData.getID();

        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_CATEGORY, theData.getCategory());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION, theData.getDescription());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_DUE_DATE, theData.getDueDateDatabase());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE, theData.getPaymentDateDatabase());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_VALUE, theData.getAmount());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP, DateHandler.getNowDatabaseFormat());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_STATUS, theData.getStatus());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_REC_ID,recID);
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
     *  Insert a Register of Balance Data into the Database
     * @param theData the data to be inserted
     * @param isFromRec the theData parameter came from the RecurrentDatabase. This indicates we must
     *                  calculate how much recurrences of this record must be inserted in the current view period.
     * @return the number of the row inserted or -1 if failed
     */
    public Long insert (BalanceData theData, boolean isFromRec) {
        Log.i("DBBalanceDataUpd", "WILL Insert. Is Recurrent: " + (isFromRec ? "TRUE" : "FALSE"));
        Long result = Long.valueOf(-1);
        boolean isFromRecurrent = theData.IsRecurrent();
        String theDate;
        Log.i("DBBalanceDataUpd", "date: "+ theData.getDueDateHuman());
        /**
         * Whe hte Balance Data is Recurrent, the Date do not have, Year and Month.
         */
        if(isFromRecurrent || isFromRec) {
            Long theID = theData.getID();
            //DBBalanceView theViewDataBase = new DBBalanceView(myContex);
            //BalanceView theCurrentView = theViewDataBase.getCurrent();
            //Date theViewIniDate = theCurrentView.getInitialDate();
            //Date theViewEndDate = theCurrentView.getFinalDate();
            // ArrayList<Date> theDates = DateHandler.getListofDates(theViewIniDate,theViewEndDate,theData);
            ArrayList<Date> theDates = DateHandler.getListofDates(theData);
            for (int count = 0;count < theDates.size();count ++){
                theData.resetRecurrent();
                theData.setDueDate(theDates.get(count));
                result = insert(theData);

            }


            //END IS RECURRENT
        }else {
            theData.setID(Long.valueOf(-1));
            Log.i("SALVADATA", "The Date Here: "+ theData.getDueDateHuman());
            result = insert(theData);
        }
        return result;
    }


    /**
     * This method will return the data corresponding to the selected id
     * @param id The id of the Balance Data to retrieve its data
     * @return The Cursor wirh the required Data
     */
    public Cursor getDataCursor(Long id) {
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

    /**
     * Updates a row in the Database
     * @param id the id of the row to be updates
     * @param theData The Data to replace the row in the database
     * @return true in success
     */
    public boolean update (Long id, BalanceData theData) {
        boolean retState = false;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_VALUE, theData.getAmount());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION, theData.getDescription());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE,theData.getPaymentDateDatabase());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_DUE_DATE,theData.getDueDateDatabase());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_STATUS,theData.getStatus());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_CATEGORY,theData.getCategory());
                contentValues.put(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP, DateHandler.getNowDatabaseFormat());
                String theWhere = BalanceData.BALANCEDATA_COLUMN_ID+" = ? ";
                //update returns the number of rows affected
                if (db.update(BalanceData.BALANCEDATA_TABLE_NAME, contentValues, theWhere, new String[] { Long.toString(id) } ) != 1){
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
     * Delete only the BalanceData related to the Passed BalanceDataRec
     * Delete only unpaid itens from now to the end of the period
     * @param theBalRecID The BalanceDataRec Id of the correlated records to update
     * @param
     * @return true if success
     */
    public boolean deleteSelected (Long theBalRecID, DBBalanceDataRec theDB) {
        Log.i("DBBalanceDataUpd", "Need to Update the related records: " + theBalRecID);
        boolean retState = false;
        if (theBalRecID == null) return false;
        if (theBalRecID <=0) return false;

        BalanceData theRecRecord =theDB.getData(theBalRecID);
        Log.i("DBBalanceDataUpd", "Got from database the Bal Rec Id: " + theRecRecord.getID());
        Cursor cursor =  getSelectedCursor(theBalRecID, PAY_STATUS.UNPAID_UNRECEIVED);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                Log.i("DBBalanceDataUpd", "Got cursor with this number of records from BalanceDAta: " + cursor.getCount());

                do {
                    Long theID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_ID));
                    Log.i("DBBalanceDataUpd", "will Delete this BalanceDAta: " + theID);

                     delete(theID);
                   // update(theID,theData);
                    // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen

                    //Log.i("SALVADATABASE","ADDED OUTCOMES: "+description);
                    Log.i("DBBalanceDataUpd", "ID Updated: " + theID);


                } while (cursor.moveToNext());
                retState = true;
            }

        }else retState = false;

        return retState;
    }
    /**
     * Return a Cursor with all BalanceDatas in the database
     * @return the Cursor with Outcomes and Incomes and Informative data ion the database
     */
    public Cursor getAllCursor(BalanceView theView){
        Cursor cursor;
        //return cursor;
        if (theView == null){
            DBBalanceView myView = new DBBalanceView(myContex);
            theView = myView.getCurrent();
            if (theView == null){
                return null;
            }
        }
        String theInitialDate = theView.getInitialDateToDatabase();
        String theFinalDate = theView.getFinalDateToDatabase();

        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();

            //return cursor;
            String[] Projections = getProjections();
            String theWhere = BalanceData.BALANCEDATA_COLUMN_DUE_DATE+
                    " BETWEEN "+"\""+theInitialDate+"\""+" AND "+"\""+theFinalDate+"\"";
            cursor = db.query(BalanceData.BALANCEDATA_TABLE_NAME,Projections,theWhere,null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

/* This was just a try
    private BalanceData fillData(Cursor cursor) {
        if (cursor == null || cursor.getCount() > 1) return null;
        BalanceData theBalData = new BalanceData();
        if (cursor.moveToFirst()) {
            do {

                Long theID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_ID));
                String theDescription = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                String theDueDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                Double theAmount = cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                Long theCatID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
                String thePaymentDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE));
                String theTimesTamp = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP));
                Integer theStatus = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));

                //initialize BalanceData
                theBalData.setDueDateFromDatabase(theDueDate);
                theBalData.setDescription(theDescription);
                theBalData.setValue(theAmount);
                theBalData.setPaymentDateFromDatabase(thePaymentDate);
                theBalData.setStatus(theStatus);
                theBalData.setCategory(theCatID);
                theBalData.setID(theID);
                theBalData.resetRecurrent();

                // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen

                //Log.i("SALVADATABASE","ADDED OUTCOMES: "+description);
                Log.i("DBBalanceData", "ID: " + theBalData.getID());
                Log.i("DBBalanceData", "Status: " + (theStatus == 0 ? "Paid" : "Unpaid"));

            } while (cursor.moveToNext());
        }
        return theBalData;
    }
    */

    /**
     * Get Selected data from the Database. Select all data with BalRecId= theBalRecId and paymentStatus= thePaymentStatus
     * @param theBalRecID The id of the BalanceDataRec we want data for
     * @param thePaymentStatus The payment status of the data we want to select
     * @returnReturn a Cursor with the selected data
     */
    public Cursor getSelectedCursor(Long theBalRecID, Integer thePaymentStatus)
    {
        Log.i("DBBalanceDataUpd", "Will find this balance data Rec relationship: " + theBalRecID.toString());
        Log.i("DBBalanceDataUpd", "Whis this status: " + thePaymentStatus.toString());
        if (theBalRecID <= 0) return null;
        Cursor cursor;
        if (thePaymentStatus == PAY_STATUS.PAID_RECEIVED || thePaymentStatus == PAY_STATUS.UNPAID_UNRECEIVED) {


            Log.i("DBBalanceDataUpd", "ID to get related: " + theBalRecID);

            //return cursor;
            //if (theView == null){
            //   DBBalanceView myView = new DBBalanceView(myContex);
            //   theView = myView.getCurrent();
            //   if (theView == null){
            //       return null;
            //   }
            //}
            String theInitialDate = DateHandler.getNowDateDatabase();
            int theEndMonth = DateHandler.getMonthEnd();
            int theEndYear = DateHandler.getYearEnd();
            int LastDayOftheLastMonth = DateHandler.getLastDayOfMonth(theEndMonth);
            Date EndDate = DateHandler.getDate(LastDayOftheLastMonth, theEndMonth, theEndYear);
            DateFormat df = new SimpleDateFormat(DateHandler.DATABASE_DATE_FORMAT, DateHandler.DEF_LOCALE);
            String theEndDateStr = df.format(EndDate);
            String theFinalDate = theEndDateStr;

            myDB.myLock.readLock().lock();
            try {
                SQLiteDatabase db = myDB.getReadableDatabase();

                //return cursor;
                String[] Projections = getProjections();
                String theWhere = BalanceData.BALANCEDATA_COLUMN_REC_ID + " = " + theBalRecID.toString() +
                        " AND " + BalanceData.BALANCEDATA_COLUMN_DUE_DATE +
                        " BETWEEN " + "\"" + theInitialDate + "\"" + " AND " + "\"" + theFinalDate + "\"";
                cursor = db.query(BalanceData.BALANCEDATA_TABLE_NAME, Projections, theWhere, null,
                        null, null, null);

            } finally {
                myDB.myLock.readLock().unlock();
            }
        }else return null;
        Log.i("DBBalanceDataUpd", "Records: " + cursor.getCount());
        return cursor;
    }


    /**
     * Get An Array with the names odf the Columns of the Table in the Database
     * @return An Array with the names odf the Columns of the Table in the Database
     */
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
     * Return a Cursor with all Income BalanceDatas in the database in the passed period
     * @return TheCursor with all Incomes
     */
    public Cursor getIncomesCursor(BalanceView theView){

        Cursor cursor;
        String theInitialDate = theView.getInitialDateToDatabase();
        String theFinalDate = theView.getFinalDateToDatabase();
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            String[] Projections = getProjections();

            /* "inner" join.
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

            String theWhere = Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.CREDIT).toString()+
                    " AND "+BalanceData.BALANCEDATA_COLUMN_DUE_DATE+
                    " BETWEEN "+"\""+theInitialDate+"\""+" AND "+"\""+theFinalDate+"\"";

            cursor = db.query(theTableArg,Projections,theWhere,null,
                    null,null,null);
        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Return a Cursor with all Income Balance Datas in the database
     * @return TheCursor with all Incomes regardless of its view period
     */
    public Cursor getIncomesCursor(){

        Cursor cursor;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            String[] Projections = getProjections();

            /* "inner" join.
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
            //Log.i("SALVADATABASE","INCOMES");
            //Log.i("SALVADATABASE",theTableArg);
            //Log.i("SALVADATABASE",theWhere);
            cursor = db.query(theTableArg,Projections,theWhere,null,
                    null,null,null);
            //Log.i("SALVADATABASE","CURSOR SIZE: "+cursor.getCount());
        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Return a Cursor with all Outcomes BalanceDatas in the database in the passed period
     * @return the Cursor with all Outcomes
     */
    public Cursor getOutcomesCursor(BalanceView theView){

        if (theView == null) return null;
        Cursor cursor;
        String theInitialDate = theView.getInitialDateToDatabase();
        String theFinalDate = theView.getFinalDateToDatabase();
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            String[] Projections = getProjections();

            /* "inner" join.
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


            String theWhere = Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.DEBIT).toString()+
                    " AND "+BalanceData.BALANCEDATA_COLUMN_DUE_DATE+
                    " BETWEEN "+"\""+theInitialDate+"\""+" AND "+"\""+theFinalDate+"\"";

            cursor = db.query(theTableArg,Projections,theWhere,null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Return a Cursor with all Outcomes BalanceDatas in the database in the passed period
     * @return the Cursor with all Outcomes regardless of the period
     */
    public Cursor getOutcomesCursor(){


        Cursor cursor;

        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            String[] Projections = getProjections();

            /* "inner" join.
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
            //Log.i("SALVADATABASE","OUTCOMES");
            //Log.i("SALVADATABASE",theTableArg);
            //Log.i("SALVADATABASE",theWhere);
            cursor = db.query(theTableArg,Projections,theWhere,null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Get the Array of BalanceData filled with all Outcomes(Bills) Transactions in the Database
     * @return the filled array
     */
    public ArrayList<BalanceData> getOutcomesArray(){
        ArrayList<BalanceData> theArray = new ArrayList<>();
        Cursor cursor = getOutcomesCursor();
        if (cursor !=null) {
            if (cursor.moveToFirst()) {
                do {
                    BalanceData theBalData= new BalanceData();
                    Long theID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_ID));
                    String theDescription = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                    String theDueDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                    Double theAmount = cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                    Long theCatID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
                    String thePaymentDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE));
                    String theTimesTamp = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP));
                    Integer theStatus = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));

                    //initialize BalanceData
                    theBalData.setDueDateFromDatabase(theDueDate);
                    theBalData.setDescription(theDescription);
                    theBalData.setValue(theAmount);
                    theBalData.setPaymentDateFromDatabase(thePaymentDate);
                    theBalData.setStatus(theStatus);
                    theBalData.setCategory(theCatID);
                    theBalData.setID(theID);
                    theBalData.resetRecurrent();

                    // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen
                    theArray.add(theBalData);
                    //Log.i("SALVADATABASE","ADDED OUTCOMES: "+description);
                    Log.i("DBBalanceData", "ID: "+theBalData.getID());
                    Log.i("DBBalanceData", "Status: "+(theStatus==0 ? "Paid" : "Unpaid"));

                } while (cursor.moveToNext());
            }
        }

        return theArray;
    }

    /**
     * Get the Array of BalanceData filled with all Income Transactions in the Database
     * @return the filled array
     */
    public ArrayList<BalanceData> getIncomesArray(){
        ArrayList<BalanceData> theArray = new ArrayList<>();
        Cursor cursor = getIncomesCursor();
        if (cursor !=null) {
            if (cursor.moveToFirst()) {
                do {
                    BalanceData theBalData= new BalanceData();
                    Long theID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_ID));
                    String theDescription = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                    String theDueDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                    Double theAmount = cursor.getDouble(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                    Long theCatID = cursor.getLong(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
                    String thePaymentDate = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE));
                    String theTimesTamp = cursor.getString(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP));
                    Integer theStatus = cursor.getInt(cursor.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));

                    //initialize BalanceData
                    theBalData.setDueDateFromDatabase(theDueDate);
                    theBalData.setDescription(theDescription);
                    theBalData.setValue(theAmount);
                    theBalData.setPaymentDateFromDatabase(thePaymentDate);
                    theBalData.setStatus(theStatus);
                    theBalData.setCategory(theCatID);
                    theBalData.setID(theID);
                    theBalData.resetRecurrent();

                    Log.i("DBBalanceData", "ID: "+theBalData.getID());
                    Log.i("DBBalanceData", "Status: "+(theStatus==0 ? "Paid" : "Unpaid"));
                    // Set SummaryType.Expense for expenses. This allows them to be swiped for marking as paid on summary screen
                    theArray.add(theBalData);
                    //Log.i("SALVADATABASE","ADDED OUTCOMES: "+description);


                } while (cursor.moveToNext());
            }
        }

        return theArray;

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

            /* "inner" join.
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
     * Delete a row of Balance Data from the Database
     * @param id The id of the Balance Data to delete
     * @return true if the delete was a success, false otherwise
     */
    public boolean delete (Long id) {


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

        myDB.notifyBalanceDataChanged();
        return result;
    }
    /////////      END BALANCE DATA METHODS  ///////////////
}
