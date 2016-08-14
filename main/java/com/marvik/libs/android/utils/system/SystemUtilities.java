package com.marvik.libs.android.utils.system;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

import com.marvik.libs.android.R;

/**
 * SystemUtilities
 * Utilities of android core functions
 * Created by victor on 7/20/2016.
 */
public class SystemUtilities {

    private Context context;

    public SystemUtilities(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * Sends a notification
     *
     * @param notificationId
     * @param title
     * @param message
     */
    public void sendNotification(int notificationId, String title, String message) {
        sendNotification(notificationId, R.mipmap.ic_launcher, title, message);
    }

    /**
     * Sends a notification
     *
     * @param notificationId
     * @param iconId
     * @param title
     * @param message
     */
    public void sendNotification(int notificationId, int iconId, String title, String message) {
        sendNotification(notificationId, iconId, iconId, title, message);
    }

    /**
     * Sends a notification
     *
     * @param notificationId
     * @param smallIcon
     * @param largeIcon
     * @param title
     * @param message
     */
    public void sendNotification(int notificationId, int smallIcon, int largeIcon, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getContext());
        notificationCompat.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationCompat.build();
        notificationCompat.mContentTitle = title;
        notificationCompat.mContentText = message;
        notificationCompat.setSmallIcon(smallIcon);
        notificationCompat.setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(), largeIcon));
        notificationManager.notify(notificationId, notificationCompat.build());
    }

    /**
     * Vibrate
     *
     * @param pattern
     * @param repeat
     */
    public void vibrate(long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeat);
    }

    /**
     * Vibarate
     *
     * @param pattern
     */
    public void vibrate(long[] pattern) {
        vibrate(pattern, 0);
    }
}
