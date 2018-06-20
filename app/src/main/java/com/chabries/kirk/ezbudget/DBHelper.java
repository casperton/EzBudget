package com.chabries.kirk.ezbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * This class will comunicate with the SQLite Database and perform all kinds of database operations
 * Todo: Maybe it is better to create an Interface and a subClass for each Database Table. We have to see the pros and cons and if it is possible or not,
 * remembering that this class extends SQLiteOpenHelper
 */
public class DBHelper extends SQLiteOpenHelper {


    private String getNow() {
        //NOW
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;

    }
    public static final String DATABASE_NAME = "EzBudgetDB.db";

    //ArrayList<Category> myDefaultCategories;
    //////////////////////////
    // TABLE CATEGORY
    /////////////////////////

    private static final String CREATE_TABLE_CATEGORY = "create table " + Category.CATEGORY_TABLE_NAME +
            "(" + Category.CATEGORY_COLUMN_ID + " integer primary key autoincrement, " +
            Category.CATEGORY_COLUMN_NAME +" text," +
            Category.CATEGORY_COLUMN_DESCRIPTION + " text," +
            Category.CATEGORY_COLUMN_OPERATION + " integer)";

    private static final String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS " + Category.CATEGORY_TABLE_NAME;



    //////////////////////////
    // TABLE BALANCE DATA
    /////////////////////////
    private static final String CREATE_TABLE_BALANCEDATA = "create table " + BalanceData.BALANCEDATA_TABLE_NAME +
            "(" + BalanceData.BALANCEDATA_COLUMN_ID + " integer primary key autoincrement, " +
            BalanceData.BALANCEDATA_COLUMN_DUE_DATE +" text," +
            BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE +" text," +
            BalanceData.BALANCEDATA_COLUMN_VALUE + " double," +
            BalanceData.BALANCEDATA_COLUMN_CATEGORY + " integer," +
            BalanceData.BALANCEDATA_COLUMN_DESCRIPTION + " text," +
            BalanceData.BALANCEDATA_COLUMN_STATUS + " integer," +
            BalanceData.BALANCEDATA_COLUMN_TIMESTAMP + " text)";

    private static final String DROP_TABLE_BALANCEDATA = "DROP TABLE IF EXISTS " + BalanceData.BALANCEDATA_TABLE_NAME;

    //////////////////////////
    // TABLE BALANCE DATA RECURRENT
    /////////////////////////
    private static final String CREATE_TABLE_BALANCEDATAREC = "create table " + BalanceData.BALANCEDATAREC_TABLE_NAME +
            "(" + BalanceData.BALANCEDATAREC_COLUMN_ID + " integer primary key autoincrement, " +
            BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE +" date," +
            BalanceData.BALANCEDATAREC_COLUMN_VALUE + " double," +
            BalanceData.BALANCEDATAREC_COLUMN_CATEGORY + " integer," +
            BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION + " text," +
            BalanceData.BALANCEDATAREC_COLUMN_PERIOD + " integer)";

    private static final String DROP_TABLE_BALANCEDATAREC = "DROP TABLE IF EXISTS " + BalanceData.BALANCEDATAREC_TABLE_NAME;


    public DBHelper(Context context) {

        super(context, DATABASE_NAME , null, 1);
        ArrayList<Category> myDefaultCategories = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //* Table Category*/
        db.execSQL(CREATE_TABLE_CATEGORY);
        insertDefaultCategories(db);
        //* Table Balance Data*/
        db.execSQL(CREATE_TABLE_BALANCEDATA);
        //* Table Balance Data Recurrent*/
        db.execSQL(CREATE_TABLE_BALANCEDATAREC);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Table Category
        db.execSQL(DROP_TABLE_CATEGORY);
        //Table Balance Data
        db.execSQL(DROP_TABLE_BALANCEDATA);
        //Table Balance Data Recurrent
        db.execSQL(DROP_TABLE_BALANCEDATAREC);

    }

    // Category methods
    // The following mwethos are to deal with the Category Table
    public boolean insertCategory (String name, String description, Integer operation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Category.CATEGORY_COLUMN_NAME, name);
        contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, description);
        contentValues.put( Category.CATEGORY_COLUMN_OPERATION, operation);
        db.insert(Category.CATEGORY_TABLE_NAME, null, contentValues);
        return true;
    }
    private boolean insertCategory (SQLiteDatabase db , Category theCat) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Category.CATEGORY_COLUMN_NAME, theCat.getName());
        contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, theCat.getDescription());
        contentValues.put( Category.CATEGORY_COLUMN_OPERATION, theCat.getOperation());
        db.insert(Category.CATEGORY_TABLE_NAME, null, contentValues);
        return true;
    }


    public boolean insertDefaultCategories(SQLiteDatabase theDataBase){

        insertCategory(theDataBase,Category.DB_CAT_HOUSING);
        insertCategory(theDataBase,Category.DB_CAT_FOOD);
        insertCategory(theDataBase,Category.DB_CAT_TRANSPORTATION);
        insertCategory(theDataBase,Category.DB_CAT_EDUCATION);
        insertCategory(theDataBase,Category.DB_CAT_UTILITIES);
        insertCategory(theDataBase,Category.DB_CAT_CLOTHING);
        insertCategory(theDataBase,Category.DB_CAT_MEDICAL);
        insertCategory(theDataBase,Category.DB_CAT_INSURANCE);
        insertCategory(theDataBase,Category.DB_CAT_RETIREMENT);
        insertCategory(theDataBase,Category.DB_CAT_SALARY);
        insertCategory(theDataBase,Category.DB_CAT_TAX_REFUNDS);
        insertCategory(theDataBase,Category.DB_CAT_INVESTMENTS);

        return true;
    }

    /**
     * This method will return the data corresponding to the selected id
     * @param id The id of the Category to retrieve its data
     * @return The Cursor wirh the required Data
     */
    public Cursor getCategoryData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from category where id="+id+"", null );
        return res;
    }

    /**
     * Return a Categoryfrom the passed id
     * @param id The id of the Category to retrieve its data
     * @return The Category with the required Data
     */
    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs =  getCategoryData(id);
        rs.moveToFirst();
        //column values
        Integer theID = rs.getInt(rs.getColumnIndex(Category.CATEGORY_COLUMN_ID));
        String theName = rs.getString(rs.getColumnIndex(Category.CATEGORY_COLUMN_NAME));
        String theDescription = rs.getString(rs.getColumnIndex(Category.CATEGORY_COLUMN_DESCRIPTION));
        Integer theOperation = rs.getInt(rs.getColumnIndex(Category.CATEGORY_COLUMN_OPERATION));

        Category theCat = new Category();
        theCat.setID(theID);
        theCat.setName(theName);
        theCat.setDescription(theDescription);
        theCat.setOperation(theOperation);
        return theCat;
    }

    /**
     * Return the number of Rows of the Category table
     * @return the number of rows in the Category table
     */
    public int getCategoryRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Category.CATEGORY_TABLE_NAME);
        return numRows;
    }

    /**
     *
     * Return the categoryid of the name
     * @param theCategoryName
     * @return The Category IKD
     */
    public int getCategoryID(String theCategoryName){
        int theID = Category.UNKNOWN;
        SQLiteDatabase db = this.getReadableDatabase();
        String findNameSQL = "select * from category where "+ Category.CATEGORY_COLUMN_NAME+" = '"+theCategoryName+"'";

        Cursor res =  db.rawQuery( findNameSQL , null );
        if (res != null && res.getCount()>0) {
            res.moveToFirst();
            theID =  res.getInt(res.getColumnIndex(Category.CATEGORY_COLUMN_ID));

        }
        res.close();
        return theID;
    }
    /**
     * This method will update a row in the Category Table
     * @param id The id of the Category to update
     * @param name The name
     * @param description The Description
     * @param operation The operation
     * @return true on success
     */
    public boolean updateCategory (Integer id, String name, String description, Integer operation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Category.CATEGORY_COLUMN_NAME, name);
        contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, description);
        contentValues.put( Category.CATEGORY_COLUMN_OPERATION, operation);
        db.update(Category.CATEGORY_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    /**
     * Delete a Category from the Database
     * @param id The id of the category to delete
     * @return
     */
    public Integer deleteCategory (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Category.CATEGORY_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    /**
     * Return a List with all categories in the database
     * @return The List<Category>
     */
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> array_list = new ArrayList<Category>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Category.CATEGORY_TABLE_NAME + " ORDER BY " +
                Category.CATEGORY_COLUMN_NAME + " DESC";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( selectQuery, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category note = new Category();
                note.setID(cursor.getInt(cursor.getColumnIndex("id")));
                note.setName(cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_NAME)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_DESCRIPTION)));
                note.setOperation(cursor.getInt(cursor.getColumnIndex(Category.CATEGORY_COLUMN_OPERATION)));
                array_list.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();
        return array_list;
    }

    /**
     * Return a list with the names of all categories in the database
     * @return
     */
    public ArrayList<String>getAllCategoryNames(){
        ArrayList<String> array_list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Category.CATEGORY_TABLE_NAME + " ORDER BY " +
                Category.CATEGORY_COLUMN_NAME + " DESC";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( selectQuery, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_NAME));
                array_list.add(name);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();
        return array_list;
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase db){
        //String selectQuery = "SELECT  * FROM " + Category.CATEGORY_TABLE_NAME + " ORDER BY " +
        //        Category.CATEGORY_COLUMN_NAME + " DESC";
        //hp = new HashMap();
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor =  db.rawQuery( selectQuery, null );
        Cursor cursor;
        //return cursor;
        String[] Projections = {Category.CATEGORY_COLUMN_ID,Category.CATEGORY_COLUMN_NAME,Category.CATEGORY_COLUMN_DESCRIPTION,Category.CATEGORY_COLUMN_OPERATION};
        cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,null,null,
                null,null,null);
        //Log.i("MY CURSOR SIZE: ", Integer.toString(cursor.getCount()));
        return cursor;
    }
////////////////   END CATEGORY METHODS /////////////////////
    ///////////    START BALANCE DATA METHODS ///////////////

// The following mwethos are to deal with the Balance Data Table
    /*
public boolean insertBalanceData (Integer , String description, Integer operation) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(Category.CATEGORY_COLUMN_NAME, name);
    contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, description);
    contentValues.put( Category.CATEGORY_COLUMN_OPERATION, operation);
    db.insert(Category.CATEGORY_TABLE_NAME, null, contentValues);
    return true;
}
*/

    public boolean insertBalanceData(BalanceData theData){
        SQLiteDatabase db = this.getWritableDatabase();
        return insertBalanceData(db, theData);

    }

    private boolean insertBalanceData (SQLiteDatabase db , BalanceData theData) {
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
    public Cursor getBalanceDataData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from balanceData where id="+id+"", null );
        return res;
    }

    /**
     * Return the number of Rows of the Balance Data table
     * @return the number of rows in the Balance Data table
     */
    public int getBalDataRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BalanceData.BALANCEDATA_TABLE_NAME);
        return numRows;
    }

    public boolean updateBalanceData (Integer id, BalanceData theData) {
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
