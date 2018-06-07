package com.thePerfectDay.brown.test;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by brown on 5/27/2018.
 */

public class lbcDateHandler {

    public Date dateFromString(String dateString)
    {
        Date returnDate = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy hh:mm:ss");

        try {
            returnDate = dateFormatter.parse(dateString);
        }
        catch (Exception e)
        {
            Log.e("E0001", "Error - database has incorrect format of date");
        }

        return returnDate;
    }

    public Calendar calendarFromString(String dateString) {
        Date myDate = this.dateFromString(dateString);
        Calendar myCalendar = Calendar.getInstance();

        if (myDate != null) {
            myCalendar.setTime(myDate);
            return myCalendar;
        }

        return  null;
    }

    public boolean isSameDay(Calendar dayA, Calendar dayB) {

        boolean month = false;
        boolean day = false;
        boolean year = false;

        int monthDbInt = dayA.get(Calendar.MONTH);
        int dayDbInt = dayA.get(Calendar.DAY_OF_MONTH);
        int yearDbInt = dayA.get(Calendar.YEAR);

        int monthTodayInt = dayB.get(Calendar.MONTH);
        int dayTodayInt = dayB.get(Calendar.DAY_OF_MONTH);
        int yearTodayInt = dayB.get(Calendar.YEAR);

        if (monthDbInt == monthTodayInt) month = true;
        if (dayDbInt == dayTodayInt) day = true;
        if (yearDbInt == yearTodayInt) year = true;

        if (month && day && year) {
            // match
            return true;
        }

        return  false;
    }

    public String getDayString(String dateString){
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

        Date theDate = null;
        Calendar dbDate = Calendar.getInstance();

        try {
            theDate = format.parse(dateString);
            dbDate.setTime(theDate);
        }
        catch (Exception e) {
            // (!) error handling code
        }

        int dayInt = dbDate.get(Calendar.DAY_OF_MONTH);

        return new String(Integer.toString(dayInt));
    }

    public String getMonthString(String dateString){
        Date myDate = this.dateFromString(dateString);

        if (myDate != null)
        {
            // convert to Calendar so you can pull month
            Calendar calendarDate = Calendar.getInstance();
            calendarDate.setTime(myDate);

            String monthString = new String("error");

            int month = calendarDate.get(Calendar.MONTH);
            switch(++month) {
                case 1:
                    monthString = "Jan";
                    break;
                case 2:
                    monthString = "Feb";
                    break;
                case 3:
                    monthString = "Mar";
                    break;
                case 4:
                    monthString = "Apr";
                    break;
                case 5:
                    monthString = "May";
                    break;
                case 6:
                    monthString = "Jun";
                    break;
                case 7:
                    monthString = "Jul";
                    break;
                case 8:
                    monthString = "Aug";
                    break;
                case 9:
                    monthString = "Sep";
                    break;
                case 10:
                    monthString = "Oct";
                    break;
                case 11:
                    monthString = "Nov";
                    break;
                case 12:
                    monthString = "Dec";
                    break;
            }

            return monthString;
        }
        else
        {
            return "error";
        }
    }
}
