package com.javatest;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/2/4
 * Notes:
 */

public class CalendarDemo {

  public static void main(String[] args) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Calendar calendar =
        Calendar.getInstance(TimeZone.getTimeZone("CST"), Locale.US);
    System.out.println(dateFormat.format(calendar.getTime()));

    calendar.set(Calendar.DATE, 2);
    System.out.println(dateFormat.format(calendar.getTime()));


    // calendar.add(Calendar.DATE, 100);
    // System.out.println(dateFormat.format(calendar.getTime()));

    calendar.set(2017, 1, 1);
    System.out.println(dateFormat.format(calendar.getTime()));

    Date date = new Date("2017-01-08");
    System.out.println(dateFormat.format(date));
    // calendar.set(Calendar.MONTH, 12);
    // System.out.println(dateFormat.format(calendar.getTime()));

    //
    // calendar.set(Calendar.DAY_OF_MONTH, 28);
    // System.out.println(dateFormat.format(calendar.getTime()));
    // Date date = calendar.getTime();
    // System.out.println("" + date.getDate());
    // System.out.println("" + date.getDay());
    // System.out.println(calendar.getTimeInMillis());


    // System.out.println("getActualMaximum-DAY_OF_MONTH:" +
    // calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    // System.out.println("getActualMinimum-DAY_OF_MONTH:" +
    // calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    // System.out.println("getActualMaximum-DAY_OF_WEEK:" +
    // calendar.getActualMaximum(Calendar.DAY_OF_WEEK_IN_MONTH));
    // System.out.println("getActualMinimum-DAY_OF_WEEK:" +
    // calendar.getActualMinimum(Calendar.DAY_OF_WEEK_IN_MONTH));
    // System.out.println("getFirstDayOfWeek:" + calendar.get(Calendar.DAY_OF_WEEK));



  }
}
