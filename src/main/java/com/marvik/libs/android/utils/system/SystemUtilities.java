package com.marvik.libs.android.utils.system;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import com.marvik.libs.android.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Get the line 1 number of the active simcard
     *
     * @return line 1 number
     */
    public String getLine1Number() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    /**
     * Open an asset file and return the contents as a string
     * This file is opened using access mode private
     *
     * @param filename the file name
     * @return string file content
     * @throws IOException
     */
    public String openAsset(String filename) throws IOException {
        return openAsset(filename, Context.MODE_PRIVATE);
    }

    /**
     * Open an asset file and return the contents as a string
     * This file is opened using access mode private
     *
     * @param filename   the file name
     * @param accessMode the mode used in accessing the file
     * @return string file content
     * @throws IOException
     */
    public String openAsset(String filename, int accessMode) throws IOException {
        InputStream inputStream = getContext().getAssets().open(filename, accessMode);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String readContents = "";
        while ((readContents = bufferedReader.readLine()) != null) {
            stringBuffer.append(readContents);
        }
        return stringBuffer.toString();
    }

    /**
     * Show a low SDK Error when an unavailable feature is requested
     * The default action is finishing the activity
     *
     * @param activity
     */
    public void showLowSDKVersionError(final Activity activity) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Compatibility failure");
        alert.setMessage("Cannot show required features, your device is running a low Android version");
        alert.setPositiveButton("Exit App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        });
        alert.show();
    }

    /**
     * Send an email message
     *
     * @param text
     * @intentChooserTitle the title of the intent chooser dialog
     */
    public void sendEmail(String text, String intentChooserTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        getContext().startActivity(Intent.createChooser(intent, intentChooserTitle));
    }

    /**
     * Get the phone numbers of the simcards in the device
     *
     * @return
     */
    public List<String> getLineNumbers() {
        List<String> lineNumbers = new ArrayList<>();
        String phoneNumber = getLine1Number();
        if (phoneNumber != null) {
            if (phoneNumber.length() > 0)
                lineNumbers.add(phoneNumber);
        }
        return lineNumbers;
    }

    /**
     * Read all the authenticated accounts and gets all the phone numbers
     *
     * @return
     */
    public List<String> getAccountEmailAddress() {
        List<String> emailAddresses = new ArrayList<>();
        AccountManager accountManager = (AccountManager) getContext().getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            if (account.name.matches(Patterns.EMAIL_ADDRESS.pattern())) {
                emailAddresses.add(account.name);
            }
        }
        return emailAddresses;
    }
}
