package com.cs246.EzBudget;

import android.icu.util.Calendar;

import android.util.Log;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Handles Dates in the correct Format
 */
public class DateHandler {
    public static final Locale DEF_LOCALE = Locale.ENGLISH;
    public static final String DATABASE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATABASE_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
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
        DateFormat df = new SimpleDateFormat(DATABASE_TIMESTAMP_FORMAT,DateHandler.DEF_LOCALE);

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
        DateFormat df = new SimpleDateFormat(DATABASE_DATE_FORMAT,DateHandler.DEF_LOCALE);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;

    }
    public static String getActualMonth(){
       String theMonth="";

        DateFormat df = new SimpleDateFormat(MONTH_FORMAT,DateHandler.DEF_LOCALE);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        theMonth = df.format(today);
       return theMonth;
    }

    public static String getMonth (Date theDate) {
        if (theDate == null) return "";
        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(MONTH_FORMAT,DateHandler.DEF_LOCALE);

        if(theDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(theDate);
        }

        return reportDate;
    }
    public static String getActualYear(){
        String theYear="";

        DateFormat df = new SimpleDateFormat(YEAR_FORMAT,DateHandler.DEF_LOCALE);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        theYear = df.format(today);
        return theYear;
    }
    public static String getActualDay(){
        String theDay="";

        DateFormat df = new SimpleDateFormat(DAY_FORMAT,DateHandler.DEF_LOCALE);

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
        DateFormat df = new SimpleDateFormat(DATE_FORMAT,DateHandler.DEF_LOCALE);

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
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT,DateHandler.DEF_LOCALE);
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
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT,DateHandler.DEF_LOCALE);
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
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT,DateHandler.DEF_LOCALE);
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
     * Return the last day of the passed month
     * @param month the month to get the last doy
     * @return the last day
     */
    public static int getLastDayOfMonth(int month) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT,DateHandler.DEF_LOCALE);
        java.util.Calendar aCalendar = java.util.Calendar.getInstance();
        String firstDateOfPreviousMonth_str;
        String lastDateOfPreviousMonth_str;
        // add -1 month to current month
        //aCalendar.add(Calendar.MONTH,1);
        // set DATE to 1, so first date of previous month
        aCalendar.set(java.util.Calendar.MONTH, month);

        Date firstDateOfTheMonth = aCalendar.getTime();
        // set actual maximum date of previous month
        aCalendar.set(java.util.Calendar.DATE, aCalendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfTheMonth = aCalendar.getTime();
        lastDateOfPreviousMonth_str = df.format(lastDateOfTheMonth);

        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df2 = new SimpleDateFormat(DateHandler.RECURRENT_DATE_FORMAT);


            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
           String reportDate = df2.format(lastDateOfTheMonth);

        int theDay = Integer.parseInt(reportDate);
        return theDay;
    }



    static Date addDays(Date theDate, int theNumberofDaysToAdd){

            Date myDate = theDate;
            // Create an instance of SimpleDateFormat used for formatting
            // the string representation of date (month/day/year)
            DateFormat df = new SimpleDateFormat(DateHandler.DATE_FORMAT,DateHandler.DEF_LOCALE);
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTime(theDate);
           //initialize calendar to the date.
            //aCalendar.set(Calendar.YEAR, 1999);
            //aCalendar.set(Calendar.MONTH, 7);
            //aCalendar.set(Calendar.DAY_OF_MONTH, 26);

            // add -1 month to current month
            aCalendar.add(Calendar.DATE,theNumberofDaysToAdd);
            // set DATE to 1, so first date of previous month


            Date retDate = aCalendar.getTime();

            return retDate;
    }

    /**
     * Convert a Date into a String in the Format (Short_Month_name Year)
     * @param theDate The date to be formated
     * @return the formatet date
     */
    public static String getShortName(Date theDate){
        if (theDate==null) return "";
        //Month name will contain the full month name,,if you want short month name use this

        SimpleDateFormat month_date = new SimpleDateFormat(SHORT_MONTH_NAME+" "+"dd",DateHandler.DEF_LOCALE);
        String month_name = month_date.format(theDate);
        return month_name;
    }

    /**
     * Convert a Date into a String in the Format (Long_Month_name Year)
     * @param theDate The date to be formated
     * @return the formatet date
     */
    public static String getLongName(Date theDate){
        if (theDate==null) return "";
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat(LONG_MONTH_NAME+" "+"dd",DateHandler.DEF_LOCALE);
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
        if (dateStart == null || dateEnd==null) return 0;
        long diff = -1;
        try {

            //time is always 00:00:00 so rounding should help to ignore the missing hour when going from winter to summer time as well as the extra hour in the other direction
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diff;
    }



    public static ArrayList<Date> getListofDates(BalanceData theData){
        ArrayList<Date> theRetArray = new ArrayList<>();

        int theRecPeriod = theData.getRecPeriod();
        int daysToAdd =0;
        if (theRecPeriod == RECURRENT.ONCE) daysToAdd=0;
        else if (theRecPeriod == RECURRENT.WEEKLY) daysToAdd=7;
        else if (theRecPeriod == RECURRENT.BI_WEEKLI) daysToAdd=14;
        else if (theRecPeriod == RECURRENT.MONTHLY) daysToAdd=30;
        else if (theRecPeriod == RECURRENT.UNKNOWN) daysToAdd=0;

        int initialDay = Integer.parseInt(theData.getDueDateDay());


        //period Start Day is alway dia 1
        int iniMonth = getMonthBegin();
        int iniYear = getYearBegin();
        int LastDayOftheIniMonth = getLastDayOfMonth(iniMonth);
        //if the bill Due Date id more than the last day of the month. make the Due Date the last day of the month
        // for instance the bill due date is 30 and we are in February. The last day is 28 or 29
        if (initialDay > LastDayOftheIniMonth) initialDay = LastDayOftheIniMonth;
        Date FirstDate = getDate(initialDay, iniMonth,iniYear);
        theRetArray.add(FirstDate);

        int theEndMonth = getMonthEnd();
        int theEndYear = getYearEnd();
        int LastDayOftheLastMonth = getLastDayOfMonth(theEndMonth);
        Date EndDate = getDate(LastDayOftheLastMonth, theEndMonth,theEndYear);


        Date ActualDate = FirstDate;


        if (daysToAdd > 0) {
            while ( ActualDate.before(EndDate)) {
                //Log.i("SALVADATES","ACTUAL DATE: "+ActualDate.toString());
                Date newDate = addDays(ActualDate, daysToAdd);
                //Log.i("SALVADATES","ADDED DAYS to ActualDate: "+daysToAdd);
                //Log.i("SALVADATES","NEW DATE: "+newDate.toString());
                Date addDate=  new Date();
                addDate = newDate;
                //Log.i("SALVADATES","Added DATE: "+addDate.toString());
                theRetArray.add(addDate);
                ActualDate = new Date();
                 ActualDate = newDate;

            }
        }

        //Log.i("SALVADATES","Date Array Size: "+theRetArray.size());
        return theRetArray;
    }

    /**
     * Get the list of dates that the Balance Data repeats (recurent) inside the period
     * @param theViewIniDate  the initial date of the period
     * @param theViewEndDate the final date of the period
     * @param theData  the BalanceData record to check its recurrence
     * @return the list of recurrent dates this "theData" will repeat itsenf inside he passed period
     */
    public static ArrayList<Date> getListofDates(Date theViewIniDate,Date theViewEndDate,BalanceData theData){
        ArrayList<Date> theRetArray = new ArrayList<>();
        if (theViewIniDate==null || theViewIniDate==null || theData==null) return theRetArray;
        // This is just to test
        //Todo: calculate the dates correctly based on the period and the recurrence od the data
        Integer theDay = Integer.parseInt(theData.getDueDateDay());
        Integer theMonth = Integer.parseInt(getMonth(theViewIniDate))-1;
        //String theMonth = DateHandler.getActualMonth();
        Integer theYear = Integer.parseInt(getActualYear());

        Date date;
        java.util.Calendar aCalendar = java.util.Calendar.getInstance();


        // set DATE to 1, so first date of previous month
        aCalendar.set(java.util.Calendar.DATE, theDay);
        aCalendar.set(java.util.Calendar.MONTH, theMonth);
        aCalendar.set(java.util.Calendar.YEAR, theYear);
        date = aCalendar.getTime();
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

    /**
     * Convert the passed string with a date in the Database format to a Date (java.Calendar)
     * @param theDate the string with the date in the Database Format "yyyy-MM-dd"
     * @return the Date initialized with the correct yyy,MM and dd
     */
    public static Date getDateFromDatabaseString(String theDate){
        if (theDate == null) return null;
        if (theDate.isEmpty()) return null;

        Date retDate= null;
        DateFormat format = new SimpleDateFormat(DATABASE_DATE_FORMAT,DEF_LOCALE);

        try {
            retDate = format.parse(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return retDate;
    }

    /**
     * Convert the passed string with a date in the Human readable format to a Date (java.Calendar)
     * @param theDate the string with the date in the Human readable format
     * @return the Date initialized with the correct yyy,MM and dd
     */
    public static Date getDateFromHumanString(String theDate){
        if (theDate == null) return null;
        if (theDate.isEmpty()) return null;

        Date retDate= null;
        DateFormat format = new SimpleDateFormat(DATE_FORMAT,DEF_LOCALE);

        try {
            retDate = format.parse(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return retDate;
    }
    /**
     * Get the value of the date in the human readable format string
     * usualy to show in textview or editview
     * @return the string of date
     */
    public static String getStrDateInHumanFormat(Date theDate){
        if (theDate == null) return "";

        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DATE_FORMAT,DEF_LOCALE);

        if(theDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(theDate);
        }
        return reportDate;
    }

    /**
     * Get the value of the date in the Database format string
     *  "YYYY-MM-DD"
     * @return the string of date
     */
    public static String getStrDateInDatabaseFormat(Date theDate){
        if (theDate == null) return "";

        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DATABASE_DATE_FORMAT,DEF_LOCALE);

        if(theDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(theDate);
        }
        return reportDate;
    }

    /**
     * initialize a Date with the passed parameters
     * @param theDay the day of the date
     * @param theMonth the month of the date
     * @param theYear the year of the date
     * @return the date initialed with the passed paramentes
     * @warning DO NOT MAKE CHECKS IF THE PARAMENTERS ARE IN THE CORRECT RANGE - TAKE CARE
     */
    public static Date getDate (Integer theDay, Integer theMonth, Integer theYear) {
        if (theDay == null) return null;
        if (theMonth == null) return null;
        if (theYear == null) return null;

        java.util.Calendar aCalendar = java.util.Calendar.getInstance();
        // set DATE to 1, so first date of previous month
        aCalendar.set(java.util.Calendar.DATE, theDay);
        aCalendar.set(java.util.Calendar.MONTH, theMonth);
        aCalendar.set(java.util.Calendar.YEAR, theYear);
        return aCalendar.getTime();

    }


    /**
     * Convert a date from Database Format "yyyy-MM-dd"to a human readable format
     * @param theDate the date to be converted
     * @return the string with the converted date
     */
    public static String convertStrFromDatabaseToHuman(String theDate){
        if(theDate ==null){
            return "";
        }
        if (theDate.isEmpty()){
            return "";
        }
        Date myDate = getDateFromDatabaseString(theDate);
        return getStrDateInHumanFormat(myDate);

    }

    public static int getMonthBegin(){

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthBegin = calendar.get(Calendar.MONTH);
        int yearBegin = calendar.get(Calendar.YEAR);
        int monthEnd = (monthBegin + 2);
        int yearEnd = yearBegin;
        if (monthEnd > 11) {
            monthEnd = (monthEnd - 12);
            yearEnd++;
        }
        return monthBegin;
    }

    public static int getMonthEnd(){


        int monthBegin = getMonthBegin();

        int monthEnd = (monthBegin + 2);
        if (monthEnd > 11) {
            monthEnd = (monthEnd - 12);

        }
        return monthEnd;
    }

    public static int getYearBegin(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int yearBegin = calendar.get(Calendar.YEAR);
        return yearBegin;
    }

    public static int getYearEnd(){
        int monthBegin = getMonthBegin();
        int yearBegin = getYearBegin();
        int monthEnd = (monthBegin + 2);
        int yearEnd = yearBegin;
        if (monthEnd > 11) {
            yearEnd++;
        }
        return  yearEnd;
    }


}
