package com.marvik.libs.android.vendor.payments;

import com.marvik.libs.android.io.handler.FilesHandler;
import com.marvik.libs.android.vendor.payments.consts.Payments;
import com.marvik.libs.android.vendor.payments.deadlines.PaymentDeadlines;

import java.io.File;
import java.io.IOException;

/**
 * PaymentDeadlines
 * Contains method to see if an app is paid for
 * Created by victor on 7/31/2016.
 */
public class AppPayments {
    static private FilesHandler filesHandler;
    private static AppPayments appPayments = new AppPayments();

    public static AppPayments getInstance() {
        filesHandler = new FilesHandler();
        return appPayments;
    }

    public static FilesHandler getFilesHandler() {
        return filesHandler;
    }

    /**
     * Creates a file that contains the timestamp of the first time this app is installed
     *
     * @param firstRunMillis system milli of the first time this app is run
     * @throws IOException
     */
    public static void createPaymentTracker(long firstRunMillis) throws IOException {
        if (!getFilesHandler().isExists(new File(Payments.FIRST_RUN_TRACKER_FILE)))
            getFilesHandler().writeStream(new File(Payments.FIRST_RUN_TRACKER_FILE), String.valueOf(firstRunMillis));
    }

    /**
     * Returns true if app free use period is expired
     *
     * @param paymentDeadlines
     * @return
     * @throws IOException
     */
    public boolean isOverdue(PaymentDeadlines paymentDeadlines) throws IOException {
        long currentTime = System.currentTimeMillis();
        long allowedLifetime = calculateTimeMillis(paymentDeadlines);
        String firstRunTime = getFilesHandler().readFile(Payments.FIRST_RUN_TRACKER_FILE);

        long firstRunMillis = firstRunTime.equals("") ? System.currentTimeMillis() : Long.parseLong(firstRunTime.trim());
        boolean isOverdue = ((firstRunMillis + allowedLifetime) >= currentTime);
        return isOverdue;
    }

    /**
     * Calculates the time period millis
     *
     * @param paymentDeadlines
     * @return
     */
    private long calculateTimeMillis(PaymentDeadlines paymentDeadlines) {
        int millis = 1;
        int second = 1000 * millis;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        int week = day * 7;
        int month = week * 4;
        int year = month * 12;
        switch (paymentDeadlines) {
            case NOW:
                return millis;
            case ONE_HOUR:
                return hour;
            case TWO_HOURS:
                return (2 * hour);
            case SIX_HOURS:
                return (6 * hour);
            case HALF_DAY:
                return (12 * hour);
            case DAY:
                return day;
            case WEEK:
                return week;
            case FORTNIGHTS:
                return week * 2;
            case HALF_YEAR:
                return year / 2;
            case YEAR:
                return year;
        }
        return 0;
    }
}
