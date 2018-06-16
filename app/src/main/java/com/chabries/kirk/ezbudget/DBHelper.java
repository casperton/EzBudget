package com.chabries.kirk.ezbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will comunicate with the SQLite Database and perform all kinds of database operations
 * Todo: Maybe it is better to create an Interface and a subClass for each Database Table. We have to see the pros and cons and if it is possible or not,
 * remembering that this class extends SQLiteOpenHelper
 */
public class DBHelper extends SQLiteOpenHelper {

    // TABLE CATEGORY
    public static final String DATABASE_NAME = "EzBudgetDB.db";


    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                //* Table Category*/
                "create table " + Category.CATEGORY_TABLE_NAME +
                        "(" + Category.CATEGORY_COLUMN_ID + " integer primary key, " +
                        Category.CATEGORY_COLUMN_NAME +" text," +
                        Category.CATEGORY_COLUMN_DESCRIPTION + " text," +
                        Category.CATEGORY_COLUMN_OPERATION + " integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Table Category
        db.execSQL("DROP TABLE IF EXISTS " + Category.CATEGORY_TABLE_NAME);
        onCreate(db);
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
     * Return the number of Rows of the Category table
     * @return the number of rows in the Category table
     */
    public int getCategoryRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Category.CATEGORY_TABLE_NAME);
        return numRows;
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
////////////////   END CATEGORY METHODS /////////////////////

}
