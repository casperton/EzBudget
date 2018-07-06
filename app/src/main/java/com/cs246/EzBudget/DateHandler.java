package com.cs246.EzBudget;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static final String SHORT_MONTH_NAME = "MMM";
    private static final String LONG_MONTH_NAME = "MMMM";
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

    public static String getMonth (Date theDate) {
        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(MONTH_FORMAT);

        if(theDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(theDate);
        }

        return reportDate;
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

    /**
     * Get the first or last day of the previous (the current - 1) month
     * @param type: 1 get the first day
     *              2 get the last day
     * @return the first or last day of the previous (current-1) month
     */
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

    /**
     * Get the first or last day of the next (the current + 1) month
     * @param type: 1 get the first day
     *              2 get the last day
     * @return the first or last day of the next (current+1) month
     */
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

    /**
     * Get the first or last day of this (the current) month
     * @param type: 1 get the first day
     *              2 get the last day
     * @return the first or last day of the current month
     */
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

        Date firstDateOfTheMonth = aCalendar.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        firstDateOfPreviousMonth_str = df.format(firstDateOfTheMonth);
        // set actual maximum date of previous month
        aCalendar.set(java.util.Calendar.DATE, aCalendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfTheMonth = aCalendar.getTime();
        lastDateOfPreviousMonth_str = df.format(lastDateOfTheMonth);
        if(type == 1) return firstDateOfTheMonth;
        else return lastDateOfTheMonth;
    }

    /**
     * Convert a Date into a String in the Format (Short_Month_name Year)
     * @param theDate The date to be formated
     * @return the formatet date
     */
    public static String getShortName(Date theDate){
        //Month name will contain the full month name,,if you want short month name use this

        SimpleDateFormat month_date = new SimpleDateFormat(SHORT_MONTH_NAME+" "+"dd");
        String month_name = month_date.format(theDate);
        return month_name;
    }

    /**
     * Convert a Date into a String in the Format (Long_Month_name Year)
     * @param theDate The date to be formated
     * @return the formatet date
     */
    public static String getLongName(Date theDate){

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat(LONG_MONTH_NAME+" "+"dd");
        String month_name = month_date.format(theDate);
        return month_name;
        //Month name will contain the full month name,
    }

    /**
     * Calculate the number of days between two dates
     * @param dateStart
     * @param dateEnd
     * @return the number of days between the passsed dates
     */
    public static long getDayCount(Date dateStart, Date dateEnd) {
        long diff = -1;
        try {

            //time is always 00:00:00 so rounding should help to ignore the missing hour when going from winter to summer time as well as the extra hour in the other direction
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diff;
    }

    public static ArrayList<Date> getListofDates(Date theViewIniDate,Date theViewEndDate,BalanceData theData){
        ArrayList<Date> theRetArray = new ArrayList<>();
        // This is just to test
        //Todo: calculate the dates correctly based on the period and the recurrence od the data
        Integer theDay = Integer.parseInt(theData.getDueDateDay());
        Integer theMonth = Integer.parseInt(getMonth(theViewIniDate))-1;
        //String theMonth = DateHandler.getActualMonth();
        Integer theYear = Integer.parseInt(getActualYear());

        Date date;
        date = new GregorianCalendar(theYear, theMonth, theDay).getTime();
        theRetArray.add(date);
        /*
        long theQtdDays = DateHandler.getDayCount(theViewIniDate,theViewEndDate);
        int thePeriod = theData.getRecPeriod();
        long theperiodDays=1;

        long numberOfRepetitions=0;

        if (thePeriod == RECURRENT.NO_PERIODIC){
            theperiodDays= 0;
        }else if (thePeriod == RECURRENT.UNKNOWN){
            theperiodDays= 0;
        }else if(thePeriod == RECURRENT.DAILY){
            theperiodDays= 1;
        }else if(thePeriod == RECURRENT.WEEKLY){
            theperiodDays= 7;
        }else if(thePeriod == RECURRENT.BI_WEEKLI){
            theperiodDays= 14;
        }else if(thePeriod == RECURRENT.MONTHLY){
            theperiodDays= 30;
        }
        if (theperiodDays >0) {
            numberOfRepetitions = theQtdDays / theperiodDays;
        }
        //todo: calculate the repetition dates
        if (numberOfRepetitions > 1){


        }
*/
        return theRetArray;
    }
}
