package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.util.Log;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.Database.DBCategory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class will comunicate with the SQLite Database and perform all kinds of database operations
 * Todo: Maybe it is better to create an Interface and a subClass for each Database Table. We have to see the pros and cons and if it is possible or not,
 * remembering that this class extends SQLiteOpenHelper
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATE_FORMAT = "mm/dd/yyyy";
    public static final String TIMESTAMP_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATABASE_NAME = "EzBudgetDB.db";

    static public String getNow() {
        //NOW
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;

    }


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

    //////////////////////////
    // TABLE BALANCE VIEW
    /////////////////////////
    private static final String CREATE_TABLE_BALANCEVIEW = "create table " + BalanceView.BALANCEVIEW_TABLE_NAME +
            "(" + BalanceView.BALANCEVIEW_COLUMN_ID + " integer primary key autoincrement, " +
            BalanceView.BALANCEVIEW_COLUMN_INI_DATE + " date," +
            BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE + " date," +
            BalanceView.BALANCEVIEW_COLUMN_KEY_DATE + " date," +
            BalanceView.BALANCEVIEW_COLUMN_END_BALANCE + " double," +
            BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION + " text)";

    private static final String DROP_TABLE_BALANCEVIEW = "DROP TABLE IF EXISTS " + BalanceView.BALANCEVIEW_TABLE_NAME;


    public DBHelper(Context context) {

        super(context, DATABASE_NAME , null, 1);
        ArrayList<Category> myDefaultCategories = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //* Table Category*/
        db.execSQL(CREATE_TABLE_CATEGORY);
        DBCategory.insertCategory(db,Category.DB_CAT_HOUSING);
        DBCategory.insertCategory(db,Category.DB_CAT_FOOD);
        DBCategory.insertCategory(db,Category.DB_CAT_TRANSPORTATION);
        DBCategory.insertCategory(db,Category.DB_CAT_EDUCATION);
        DBCategory.insertCategory(db,Category.DB_CAT_UTILITIES);
        DBCategory.insertCategory(db,Category.DB_CAT_CLOTHING);
        DBCategory.insertCategory(db,Category.DB_CAT_MEDICAL);
        DBCategory.insertCategory(db,Category.DB_CAT_INSURANCE);
        DBCategory.insertCategory(db,Category.DB_CAT_RETIREMENT);
        DBCategory.insertCategory(db,Category.DB_CAT_SALARY);
        DBCategory.insertCategory(db,Category.DB_CAT_TAX_REFUNDS);
        DBCategory.insertCategory(db,Category.DB_CAT_INVESTMENTS);
        //* Table Balance Data*/
        db.execSQL(CREATE_TABLE_BALANCEDATA);
        //* Table Balance Data Recurrent*/
        db.execSQL(CREATE_TABLE_BALANCEDATAREC);
        //* Table Balance View*/
        db.execSQL(CREATE_TABLE_BALANCEVIEW);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Table Category
        db.execSQL(DROP_TABLE_CATEGORY);
        //Table Balance Data
        db.execSQL(DROP_TABLE_BALANCEDATA);
        //Table Balance Data Recurrent
        db.execSQL(DROP_TABLE_BALANCEDATAREC);
        //Table Balance View
        db.execSQL(DROP_TABLE_BALANCEVIEW);

    }





}
