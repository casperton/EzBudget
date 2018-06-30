package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.OPERATION;

import java.util.ArrayList;

public class DBCategory {

    private final String TAG = "DB_CATEGORY";
    private DBHelper myDB;
    public DBCategory(Context context) {
        myDB = DBHelper.getInstance(context);

    }

    // Category methods
    // The following mwethos are to deal with the Category Table

    /**
     * Insert a row in the category database
     * @param name
     * @param description
     * @param operation
     * @return the number of the row inserted or -1 if failed
     */
    public Long insert (String name, String description, Integer operation) {
        Long result;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Category.CATEGORY_COLUMN_NAME, name);
                contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, description);
                contentValues.put( Category.CATEGORY_COLUMN_OPERATION, operation);
                result = db.insert(Category.CATEGORY_TABLE_NAME, null, contentValues);
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

        myDB.notifyCategoryChanged();
        return result;
    }


    /**
     * Insert a row in the Category Database
     * @param theCat the category to be inserted
     * @return the row id of the category or -1 if failed
     */
    public Long insert (Category theCat) {
        Long result;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Category.CATEGORY_COLUMN_NAME, theCat.getName());
                contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, theCat.getDescription());
                contentValues.put( Category.CATEGORY_COLUMN_OPERATION, theCat.getOperation());
                result = db.insert(Category.CATEGORY_TABLE_NAME, null, contentValues);
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

        myDB.notifyCategoryChanged();
        return result;
    }

    static public Long insertCategory (SQLiteDatabase db , Category theCat) {

        Long id;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Category.CATEGORY_COLUMN_NAME, theCat.getName());
        contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, theCat.getDescription());
        contentValues.put( Category.CATEGORY_COLUMN_OPERATION, theCat.getOperation());
        id = db.insert(Category.CATEGORY_TABLE_NAME, null, contentValues);


        return id;
    }


   static public boolean insertDefaultCategories(SQLiteDatabase theDataBase){

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
    public Cursor getDataCursor(Long id) {
        String theQuery = "select * from "+Category.CATEGORY_TABLE_NAME+" where "+Category.CATEGORY_COLUMN_ID+" = "+id+"";

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
     * Return a Categoryfrom the passed id
     * @param id The id of the Category to retrieve its data
     * @return The Category with the required Data
     */
    public Category get(Long id) {
        SQLiteDatabase db = myDB.getReadableDatabase();
        Cursor rs =  getDataCursor(id);
        rs.moveToFirst();
        //column values
        Long theID = rs.getLong(rs.getColumnIndex(Category.CATEGORY_COLUMN_ID));
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
    public int getRows(){
        SQLiteDatabase db = myDB.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Category.CATEGORY_TABLE_NAME);
        return numRows;
    }

    /**
     *
     * Return the categoryid of the name
     * @param theCategoryName
     * @return The Category IKD
     */
    public Long getID(String theCategoryName){
        Long theID = Category.UNKNOWN;
        String findNameSQL = "select * from "+Category.CATEGORY_TABLE_NAME+" where "+ Category.CATEGORY_COLUMN_NAME+" = '"+theCategoryName+"'";

        Cursor res;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            res =  db.rawQuery( findNameSQL , null );

        } finally {
            myDB.myLock.readLock().unlock();
        }

        if (res != null && res.getCount()>0) {
            res.moveToFirst();
            theID =  res.getLong(res.getColumnIndex(Category.CATEGORY_COLUMN_ID));

        }
        res.close();
        return theID;
    }

    /**
     *
     * Return the categoryid name
     * @param theCategoryID
     * @return The Category Name
     */
    public String getName(Long theCategoryID){
        String theName = "";
        String findNameSQL = "select * from "+Category.CATEGORY_TABLE_NAME+" where "+ Category.CATEGORY_COLUMN_ID+" = '"+theCategoryID.toString()+"'";

        Cursor res;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            res =  db.rawQuery( findNameSQL , null );

        } finally {
            myDB.myLock.readLock().unlock();
        }

        if (res != null && res.getCount()>0) {
            res.moveToFirst();
            theName =  res.getString(res.getColumnIndex(Category.CATEGORY_COLUMN_NAME));

        }
        res.close();
        return theName;
    }
    /**
     * This method will update a row in the Category Table
     * @param id The id of the Category to update
     * @param name The name
     * @param description The Description
     * @param operation The operation
     * @return true on success
     */
    public boolean update (Long id, String name, String description, Integer operation) {
        boolean retState = false;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Category.CATEGORY_COLUMN_NAME, name);
                contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, description);
                contentValues.put( Category.CATEGORY_COLUMN_OPERATION, operation);
                String theWhere = Category.CATEGORY_COLUMN_ID+" = ? ";
                //update returns the number of rows affected
                if (db.update(Category.CATEGORY_TABLE_NAME, contentValues, theWhere, new String[] { Long.toString(id) } ) != 1){
                    Log.e(TAG, "Update category failed");
                }else retState = true;


                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } finally {
            myDB.myLock.writeLock().unlock();
        }

        myDB.notifyCategoryChanged();
        return retState;
    }

    /**
     * Delete a Category from the Database
     * @param id The id of the category to delete
     * @return true if the delete was a success
     */
    public boolean delete (Long id) {


        boolean result = false;
        myDB.myLock.writeLock().lock();
        try {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.beginTransactionNonExclusive();
            try {
                String theWhere = Category.CATEGORY_COLUMN_ID+" = ? ";
                /**
                 * the number of rows affected if a whereClause is passed in, 0 otherwise.
                 * To remove all rows and get a count pass "1" as the whereClause.
                 */
                int theResult =  db.delete(Category.CATEGORY_TABLE_NAME,
                        theWhere,
                        new String[] { Long.toString(id) });
                if(theResult == 1) {
                    db.setTransactionSuccessful();
                    result= true;
                }else {
                    result = false;
                }
            } finally {
                db.endTransaction();
            }
        } finally {
            myDB.myLock.writeLock().unlock();
        }

        myDB.notifyCategoryChanged();
        return result;
    }

    /**
     * Return a List with all categories in the database
     * @return The List<Category>
     */
    public ArrayList<Category> getAllArray() {
        ArrayList<Category> array_list = new ArrayList<Category>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Category.CATEGORY_TABLE_NAME + " ORDER BY " +
                Category.CATEGORY_COLUMN_NAME + " DESC";
        //hp = new HashMap();
        Cursor cursor;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            cursor =  db.rawQuery( selectQuery, null );

        } finally {
            myDB.myLock.readLock().unlock();
        }
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category note = new Category();
                note.setID(cursor.getLong(cursor.getColumnIndex(Category.CATEGORY_COLUMN_ID)));
                note.setName(cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_NAME)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_DESCRIPTION)));
                note.setOperation(cursor.getInt(cursor.getColumnIndex(Category.CATEGORY_COLUMN_OPERATION)));
                array_list.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        cursor.close();
        return array_list;
    }

    /**
     * Return a list with the names of all categories in the database
     * @return
     */
    public ArrayList<String>getAllNamesArray(){
        ArrayList<String> array_list = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Category.CATEGORY_TABLE_NAME + " ORDER BY " +
                Category.CATEGORY_COLUMN_NAME + " DESC";
        Cursor cursor;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();
            cursor =  db.rawQuery( selectQuery, null );

        } finally {
            myDB.myLock.readLock().unlock();
        }


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(Category.CATEGORY_COLUMN_NAME));
                array_list.add(name);
            } while (cursor.moveToNext());
        }

        return array_list;
    }

    String[] getProjections(){
        String[] Projections = {
                Category.CATEGORY_COLUMN_ID,
                Category.CATEGORY_COLUMN_NAME,
                Category.CATEGORY_COLUMN_DESCRIPTION,
                Category.CATEGORY_COLUMN_OPERATION
        };
        return Projections;
    }
    /**
     * Return a Cursor with all Categories in the database
     * @return
     */
    public Cursor getAllCursor(){
        Cursor cursor;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();

            //return cursor;
            String[] Projections = getProjections();
            cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,null,null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Return a Cursor with all Income Categories in the database
     * @return
     */
    public Cursor getIncomesCursor(){

        Cursor cursor;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();

            //return cursor;
            String[] Projections = getProjections();
            cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.CREDIT).toString(),null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Return a Cursor with all Outcomes Categories in the database
     * @return
     */
    public Cursor getOutcomesCursor(){
        Cursor cursor;

        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();

            //return cursor;
            String[] Projections = getProjections();
            cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.DEBIT).toString(),null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }

    /**
     * Return a Cursor with all Informative Categories in the database
     * @return
     */
    public Cursor getInformativesCursor(){
        Cursor cursor;
        myDB.myLock.readLock().lock();
        try {
            SQLiteDatabase db = myDB.getReadableDatabase();

            //return cursor;
            String[] Projections = getProjections();
            cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.INFORMATIVE).toString(),null,
                    null,null,null);

        } finally {
            myDB.myLock.readLock().unlock();
        }
        return cursor;
    }
////////////////   END CATEGORY METHODS /////////////////////




}
