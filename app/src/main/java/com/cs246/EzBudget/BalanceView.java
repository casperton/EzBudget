package com.cs246.EzBudget;



import com.cs246.EzBudget.Database.DBHelper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BalanceView{

    /**
     * FIELDS FOR DATABASE DESCRIPTION
     */
    public static final String BALANCEVIEW_TABLE_NAME = "balanceView";
    public static final String BALANCEVIEW_COLUMN_ID = "idBalView";
    public static final String BALANCEVIEW_COLUMN_INI_DATE = "initialDate";
    public static final String BALANCEVIEW_COLUMN_FINAL_DATE = "finalDate";
    public static final String BALANCEVIEW_COLUMN_KEY_DATE = "keyDate";
    public static final String BALANCEVIEW_COLUMN_DESCRIPTION = "description";
    public static final String BALANCEVIEW_COLUMN_TITLE = "title";
    public static final String BALANCEVIEW_COLUMN_END_BALANCE = "finalBalance";
    public static final String BALANCEVIEW_COLUMN_IS_CURRENT = "isCurrent";

    //LAST MONTH






    public static Date getDateFromLastMonth(int type) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        Calendar aCalendar = Calendar.getInstance();
        String firstDateOfPreviousMonth_str;
        String lastDateOfPreviousMonth_str;
        // add -1 month to current month
        aCalendar.add(Calendar.MONTH,-1);
        // set DATE to 1, so first date of previous month
        aCalendar.set(Calendar.DATE, 1);

        Date firstDateOfPreviousMonth = aCalendar.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        firstDateOfPreviousMonth_str = df.format(firstDateOfPreviousMonth);
        // set actual maximum date of previous month
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        lastDateOfPreviousMonth_str = df.format(lastDateOfPreviousMonth);
        if(type == 1) return firstDateOfPreviousMonth;
        else return lastDateOfPreviousMonth;
    }

    public static Date getDateFromNextMonth(int type) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        Calendar aCalendar = Calendar.getInstance();
        String firstDateOfPreviousMonth_str;
        String lastDateOfPreviousMonth_str;
        // add -1 month to current month
        aCalendar.add(Calendar.MONTH,1);
        // set DATE to 1, so first date of previous month
        aCalendar.set(Calendar.DATE, 1);

        Date firstDateOfPreviousMonth = aCalendar.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        firstDateOfPreviousMonth_str = df.format(firstDateOfPreviousMonth);
        // set actual maximum date of previous month
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        lastDateOfPreviousMonth_str = df.format(lastDateOfPreviousMonth);
        if(type == 1) return firstDateOfPreviousMonth;
        else return lastDateOfPreviousMonth;
    }

    public static Date getDateFromThisMonth(int type) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        Calendar aCalendar = Calendar.getInstance();
        String firstDateOfPreviousMonth_str;
        String lastDateOfPreviousMonth_str;
        // add -1 month to current month
        //aCalendar.add(Calendar.MONTH,1);
        // set DATE to 1, so first date of previous month
        aCalendar.set(Calendar.DATE, 1);

        Date firstDateOfPreviousMonth = aCalendar.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        firstDateOfPreviousMonth_str = df.format(firstDateOfPreviousMonth);
        // set actual maximum date of previous month
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        lastDateOfPreviousMonth_str = df.format(lastDateOfPreviousMonth);
        if(type == 1) return firstDateOfPreviousMonth;
        else return lastDateOfPreviousMonth;
    }


    public static final String DB_BALVIEW_LAST_MONTH_TITLE = "LAST MONTH";
    public static final String DB_BALVIEW_LAST_MONTH_DESCRIPTION = "The Balance for the Last Month.";
    public static final Date DB_BALVIEW_LAST_MONTH_INI_DATE = getDateFromLastMonth(1);
    public static final Date DB_BALVIEW_LAST_MONTH_END_DATE = getDateFromLastMonth(2);
    public static final Date DB_BALVIEW_LAST_MONTH_KEY_DATE = DB_BALVIEW_LAST_MONTH_END_DATE;
    public static final BalanceView DB_BALVIEW_LAST_MONTH = new BalanceView(DB_BALVIEW_LAST_MONTH_TITLE,
            DB_BALVIEW_LAST_MONTH_INI_DATE,
            DB_BALVIEW_LAST_MONTH_END_DATE,
            DB_BALVIEW_LAST_MONTH_KEY_DATE,
            DB_BALVIEW_LAST_MONTH_DESCRIPTION,false);

    // CURRENT MONTH
    public static final String DB_BALVIEW_THIS_MONTH_TITLE = "CURRENT MONTH";
    public static final String DB_BALVIEW_THIS_MONTH_DESCRIPTION = "The Balance for the Current Month" ;
    public static final Date DB_BALVIEW_THIS_MONTH_INI_DATE = getDateFromThisMonth(1);
    public static final Date DB_BALVIEW_THIS_MONTH_END_DATE = getDateFromThisMonth(2);
    public static final Date DB_BALVIEW_THIS_MONTH_KEY_DATE = DB_BALVIEW_THIS_MONTH_END_DATE;
    public static final BalanceView DB_BALVIEW_THIS_MONTH = new BalanceView(DB_BALVIEW_THIS_MONTH_TITLE,
            DB_BALVIEW_THIS_MONTH_INI_DATE,
            DB_BALVIEW_THIS_MONTH_END_DATE,
            DB_BALVIEW_THIS_MONTH_KEY_DATE,
            DB_BALVIEW_THIS_MONTH_DESCRIPTION,
            true);

    //NEXT MONTH
    public static final String DB_BALVIEW_NEXT_MONTH_TITLE = "NEXT MONTH";
    public static final String DB_BALVIEW_NEXT_MONTH_DESCRIPTION = "The Balance for the Next Month.";
    public static final Date DB_BALVIEW_NEXT_MONTH_INI_DATE = getDateFromNextMonth(1);
    public static final Date DB_BALVIEW_NEXT_MONTH_END_DATE = getDateFromNextMonth(2);
    public static final Date DB_BALVIEW_NEXT_MONTH_KEY_DATE = DB_BALVIEW_NEXT_MONTH_END_DATE;
    public static final BalanceView DB_BALVIEW_NEXT_MONTH = new BalanceView(DB_BALVIEW_NEXT_MONTH_TITLE,
            DB_BALVIEW_NEXT_MONTH_INI_DATE,
            DB_BALVIEW_NEXT_MONTH_END_DATE,
            DB_BALVIEW_NEXT_MONTH_KEY_DATE,
            DB_BALVIEW_NEXT_MONTH_DESCRIPTION,
            false);


    private Long myID;
    private Date myInitialDate;
    private Date myFinalDate;
    private Date myKeyDate;
    private String myDescription;
    private double myEndBalance;
    private Balance myBalance;
    private String myTitle;
    private boolean isCurrent;

    public BalanceView() {

    }
    public BalanceView(String theTitle, Date theInitialDate, Date theFinaDate, Date theKeyDate,String theDescr, boolean theCurrent) {
    this.myTitle = theTitle;
    this.myInitialDate = theInitialDate;
    this.myFinalDate = theFinaDate;
    this.myKeyDate = theKeyDate;
    this.myDescription = theDescr;
    this.isCurrent = theCurrent;
    }

    public Long getID() {
        return myID;
    }

    public void setID(Long myID) {
        this.myID = myID;
    }

    public String getTitle() {
        return myTitle;
    }

    public void setTitle(String myTitle) {
        this.myTitle = myTitle;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent() {
        isCurrent = true;
    }

    public void resetCurrent() {
        isCurrent = false;
    }

    public void setInitialDate(String newVar) {
        if (! newVar.isEmpty()) {
            DateFormat format = new SimpleDateFormat(DBHelper.DATE_FORMAT);
            Date date = null;
            try {
                date = format.parse(newVar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            this.myInitialDate = date;
        }

    }

    public String getInitialDate() {

        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);

        if(this.myInitialDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(this.myInitialDate);
        }
        return reportDate;
    }





    public void setKeyDate(String newVar) {
        if (! newVar.isEmpty()) {
            DateFormat format = new SimpleDateFormat(DBHelper.DATE_FORMAT);
            Date date = null;
            try {
                date = format.parse(newVar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            this.myKeyDate = date;
        }

    }

    public String getKeyDate() {

        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);

        if(this.myKeyDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(this.myKeyDate);
        }
        return reportDate;
    }

    public void setDescription(String theDescription){
        myDescription = theDescription;
    }

    public String getDescription() {
        return myDescription;
    }

    public void setEndBalance(double theBalance) {
        myEndBalance = theBalance;
    }

    public double getEndBalance() {
        return myEndBalance;
    }

    private void setBalance(Balance theBalance) {
        myBalance = theBalance;
    }
    public Balance getBalance() {
        return myBalance;
    }

    public void setFinalDate(String newVar) {
        if (! newVar.isEmpty()) {
            DateFormat format = new SimpleDateFormat(DBHelper.DATE_FORMAT);
            Date date = null;
            try {
                date = format.parse(newVar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            this.myFinalDate = date;
        }

    }
    public String getFinalDate() {

        String reportDate = "";
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT);

        if(this.myFinalDate != null) {
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            reportDate = df.format(this.myFinalDate);
        }
        return reportDate;
    }

}
