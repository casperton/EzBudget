package com.cs246.EzBudget;

import android.icu.util.Calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handles Dates in the correct Format
 */
public class DateHandler {

    public static final String DATABASE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATABASE_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "dd/MM/YYYY";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String RECURRENT_DATE_FORMAT = "dd";
    private static final String YEAR_FORMAT = "yyyy";
    private static final String MONTH_FORMAT = "MM";
    private static final String DAY_FORMAT = "dd";
    //public static final String DATE_FORMAT = "MM-dd-yyyy";
    //public static final String TIMESTAMP_FORMAT = "MM-dd-yyyy HH:mm:ss";

    /**
     * Get The Time and Date in the DataBase Format
     * @return
     */
    static public String getNowDatabaseFormat() {
        //NOW
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DATABASE_TIMESTAMP_FORMAT);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;

    }

    /**
     * Get Only Date in the Database Date Format
     * @return the String with the Date Formated
     */
    static public String getNowDateDatabase() {
        //NOW
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DATABASE_DATE_FORMAT);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;

    }
    public static String getActualMonth(){
       String theMonth="";

        DateFormat df = new SimpleDateFormat(MONTH_FORMAT);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        theMonth = df.format(today);
       return theMonth;
    }
    public static String getActualYear(){
        String theYear="";

        DateFormat df = new SimpleDateFormat(YEAR_FORMAT);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        theYear = df.format(today);
        return theYear;
    }
    public static String getActualDay(){
        String theDay="";

        DateFormat df = new SimpleDateFormat(DAY_FORMAT);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        theDay = df.format(today);
        return theDay;
    }

    /**
     * Get Only Date in the Human Readable Date Format
     * @return the String with the Date Formated
     */
    static public String getNowDateHuman() {
        //NOW
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;

    }

    public static Date getDateFromLastMonth(int type) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT);
        java.util.Calendar aCalendar = java.util.Calendar.getInstance();
        String firstDateOfPreviousMonth_str;
        String lastDateOfPreviousMonth_str;
        // add -1 month to current month
        aCalendar.add(java.util.Calendar.MONTH,-1);
        // set DATE to 1, so first date of previous month
        aCalendar.set(java.util.Calendar.DATE, 1);

        Date firstDateOfPreviousMonth = aCalendar.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        firstDateOfPreviousMonth_str = df.format(firstDateOfPreviousMonth);
        // set actual maximum date of previous month
        aCalendar.set(java.util.Calendar.DATE, aCalendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        lastDateOfPreviousMonth_str = df.format(lastDateOfPreviousMonth);
        if(type == 1) return firstDateOfPreviousMonth;
        else return lastDateOfPreviousMonth;
    }

    public static Date getDateFromNextMonth(int type) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT);
        java.util.Calendar aCalendar = java.util.Calendar.getInstance();
        String firstDateOfPreviousMonth_str;
        String lastDateOfPreviousMonth_str;
        // add -1 month to current month
        aCalendar.add(java.util.Calendar.MONTH,1);
        // set DATE to 1, so first date of previous month
        aCalendar.set(java.util.Calendar.DATE, 1);

        Date firstDateOfPreviousMonth = aCalendar.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        firstDateOfPreviousMonth_str = df.format(firstDateOfPreviousMonth);
        // set actual maximum date of previous month
        aCalendar.set(java.util.Calendar.DATE, aCalendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        lastDateOfPreviousMonth_str = df.format(lastDateOfPreviousMonth);
        if(type == 1) return firstDateOfPreviousMonth;
        else return lastDateOfPreviousMonth;
    }

    public static Date getDateFromThisMonth(int type) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT);
        java.util.Calendar aCalendar = java.util.Calendar.getInstance();
        String firstDateOfPreviousMonth_str;
        String lastDateOfPreviousMonth_str;
        // add -1 month to current month
        //aCalendar.add(Calendar.MONTH,1);
        // set DATE to 1, so first date of previous month
        aCalendar.set(java.util.Calendar.DATE, 1);

        Date firstDateOfPreviousMonth = aCalendar.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        firstDateOfPreviousMonth_str = df.format(firstDateOfPreviousMonth);
        // set actual maximum date of previous month
        aCalendar.set(java.util.Calendar.DATE, aCalendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        lastDateOfPreviousMonth_str = df.format(lastDateOfPreviousMonth);
        if(type == 1) return firstDateOfPreviousMonth;
        else return lastDateOfPreviousMonth;
    }

}
