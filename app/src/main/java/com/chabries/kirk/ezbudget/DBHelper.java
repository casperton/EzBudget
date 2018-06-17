package com.chabries.kirk.ezbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    ArrayList<Category> myDefaultCategories;

    private static final String CREATE_TABLE_CATEGORY = "create table " + Category.CATEGORY_TABLE_NAME +
            "(" + Category.CATEGORY_COLUMN_ID + " integer primary key autoincrement, " +
            Category.CATEGORY_COLUMN_NAME +" text," +
            Category.CATEGORY_COLUMN_DESCRIPTION + " text," +
            Category.CATEGORY_COLUMN_OPERATION + " integer)";

    private static final String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS " + Category.CATEGORY_TABLE_NAME;

    //Most common budget categories
    /**
     * HOUSING is generally the largest item in a family budget. Depending on the type and cost of your home,
     * you may be spending a sizable percentage of your income on paying for this living space.
     * It is to your advantage to create a basic budget before selecting your living quarters.
     * By doing this, you can allow your budget numbers to influence your housing decision and
     * decrease the likelihood of committing to a property that continually pushes your budget
     * into the red.
     */
    private static final String DB_CAT_HOUSING_NAME = "HOUSING";
    private static final String DB_CAT_HOUSING_DESCRIPTION = "The sum of the monthly mortgage payment, hazard insurance,property taxes, and homeowner association fees.\n" +
            "\n" +
            "Housing expense is sometimes referred to as PITI, standing for principal, interest, taxes, and insurance.";
    private static final Integer DB_CAT_HOUSING_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_HOUSING = new Category(DB_CAT_HOUSING_NAME,DB_CAT_HOUSING_DESCRIPTION,DB_CAT_HOUSING_OPERATION);

    /**
     FOOD
     You can’t survive without it. Food needs to be very high on your prioritized budget list.

     Groceries
     Restaurants
     Pet Food/Treats

     */
    private static final String DB_CAT_FOOD_NAME = "FOOD";
    private static final String DB_CAT_FOOD_DESCRIPTION = "What do you spend on food";
    private static final Integer DB_CAT_FOOD_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_FOOD = new Category(DB_CAT_FOOD_NAME,DB_CAT_FOOD_DESCRIPTION,DB_CAT_FOOD_OPERATION);

    /**
     TRANSPORTATION
     Transportation is important. But you’re going to need more than gasoline and oil changes . . . .

     Fuel
     Tires
     Oil Changes
     Maintenance
     Parking Fees
     Repairs
     DMV Fees
     Vehicle Replacement – This should be for reasonable vehicle replacements; fancy add-ons should come from your Fun Money category.

     */
    private static final String DB_CAT_TRANSPORTATION_NAME = "TRANSPORTATION";
    private static final String DB_CAT_TRANSPORTATION_DESCRIPTION = "    Fuel\n" +
            "     Tires\n" +
            "     Oil Changes\n" +
            "     Maintenance\n" +
            "     Parking Fees\n" +
            "     Repairs\n" +
            "     DMV Fees\n" +
            "     Vehicle Replacement – This should be for reasonable vehicle replacements; fancy add-ons should come from your Fun Money category.\n";
    private static final Integer DB_CAT_TRANSPORTATION_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_TRANSPORTATION = new Category(DB_CAT_TRANSPORTATION_NAME,DB_CAT_TRANSPORTATION_DESCRIPTION,DB_CAT_TRANSPORTATION_OPERATION);

    /**
     Education
     */
    private static final String DB_CAT_EDUCATION_NAME = "EDUCATION";
    private static final String DB_CAT_EDUCATION_DESCRIPTION = "What do you spend with Education";
    private static final Integer DB_CAT_EDUCATION_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_EDUCATION = new Category(DB_CAT_EDUCATION_NAME,DB_CAT_EDUCATION_DESCRIPTION,DB_CAT_EDUCATION_OPERATION);

    /**
     Utilities

     Electricity
     Water
     Heating
     Garbage
     Phones
     Cable
     Internet

     */
    private static final String DB_CAT_UTILITIES_NAME = "UTILITIES";
    private static final String DB_CAT_UTILITIES_DESCRIPTION = "\n" +
            "    Electricity\n" +
            "    Water\n" +
            "    Heating\n" +
            "    Garbage\n" +
            "    Phones\n" +
            "    Cable\n" +
            "    Internet\n";
    private static final Integer DB_CAT_UTILITIES_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_UTILITIES = new Category(DB_CAT_EDUCATION_NAME,DB_CAT_EDUCATION_DESCRIPTION,DB_CAT_EDUCATION_OPERATION);

    /**
     Clothing

     Wear something. It’s kind of socially important.
     But don’t go overboard here with all the latest trends – that’s for your Fun Money category to manage.

     Children’s Clothing
     Adult’s Clothing

     */
    private static final String DB_CAT_CLOTHING_NAME = "CLOTHING";
    private static final String DB_CAT_CLOTHING_DESCRIPTION = "Children’s Clothing\n" +
            "Adult’s Clothing";
    private static final Integer DB_CAT_CLOTHING_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_CLOTHING = new Category(DB_CAT_CLOTHING_NAME,DB_CAT_CLOTHING_DESCRIPTION,DB_CAT_CLOTHING_OPERATION);

    /**
     Medical

     Even if you are healthy and don’t have many medical expenditures, make sure you consider these categories.

     Primary Care
     Dental Care
     Specialty Care – Think orthodontics, optometrists, etc.
     Medications
     Medical Devices


     */
    private static final String DB_CAT_MEDICAL_NAME = "MEDICAL";
    private static final String DB_CAT_MEDICAL_DESCRIPTION = "Primary Care\n" +
            "     Dental Care\n" +
            "     Specialty Care – Think orthodontics, optometrists, etc.\n" +
            "     Medications\n" +
            "     Medical Devices";
    private static final Integer DB_CAT_MEDICAL_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_MEDICAL = new Category(DB_CAT_MEDICAL_NAME,DB_CAT_MEDICAL_DESCRIPTION,DB_CAT_MEDICAL_OPERATION);

    /**
     *
     Insurance

     The goal of insurance is to pay for expenses you can’t afford but desperately need to cover. Raise your deductibles to save some money if you have a fully funded emergency fund.

     Health Insurance
     Homeowner’s Insurance
     Renter’s Insurance
     Auto Insurance
     Life Insurance
     Disability Insurance
     Identity Theft Protection
     Longterm Care Insurance

     */
    private static final String DB_CAT_INSURANCE_NAME = "INSURANCE";
    private static final String DB_CAT_INSURANCE_DESCRIPTION = "     Health Insurance\n" +
            "     Homeowner’s Insurance\n" +
            "     Renter’s Insurance\n" +
            "     Auto Insurance\n" +
            "     Life Insurance\n" +
            "     Disability Insurance\n" +
            "     Identity Theft Protection\n" +
            "     Longterm Care Insurance";
    private static final Integer DB_CAT_INSURANCE_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_INSURANCE = new Category(DB_CAT_INSURANCE_NAME,DB_CAT_INSURANCE_DESCRIPTION,DB_CAT_INSURANCE_OPERATION);

    /**
     *
     * retirement
     It’s important to have a retirement plan you can depend on. With Social Security wavering, who knows if you’ll be able to depend on the government for assistance. It is often recommended to save and invest for retirement as a high priority in your prioritized budget.

     Financial Planning
     Investing

     */
    private static final String DB_CAT_RETIREMENT_NAME = "RETIREMENT";
    private static final String DB_CAT_RETIREMENT_DESCRIPTION = "     Health Insurance\n" +
            "     Homeowner’s Insurance\n" +
            "     Renter’s Insurance\n" +
            "     Auto Insurance\n" +
            "     Life Insurance\n" +
            "     Disability Insurance\n" +
            "     Identity Theft Protection\n" +
            "     Longterm Care Insurance";
    private static final Integer DB_CAT_RETIREMENT_OPERATION = OPERATION.DEBIT;
    private static final Category DB_CAT_RETIREMENT = new Category(DB_CAT_RETIREMENT_NAME,DB_CAT_RETIREMENT_DESCRIPTION,DB_CAT_RETIREMENT_OPERATION);

    /**
     *
     Salary/Wages
     */
    private static final String DB_CAT_SALARY_NAME = "SALARY";
    private static final String DB_CAT_SALARY_DESCRIPTION = "Salary/Wages";
    private static final Integer DB_CAT_SALARY_OPERATION = OPERATION.CREDIT;
    private static final Category DB_CAT_SALARY = new Category(DB_CAT_SALARY_NAME,DB_CAT_SALARY_DESCRIPTION,DB_CAT_SALARY_OPERATION);

    /**
     *
     Tax Refunds
     */
    private static final String DB_CAT_TAX_REFUNDS_NAME = "TAX_REFUNDS";
    private static final String DB_CAT_TAX_REFUNDS_DESCRIPTION = "Tax Refunds";
    private static final Integer DB_CAT_TAX_REFUNDS_OPERATION = OPERATION.CREDIT;
    private static final Category DB_CAT_TAX_REFUNDS = new Category(DB_CAT_TAX_REFUNDS_NAME,DB_CAT_TAX_REFUNDS_DESCRIPTION,DB_CAT_TAX_REFUNDS_OPERATION);



    /**
     *
     Investment Income (IRA or 401k distributions)
     Interests
     */
    private static final String DB_CAT_INVESTMENTS_NAME = "INVESTMENTS";
    private static final String DB_CAT_INVESTMENTS_DESCRIPTION = "Investment Income (IRA or 401k distributions)\n" +
            "Interests";
    private static final Integer DB_CAT_INVESTMENTS_OPERATION = OPERATION.CREDIT;
    private static final Category DB_CAT_INVESTMENTS = new Category(DB_CAT_INVESTMENTS_NAME,DB_CAT_INVESTMENTS_DESCRIPTION,DB_CAT_INVESTMENTS_OPERATION);


    public DBHelper(Context context) {

        super(context, DATABASE_NAME , null, 1);
        ArrayList<Category> myDefaultCategories = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //* Table Category*/
        db.execSQL(CREATE_TABLE_CATEGORY);
        insertDefaultCategories(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Table Category
        db.execSQL(DROP_TABLE_CATEGORY);
        insertDefaultCategories(db);
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

        insertCategory(theDataBase,DB_CAT_HOUSING);
        insertCategory(theDataBase,DB_CAT_FOOD);
        insertCategory(theDataBase,DB_CAT_TRANSPORTATION);
        insertCategory(theDataBase,DB_CAT_EDUCATION);
        insertCategory(theDataBase,DB_CAT_UTILITIES);
        insertCategory(theDataBase,DB_CAT_CLOTHING);
        insertCategory(theDataBase,DB_CAT_MEDICAL);
        insertCategory(theDataBase,DB_CAT_INSURANCE);
        insertCategory(theDataBase,DB_CAT_RETIREMENT);
        insertCategory(theDataBase,DB_CAT_SALARY);
        insertCategory(theDataBase,DB_CAT_TAX_REFUNDS);
        insertCategory(theDataBase,DB_CAT_INVESTMENTS);

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

}
