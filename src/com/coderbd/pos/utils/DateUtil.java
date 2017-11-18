/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Biswajit Debnath
 */
public class DateUtil {

    public static boolean isSameDate(Date date1, Date date2) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int year1 = cal.get(Calendar.YEAR);
        int month1 = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(date2);

        int year2 = cal.get(Calendar.YEAR);
        int month2 = cal.get(Calendar.MONTH);
        int day2 = cal.get(Calendar.DAY_OF_MONTH);

        if (day1 == day2 && month1 == month2 && year1 == year2) {
            return true;
        }
        return false;
    }

    public static boolean isDateInRange(Date fromDate, Date date, Date toDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);

        int fromYear = cal.get(Calendar.YEAR);
        int fromMonth = cal.get(Calendar.MONTH);
        int fromDay = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(toDate);

        int toYear = cal.get(Calendar.YEAR);
        int toMonth = cal.get(Calendar.MONTH);
        int toDay = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(date);

        int curYear = cal.get(Calendar.YEAR);
        int curMonth = cal.get(Calendar.MONTH);
        int curDay = cal.get(Calendar.DAY_OF_MONTH);

        if (Math.min(fromYear, toYear) > curYear || Math.max(fromYear, toYear) < curYear) {
            return false;
        }
        if (Math.min(fromMonth, toMonth) > curMonth || Math.max(fromMonth, toMonth) < curMonth) {
            return false;
        }
        if (Math.min(fromDay, toDay) > curDay || Math.max(fromDay, toDay) < curDay) {
            return false;
        }

        return true;
    }

    public static Timestamp convertDateToTimestamp(Date date) {
        if (date == null) {
            date = new Date();
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    public static Date convertTimestampToDate(Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        Date date = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    public static String getStandardDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-YY");
        return simpleDateFormat.format(date);
    }

    public static String getStandardDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

}
