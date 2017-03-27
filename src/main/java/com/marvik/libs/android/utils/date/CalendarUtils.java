package com.marvik.libs.android.utils.date;


public class CalendarUtils {
     /**
     * Get the time in milliseconds of this time
     *
     * @param pattern date format
     * @param time    time to convert
     * @return timeInMillis
     */
    public static long parseTime(String pattern, String time) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
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
     * Formats the passed milliseconds to the defined pattern
     * The time returned is of the local time zone of this host CPU
     *
     * @param pattern      time/date pattern
     * @param milliSeconds milliseconds
     * @return formatted time/date
     */
    public static String getLocalFormattedDate(String pattern, long milliSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(new Date(milliSeconds));
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

    public static String getFriendlyTime(long baseTime, long targetTime) {
        String tense = baseTime > targetTime ? "ago" : "coming";
        return CalendarUtils.getFormattedDate("hh:mm", (targetTime - baseTime));
    }

    /**
     * Get the system year
     *
     * @return year
     */
    public static int getSystemYear() {
        return Integer.parseInt(getFormattedDate("yyy", System.currentTimeMillis()));
    }

    /**
     * Get the current system month
     *
     * @return month of the year
     */
    public static int getSystemMonth() {
        return Integer.parseInt(getFormattedDate("MM", System.currentTimeMillis()));
    }

    /**
     * Get the current system date
     *
     * @return date of the month
     */
    public static int getSystemDate() {
        return Integer.parseInt(getFormattedDate("dd", System.currentTimeMillis()));
    }

    /**
     * Get the current minute of the system
     *
     * @return minutes of the hour
     */
    public static int getSystemMinute() {
        return Integer.parseInt(getFormattedDate("mm", System.currentTimeMillis()));
    }

    /**
     * Get the current hour of the system
     *
     * @return hour of the day
     */
    public static int getSystemHour() {
        return Integer.parseInt(getFormattedDate("hh", System.currentTimeMillis()));
    }

    /**
     * Get the current hour of the system
     *
     * @return hour of the day
     */
    public static int getSystemHour24() {
        return Integer.parseInt(getFormattedDate("HH", System.currentTimeMillis()));
    }

    /**
     * Returns time in yyy-MM-dd HH:mm:ss a format
     *
     * @param millis
     * @return
     */
    public static String getDefaultDate(long millis) {
        return getFormattedDate("yyy-MM-dd HH:mm:ss Z", millis);
    }
}
