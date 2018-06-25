package com.cs246.EzBudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.OPERATION;

import java.util.ArrayList;

public class DBCategory extends DBHelper{
    public DBCategory(Context context) {
        super(context);
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
    static public boolean insertCategory (SQLiteDatabase db , Category theCat) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Category.CATEGORY_COLUMN_NAME, theCat.getName());
        contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, theCat.getDescription());
        contentValues.put( Category.CATEGORY_COLUMN_OPERATION, theCat.getOperation());
        db.insert(Category.CATEGORY_TABLE_NAME, null, contentValues);
        return true;
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
    public Cursor getDataCursor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String theQuery = "select * from category where "+Category.CATEGORY_COLUMN_ID+" = "+id+"";
        Cursor res =  db.rawQuery( theQuery, null );
        return res;
    }

    /**
     * Return a Categoryfrom the passed id
     * @param id The id of the Category to retrieve its data
     * @return The Category with the required Data
     */
    public Category get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs =  getDataCursor(id);
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
    public int getRows(){
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
    public int getID(String theCategoryName){
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
    public boolean update (Integer id, String name, String description, Integer operation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Category.CATEGORY_COLUMN_NAME, name);
        contentValues.put(Category.CATEGORY_COLUMN_DESCRIPTION, description);
        contentValues.put( Category.CATEGORY_COLUMN_OPERATION, operation);
        String theWhere = Category.CATEGORY_COLUMN_ID+" = ? ";
        db.update(Category.CATEGORY_TABLE_NAME, contentValues, theWhere, new String[] { Integer.toString(id) } );
        return true;
    }

    /**
     * Delete a Category from the Database
     * @param id The id of the category to delete
     * @return
     */
    public Integer delete (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String theWhere = Category.CATEGORY_COLUMN_ID+" = ? ";
        return db.delete(Category.CATEGORY_TABLE_NAME,
                theWhere,
                new String[] { Integer.toString(id) });
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( selectQuery, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category note = new Category();
                note.setID(cursor.getInt(cursor.getColumnIndex(Category.CATEGORY_COLUMN_ID)));
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
    public ArrayList<String>getAllNamesArray(){
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
     * @param db
     * @return
     */
    public Cursor getAllCursor(SQLiteDatabase db){

        Cursor cursor;
        //return cursor;
        String[] Projections = getProjections();
        cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,null,null,
                null,null,null);
        return cursor;
    }

    /**
     * Return a Cursor with all Income Categories in the database
     * @param db
     * @return
     */
    public Cursor getIncomesCursor(SQLiteDatabase db){

        Cursor cursor;
        String[] Projections = getProjections();
        //return cursor;
        cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.CREDIT).toString(),null,
                null,null,null);
        return cursor;
    }

    /**
     * Return a Cursor with all Outcomes Categories in the database
     * @param db
     * @return
     */
    public Cursor getOutcomesCursor(SQLiteDatabase db){
        Cursor cursor;
        //return cursor;
        String[] Projections = getProjections();
        cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.DEBIT).toString(),null,
                null,null,null);
        return cursor;
    }

    /**
     * Return a Cursor with all Informative Categories in the database
     * @param db
     * @return
     */
    public Cursor getInformativesCursor(SQLiteDatabase db){

        Cursor cursor;
        //return cursor;
        String[] Projections = getProjections();
        cursor = db.query(Category.CATEGORY_TABLE_NAME,Projections,Category.CATEGORY_COLUMN_OPERATION + " = "+ (OPERATION.INFORMATIVE).toString(),null,
                null,null,null);
        return cursor;
    }
////////////////   END CATEGORY METHODS /////////////////////




}
