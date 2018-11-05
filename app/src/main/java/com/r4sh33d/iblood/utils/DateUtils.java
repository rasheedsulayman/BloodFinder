package com.r4sh33d.iblood.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String MYSQL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MYSQL_DATE_ONLY = "yyyy-MM-dd";
    public static final String MONTH_DATE = "MMM dd";
    public static final String FULL_DATE = "dd MMMM yyyy";
    public static final String SHORT_FULL_DATE = "dd MMM yyyy";
    public static final String FULL_DATE_AND_TIME = "dd MMMM yyyy 'at' hh:mma";
    public static final long ONE_DAY_IN_MILLIS = 24*60*60*1000;


    private DateUtils() {
    }

    /**
     * @param date
     * @return
     */
    public static String formatDate(String date) {
        SimpleDateFormat sFrom = new SimpleDateFormat(MYSQL_DATE_ONLY, Locale.getDefault());
        SimpleDateFormat sTo = new SimpleDateFormat(FULL_DATE, Locale.getDefault());
        String result = null;
        try {
            result = sTo.format(sFrom.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param date
     * @param fromFormat
     * @param toFormat
     * @return
     */

    public static String formatDate(String date, String fromFormat, String toFormat) {
        SimpleDateFormat sFrom = new SimpleDateFormat(fromFormat, Locale.getDefault());
        SimpleDateFormat sTo = new SimpleDateFormat(toFormat, Locale.getDefault());
        String result = null;
        try {
            result = sTo.format(sFrom.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String formatDate(Date date, String format) {

        SimpleDateFormat simpleDateFormat = new
                SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(date);
    }


    /**
     * @param time
     * @return
     */
    public static String getRelativeDate(String time) {
        String relativeTime = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MYSQL_FORMAT, Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(time);
            relativeTime = (String) android.text.format.DateUtils.
                    getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(),
                            android.text.format.DateUtils.DAY_IN_MILLIS,
                            android.text.format.DateUtils.FORMAT_ABBREV_ALL);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeTime;
    }

    public static String getRelativeTime(long timeInMillis, boolean showTodayWithTime) {

        Calendar today = Calendar.getInstance();
        Calendar date = Calendar.getInstance();

        date.setTimeInMillis(timeInMillis);

        if (today.get(Calendar.YEAR) == date.get(Calendar.YEAR)) {

            if (today.get(Calendar.MONTH) != date.get(Calendar.MONTH)) {
                return formatDate(date.getTime(), "dd MMM");
            } else if (today.get(Calendar.DAY_OF_MONTH) - date.get(Calendar.DAY_OF_MONTH) > 1) {
                return formatDate(date.getTime(), "dd MMM");
            } else if (today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                if (showTodayWithTime) {
                    return formatDate(date.getTime(),"HH:mm");
                } else {
                    return "Today";
                }
            } else {
                return "Yesterday";
            }
        } else {
            return formatDate(date.getTime(), SHORT_FULL_DATE);
        }
    }

    public static long dateStringToTimeInMillis(String dateString, String format) {
        Date date;
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return date.getTime();
    }

    public static String getRelativeSentFromMessageWithTime(long timeInMillis) {

        String relativeTime = getRelativeTime(timeInMillis,false);
        Date date = new Date(timeInMillis);
        String msg = "%s at %s";

        String clockTime = formatDate(date, "HH:mm");

        switch (relativeTime) {

            case "Today":
                return String.format(msg, "today", clockTime);
            case "Yesterday":
                return String.format(msg, "yesterday", clockTime);
            default:
                return String.format(msg, "on " + relativeTime, clockTime);
        }
    }

    /**
     * @param dateOfBirth
     * @param format      e.g yyyy-mm-dd
     * @return
     */
    public static int getAge(String dateOfBirth, String format) {
        Date birthdate;
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            birthdate = df.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        return getAge(birthdate);
    }

    public static int getAge(Date dateOb) {

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(dateOb);
        today.setTime(Calendar.getInstance().getTime());

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }
}