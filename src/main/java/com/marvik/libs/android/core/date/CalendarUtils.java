package com.marvik.libs.android.core.date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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


    /**
     * Does date and time calculations and returns a friendly date
     *
     * @param millis
     * @return
     */
    public String getFriendlyDate(long millis) {

        int milliSeconds = 1;
        int seconds = milliSeconds * 1000;
        int minutes = seconds * 60;
        int hours = minutes * 60;
        int days = hours * 24;
        int weeks = days * 7;
        int months = (weeks * 4) + 2;
        int years = months * 12;

        String friendlyDate = "Unknown";

        long currentMillis = System.currentTimeMillis();

        if (millis == currentMillis) {
            friendlyDate = "now";
        }
        // incoming time
        if (millis > currentMillis) {

            long timeDiff = millis - currentMillis;

            if (timeDiff < years) {
                if (timeDiff / months == 0) {
                    friendlyDate = "This month";
                } else {
                    friendlyDate = "Less than " + timeDiff / months + " months";
                }

            } else {
                friendlyDate = "Less than " + timeDiff / weeks + " weeks";
            }

            if (timeDiff < months) {
                if (timeDiff / weeks == 0) {
                    friendlyDate = "This week";
                } else {
                    friendlyDate = timeDiff / weeks + " weeks";
                }

            } else {
                friendlyDate = "Less than " + timeDiff / weeks + " weeks";
            }
            if (timeDiff < weeks) {

                if (timeDiff / days == 0) {
                    friendlyDate = "Today";
                } else {
                    friendlyDate = timeDiff / days + " days";
                }

            }
            if (timeDiff < days) {
                friendlyDate = timeDiff / hours + " hours";
            }
            if (timeDiff < hours) {
                friendlyDate = timeDiff / minutes + " minutes";
            }
            if (timeDiff < minutes) {
                friendlyDate = timeDiff / seconds + " seconds";
            }
            if (timeDiff < seconds) {
                friendlyDate = "now";
            }

            if (friendlyDate.equals("Unknown")) {
                return getFormattedDate("EEE dd-MMM", millis);
            }

        }

        // old time
        if (millis < currentMillis) {
            long timeDiff = currentMillis - millis;
        }

        return friendlyDate;
    }

    /**
     * Calculates the elapsed time.
     *
     * @param timeMillis
     * @return pretty human readable elapsed time
     */
    public String getPrettyElapsedTime(long timeMillis) {

        long elapsedTime = Math.abs(System.currentTimeMillis() - timeMillis);

        int millis = 1000;
        int second = millis;
        int minute = 60 * second;
        int hour = minute * 60;
        int day = 24 * hour;
        int week = day * 7;
        int month = day * 30;
        int year = month * 12;

        boolean hasSeconds = false;
        boolean hasMinutes = false;
        boolean hasHours = false;
        boolean hasDays = false;
        boolean hasWeeks = false;
        boolean hasMonths = false;
        boolean hasYears = false;

        String duration = "";

        int years = (int) (elapsedTime / (year));

        if (years > 0) {
            elapsedTime = elapsedTime - (year * years);

            duration += years + (years > 1 ? " Years " : " Year ");
        }

        int months = (int) (elapsedTime / (month));
        if (months > 0) {
            elapsedTime = elapsedTime - (month * months);

            duration += months + (months > 1 ? " Months " : " Month ");
        }


        int weeks = (int) (elapsedTime / (week));
        if (weeks > 0) {
            elapsedTime = elapsedTime - (week * weeks);

            duration += weeks + (weeks > 1 ? " Weeks " : " Week ");
        }

        int days = (int) (elapsedTime / (day));
        if (days > 0) {
            elapsedTime = elapsedTime - (day * days);

            duration += days + (days > 1 ? " Days " : " Day ");
        }

        int hours = (int) (elapsedTime / (hour));
        if (hours > 0) {
            elapsedTime = elapsedTime - (hour * hours);

            duration += hours + (hours > 1 ? " Hours " : " Hour ");
        }

        int minutes = (int) (elapsedTime / (minute));
        if (minutes > 0) {
            elapsedTime = elapsedTime - (minute * minutes);

            duration += minutes + (minutes > 1 ? " Minutes " : " Minute ");
        }

        int seconds = (int) (elapsedTime / (second));
        if (seconds > 0) {
            elapsedTime = elapsedTime - (second * seconds);

            duration += seconds + (seconds > 1 ? " Seconds " : " Second ");
        }

        return duration;
    }

}
