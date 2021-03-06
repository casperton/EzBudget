package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.HandlerThread;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;


import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.Database.DBCategory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class will comunicate with the SQLite Database and perform all kinds of database operations
 */
public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "EzBudgetDB.db";
    public static final int DATABASE_VERSION = 1;
    protected static final String TAG = "EzBudget.Database";

    //listeners of the observer patern
    private static List<CategoryChangedListener> myCategoryChangedListeners = new ArrayList<>();
    private static List<BalanceDataChangedListener> myBalanceDataChangedListeners = new ArrayList<>();
    private static List<BalanceViewChangedListener> myBalanceViewChangedListeners = new ArrayList<>();
    private static List<BalanceDataRecChangedListener> myBalanceDataRecChangedListeners= new ArrayList<>();
    private static boolean once = true;
    private static HandlerThread myHthread = null;
    private static Handler myHandler = null;

    private final static int MSG_BALDATA = 1;
    private final static int MSG_CATEGORY = 2;
    private final static int MSG_BALDATAREC = 3;
    private final static int MSG_BALVIEW = 4;

    private SharedPreferences myPrefs;
    protected ReentrantReadWriteLock myLock = new ReentrantReadWriteLock(true);

    static {
        myHthread = new HandlerThread("DatabaseHelper");
        myHthread.start();
        myHandler = new Handler(myHthread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                handleChangedNotification(msg);
            }
        };
    }

    private static DBHelper dh = null;

    /**
     * Get the running instance of the DBHelper Singleton
     * @param context
     * @return the running instance
     */
    public static DBHelper getInstance(Context context) {
        if (dh == null)
            dh = new DBHelper(context.getApplicationContext());
        return dh;
    }


    /**
     * Class Constructor
     * @param context
     */
    protected DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (!once) {
            once = true;

            File dbfile = context.getDatabasePath(DATABASE_NAME);
            if (dbfile.exists()) {
                Log.w(TAG, "Deleting " + dbfile);
                dbfile.delete();
            }

            File dbjournal = context.getDatabasePath(DATABASE_NAME + "-journal");
            if (dbjournal.exists()) {
                Log.w(TAG, "Deleting " + dbjournal);
                dbjournal.delete();
            }
        }
        if(dh== null) dh=this;
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
            BalanceData.BALANCEDATA_COLUMN_VALUE + " real," +
            BalanceData.BALANCEDATA_COLUMN_CATEGORY + " integer," +
            BalanceData.BALANCEDATA_COLUMN_REC_ID + " integer," +
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
            BalanceView.BALANCEVIEW_COLUMN_INI_DATE + " text," +
            BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE + " text," +
            BalanceView.BALANCEVIEW_COLUMN_KEY_DATE + " text," +
            BalanceView.BALANCEVIEW_COLUMN_TITLE + " text," +
            BalanceView.BALANCEVIEW_COLUMN_END_BALANCE + " real," +
            BalanceView.BALANCEVIEW_COLUMN_IS_CURRENT + " integer," +
            BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION + " text)";

    private static final String DROP_TABLE_BALANCEVIEW = "DROP TABLE IF EXISTS " + BalanceView.BALANCEVIEW_TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {

        //* Table Category*/
        db.execSQL(CREATE_TABLE_CATEGORY);
        Long CatGenIncome = DBCategory.insertCategory(db,Category.DB_CAT_GEN_INCOME);
        DBCategory.GEN_INCOME=CatGenIncome;
        Long CatGenOutcome = DBCategory.insertCategory(db,Category.DB_CAT_GEN_OUTCOME);
        DBCategory.GEN_OUTCOME=CatGenOutcome;
        /*
        Long CatHousing = DBCategory.insertCategory(db,Category.DB_CAT_HOUSING);

        //Log.i(TAG,CatHousing.toString());
        Long CatFood =DBCategory.insertCategory(db,Category.DB_CAT_FOOD);
        //Log.i(TAG,CatFood.toString());
        Long CatTranport =DBCategory.insertCategory(db,Category.DB_CAT_TRANSPORTATION);
        Long CatEducation =DBCategory.insertCategory(db,Category.DB_CAT_EDUCATION);
        Long CatUtilities =DBCategory.insertCategory(db,Category.DB_CAT_UTILITIES);
        Long CatCloth =DBCategory.insertCategory(db,Category.DB_CAT_CLOTHING);
        Long CatMedical =DBCategory.insertCategory(db,Category.DB_CAT_MEDICAL);
        Long CatInsurance =DBCategory.insertCategory(db,Category.DB_CAT_INSURANCE);
        Long CatRetire =DBCategory.insertCategory(db,Category.DB_CAT_RETIREMENT);
        Long CatSalary =DBCategory.insertCategory(db,Category.DB_CAT_SALARY);
        Long CatRefund =DBCategory.insertCategory(db,Category.DB_CAT_TAX_REFUNDS);
        Long CatInvest =DBCategory.insertCategory(db,Category.DB_CAT_INVESTMENTS);*/
        //* Table Balance Data Recurrent*/
        db.execSQL(CREATE_TABLE_BALANCEDATAREC);
        Long RecPayMent = DBBalanceDataRec.insertBalDataRec(db,BalanceData.DB_REC_PAYMENT, CatGenIncome);
        Long RecGrocery = DBBalanceDataRec.insertBalDataRec(db,BalanceData.DB_REC_GROCERY ,CatGenOutcome);
        Long RecEletricity = DBBalanceDataRec.insertBalDataRec(db,BalanceData.DB_REC_ELETRICITY, CatGenOutcome);
        Long RecWater = DBBalanceDataRec.insertBalDataRec(db,BalanceData.DB_REC_WATER, CatGenOutcome);
        Long RecPhone = DBBalanceDataRec.insertBalDataRec(db,BalanceData.DB_REC_PHONE, CatGenOutcome);
        Long RecCar = DBBalanceDataRec.insertBalDataRec(db,BalanceData.DB_REC_CAR, CatGenOutcome);
        Long RecRent = DBBalanceDataRec.insertBalDataRec(db,BalanceData.DB_REC_RENT,CatGenOutcome);
        //* Table Balance Data*/
        db.execSQL(CREATE_TABLE_BALANCEDATA);
        DBBalanceData.insertBalData(db,BalanceData.DB_REC_PAYMENT, CatGenIncome,RecPayMent);
        DBBalanceData.insertBalData(db,BalanceData.DB_REC_ELETRICITY, CatGenOutcome,RecEletricity);
        DBBalanceData.insertBalData(db,BalanceData.DB_REC_PHONE, CatGenOutcome,RecPhone);
        DBBalanceData.insertBalData(db,BalanceData.DB_REC_CAR, CatGenOutcome,RecCar);
        DBBalanceData.insertBalData(db,BalanceData.DB_REC_RENT,CatGenOutcome,RecRent);

        //* Table Balance View*/
        db.execSQL(CREATE_TABLE_BALANCEVIEW);
        DBBalanceView.insertBalView(db,BalanceView.DB_BALVIEW_LAST_MONTH);
        DBBalanceView.insertBalView(db,BalanceView.DB_BALVIEW_THIS_MONTH);
        DBBalanceView.insertBalView(db,BalanceView.DB_BALVIEW_NEXT_MONTH);


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
    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.enableWriteAheadLogging();
        super.onConfigure(db);
    }

    /**
     * Add listener to any changes in the Category Database
     * @param listener
     */
    public void addCategoryChangedListener(CategoryChangedListener listener) {
        myCategoryChangedListeners.add(listener);
    }

    /**
     * Remove listener to changes in the Category Database
     * @param listener
     */
    public void removeCategoryChangedListener(CategoryChangedListener listener) {
        myCategoryChangedListeners.remove(listener);
    }

    /**
     * Add listener to changes in the Balance Data Database
     * @param listener
     */
    public void addBalanceDataChangedListener(BalanceDataChangedListener listener) {
        myBalanceDataChangedListeners.add(listener);
    }

    /**
     * remove istener to changes in the Balance Data Database
     * @param listener
     */
    public void removeBalanceDataChangedListener(BalanceDataChangedListener listener) {
        myBalanceDataChangedListeners.remove(listener);
    }

    /**
     * add listener to chjanges in the Balance Data Recurrent Database
     * @param listener
     */
    public void addBalanceDataRecChangedListener(BalanceDataRecChangedListener listener) {
        myBalanceDataRecChangedListeners.add(listener);
    }

    /**
     * remove listener to chjanges in the Balance Data Recurrent Database
     * @param listener
     */
    public void removeBalanceDataRecChangedListener(BalanceDataRecChangedListener listener) {
        myBalanceDataRecChangedListeners.remove(listener);
    }

    /**
     * add listener to changes in the Balance View Database
     * @param listener
     */
    public void addBalanceViewChangedListener(BalanceViewChangedListener listener) {
        myBalanceViewChangedListeners.add(listener);
    }

    /**
     * remove listener to changes in the Balance View Database
     * @param listener
     */
    public void removeBalanceViewChangedListener(BalanceViewChangedListener listener) {
        myBalanceViewChangedListeners.remove(listener);
    }


    protected void notifyBalanceDataChanged() {
        Message msg = myHandler.obtainMessage();
        msg.what = MSG_BALDATA;
        myHandler.sendMessage(msg);
    }
    protected void notifyBalanceViewChanged() {
        Message msg = myHandler.obtainMessage();
        msg.what = MSG_BALVIEW;
        myHandler.sendMessage(msg);
    }

    protected void notifyCategoryChanged() {
        Message msg = myHandler.obtainMessage();
        msg.what = MSG_CATEGORY;
        myHandler.sendMessage(msg);
    }

    protected void notifyBalanceDataRecChanged() {
        Message msg = myHandler.obtainMessage();
        msg.what = MSG_BALDATAREC;
        myHandler.sendMessage(msg);
    }

    /**
     * Handler for the change notifications of the databases
     * @param msg the message to handle
     */
    private static void handleChangedNotification(Message msg) {
        // Batch notifications
        try {
            Thread.sleep(1000);
            if (myHandler.hasMessages(msg.what))
                myHandler.removeMessages(msg.what);
        } catch (InterruptedException ignored) {
        }

        // Notify listeners
        if (msg.what == MSG_CATEGORY) {
            for (CategoryChangedListener listener : myCategoryChangedListeners)
                try {
                    listener.onChanged();
                } catch (Throwable ex) {
                    Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
                }

        } else if (msg.what == MSG_BALDATA) {
            for (BalanceDataChangedListener listener : myBalanceDataChangedListeners)
                try {
                    listener.onChanged();
                } catch (Throwable ex) {
                    Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
                }


        }else if (msg.what == MSG_BALVIEW) {
            for (BalanceViewChangedListener listener : myBalanceViewChangedListeners)
                try {
                    listener.onChanged();
                } catch (Throwable ex) {
                    Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
                }

        }else if (msg.what == MSG_BALDATAREC) {
            for (BalanceDataRecChangedListener listener : myBalanceDataRecChangedListeners)
                try {
                    listener.onChanged();
                } catch (Throwable ex) {
                    Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
                }

        }
    }

    public interface CategoryChangedListener {
        void onChanged();
    }

    public interface BalanceDataChangedListener {
        void onChanged();
    }
    public interface BalanceDataRecChangedListener {
        void onChanged();
    }

    public interface BalanceViewChangedListener {
        void onChanged();
    }
    
}

