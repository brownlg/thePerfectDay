package com.thePerfectDay.brown.test;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by brown on 4/18/2018.
 *
 *
 * Actually should have call this dataRow entry or something
 */


@Entity(tableName = "word_table")
public class userTables {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "word")
    public String mWord;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "numberOfHours")
    public float hours;

    // constructor:
    public userTables(@NonNull String word) {
        this.mWord = word;
        this.hours = 0;        // iniatialize assuming no wasted hours

        //Get or Generate Date
        Date todayDate = new Date();

        //Get an instance of the formatter
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        //If you want to show only the date then you will use
        //DateFormat dateFormat = DateFormat.getDateInstance();

        //Format date
        this.date  = dateFormat.format(todayDate);
    }

    public String getmWord(){
        String entryString;

        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date myDate = null;

        try {
            myDate = format.parse(this.date);
        } catch (Exception e) {
            // (!) exception handle
        }


        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTime(myDate);

        String monthString = new DateFormatSymbols().getMonths()[myCalendar.get(Calendar.MONTH)];
        String dayOfWeekString = new DateFormatSymbols().getWeekdays()[myCalendar.get(Calendar.DAY_OF_WEEK)];

        entryString = dayOfWeekString + " " + monthString + " " + myCalendar.get(Calendar.DAY_OF_MONTH) + " (" + this.hours + ")";

        return entryString;
    }

    /*
    public float getHours() {return this.hours;}
    public void setHours(float setHour) { this.hours = setHour; }
    */
}
