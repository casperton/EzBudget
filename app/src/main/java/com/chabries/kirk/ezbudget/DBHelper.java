package com.chabries.kirk.ezbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    // TABLE CATEGORY
    public static final String DATABASE_NAME = "EzBudgetDB.db";
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_COLUMN_ID = "id";
    public static final String CATEGORY_COLUMN_NAME = "name";
    public static final String CATEGORY_COLUMN_DESCRIPTION = "description";
    public static final String CATEGORY_COLUMN_OPERATION = "operation";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                //* Table Category*/
                "create table " + CATEGORY_TABLE_NAME +
                        "(" + CATEGORY_COLUMN_ID + " integer primary key, " +
                        CATEGORY_COLUMN_NAME +" text," +
                        CATEGORY_COLUMN_DESCRIPTION + " text," +
                        CATEGORY_COLUMN_OPERATION + " integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Table Category
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCategory (String name, String description, String operation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_NAME, name);
        contentValues.put(CATEGORY_COLUMN_DESCRIPTION, description);
        contentValues.put( CATEGORY_COLUMN_OPERATION, operation);
        db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from category where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CATEGORY_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String description, String operation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_NAME, name);
        contentValues.put(CATEGORY_COLUMN_DESCRIPTION, description);
        contentValues.put( CATEGORY_COLUMN_OPERATION, operation);
        db.update(CATEGORY_COLUMN_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteCategory (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CATEGORY_COLUMN_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> array_list = new ArrayList<Category>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CATEGORY_TABLE_NAME + " ORDER BY " +
                CATEGORY_COLUMN_NAME + " DESC";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( selectQuery, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category note = new Category();
                note.setID(cursor.getInt(cursor.getColumnIndex("id")));
                note.setName(cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_NAME)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_DESCRIPTION)));
                note.setOperation(cursor.getInt(cursor.getColumnIndex(CATEGORY_COLUMN_OPERATION)));
                array_list.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();
        return array_list;
    }

    public ArrayList<String>getAllCategoryNames(){
        ArrayList<String> array_list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CATEGORY_TABLE_NAME + " ORDER BY " +
                CATEGORY_COLUMN_NAME + " DESC";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( selectQuery, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_NAME));
                array_list.add(name);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();
        return array_list;
    }

}
