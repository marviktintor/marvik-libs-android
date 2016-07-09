package com.marvik.libs.android.utils.date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarUtils {
    public static String getMonth(int monthOfYear) {
        switch (monthOfYear) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";

            default:
                return "Invalid";
        }
    }

    /**
     * Get the time in milliseconds of this time
     *
     * @param pattern date format
     * @param time    time to convert
     * @return timeInMillis
     */
    public static long parseTime(String pattern, String time) {
        try {
            return new SimpleDateFormat(pattern).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Formats the passed milliseconds to the defined pattern
     *
     * @param pattern      time/date pattern
     * @param milliSeconds milliseconds
     * @return formatted time/date
     */
    public static String getFormattedDate(String pattern, long milliSeconds) {
        return new SimpleDateFormat(pattern, Locale.ENGLISH).format(new Date(milliSeconds));
    }

    /**
     * Performs a quick time comparison to determine if the passed timestamp is an incoming time stamp
     *
     * @param timeInMillis time stamp to test
     * @return dateIsIncoming
     */
    public static boolean isIncomingDate(long timeInMillis) {
        return timeInMillis > System.currentTimeMillis();
    }
}
