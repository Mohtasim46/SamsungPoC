package com.samsungpoc.samsungpocsensormobile;

import java.util.Calendar;

public class DateTimeUtils {

    public static int getMinute(long timeInMilliSeconds) {
        if (timeInMilliSeconds <= 0) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSeconds);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getHour(long timeInMilliSeconds) {
        if (timeInMilliSeconds <= 0) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSeconds);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getDay(long timeInMilliSeconds) {
        if (timeInMilliSeconds <= 0) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSeconds);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(long timeInMilliSeconds) {
        if (timeInMilliSeconds <= 0) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSeconds);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static String getMonthName(long timeInMilliSeconds) {
        if (timeInMilliSeconds <= 0) {
            return "";
        }
        String month;
        switch (getMonth(timeInMilliSeconds)) {
            case 1:
                month = "JAN";
                break;
            case 2:
                month = "FEB";
                break;
            case 3:
                month = "MAR";
                break;
            case 4:
                month = "APR";
                break;
            case 5:
                month = "MAY";
                break;
            case 6:
                month = "JUN";
                break;
            case 7:
                month = "JUL";
                break;
            case 8:
                month = "AUG";
                break;
            case 9:
                month = "SEP";
                break;
            case 10:
                month = "OCT";
                break;
            case 11:
                month = "NOV";
                break;
            case 12:
                month = "DEC";
                break;
            default:
                month = "";
                break;
        }
        return month;
    }

    public static int getYear(long timeInMilliSeconds) {
        if (timeInMilliSeconds <= 0) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSeconds);
        return calendar.get(Calendar.YEAR);
    }
}
