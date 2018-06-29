package com.cs246.EzBudget;

import android.icu.util.Calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handles Dates in the correct Format
 */
public class DateHandler {
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String TIMESTAMP_FORMAT = "dd/MM/yyyy HH:mm:ss";

    static public String getNow() {
        //NOW
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;

    }

    static public String getNowDate() {
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


}
