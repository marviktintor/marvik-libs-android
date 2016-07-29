package com.marvik.libs.android.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.marvik.libs.android.R;
import com.marvik.libs.android.accounts.UserAccountsManager;
import com.marvik.libs.android.database.utils.DatabaseUtilities;
import com.marvik.libs.android.utils.date.CalendarUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;


public class Utilities {

    private UserAccountsManager userAccountsManager;

    private DatabaseUtilities databaseUtilities;
    private Context context;

    public Utilities(Context context) {

        initAll(context);
    }

    private void initAll(Context context) {

        this.context = context;

        this.userAccountsManager = new UserAccountsManager(getContext());

        this.databaseUtilities = new DatabaseUtilities(getContext());
    }

    public Context getContext() {
        return context;
    }

    public DatabaseUtilities getDatabaseUtilities() {
        return databaseUtilities;
    }

    public UserAccountsManager getUserAccountsManager() {
        return userAccountsManager;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////Start Activity////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean startActivity(String action, Uri uri) {
        try {
            getContext().startActivity(new Intent(action, uri));
            return true;
        } catch (ActivityNotFoundException e) {
            toast("Oops! No installed application can perform the requested action");
            return false;
        }
    }

    public boolean startActivity(Intent intent) {
        getContext().startActivity(intent);
        return true;
    }

    /**
     * Starts an activity
     *
     * @param cls
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(cls, new Bundle(), 0);
    }

    /**
     * Starts an activity using the provided action
     *
     * @param action
     * @param cls
     */
    public void startActivity(String action, Class<? extends Activity> cls) {
        startActivity(action, cls, new Bundle(), 0);
    }

    /**
     * Starts an activity with the passed bundle
     *
     * @param cls
     * @param bundle
     */
    public void startActivity(Class<? extends Activity> cls, @NonNull Bundle bundle) {
        startActivity(cls, bundle, 0);
    }

    /**
     * Starts an activity with the passed flags
     *
     * @param cls
     * @param flags
     */
    public void startActivity(Class<? extends Activity> cls, int flags) {
        startActivity(cls, new Bundle(), flags);
    }

    /**
     * Starts a bundled flagged activity
     *
     * @param cls
     * @param extras
     * @param flags
     */
    public void startActivity(Class<? extends Activity> cls, @NonNull Bundle extras, int flags) {
        startActivity(Intent.ACTION_MAIN, cls, extras, flags);
    }

    /**
     * Starts a flagged activity with a custom action
     *
     * @param action
     * @param cls
     * @param flags
     */
    public void startActivity(String action, Class<? extends Activity> cls, int flags) {
        startActivity(action, cls, new Bundle(), flags);
    }

    /**
     * Starts a bundled flagged activity with a custom action
     *
     * @param action
     * @param cls
     * @param extras
     * @param flags
     */
    public void startActivity(String action, Class<? extends Activity> cls, @NonNull Bundle extras, int flags) {
        getContext().startActivity(new Intent(getContext(), cls).addFlags(flags).putExtras(extras).setAction(action));
    }

    /**
     * Starts a service
     *
     * @param cls
     */
    public void startService(Class<? extends Service> cls) {
        startService(cls, new Bundle(), 0);
    }

    /**
     * Starts a bundled service
     *
     * @param cls
     * @param bundle
     */
    public void startService(Class<? extends Service> cls, @NonNull Bundle bundle) {
        startService(cls, bundle, 0);
    }

    /**
     * Starts a flagged service
     *
     * @param cls
     * @param flags
     */
    public void startService(Class<? extends Service> cls, int flags) {
        startService(cls, new Bundle(), flags);
    }

    /**
     * Starts a bundled flagged service
     *
     * @param cls
     * @param extras
     * @param flags
     */
    public void startService(Class<? extends Service> cls, @NonNull Bundle extras, int flags) {
        getContext().startService(new Intent(getContext(), cls).addFlags(flags).putExtras(extras));
    }

    /**
     * Stops a service
     *
     * @param cls
     */
    public void stopService(Class<? extends Service> cls) {
        stopService(cls, new Bundle(), 0);
    }

    /**
     * Stops a bundled service
     *
     * @param cls
     * @param bundle
     */
    public void stopService(Class<? extends Service> cls, @NonNull Bundle bundle) {
        stopService(cls, bundle, 0);
    }

    /**
     * Stops a flagged service
     *
     * @param cls
     * @param flags
     */
    public void stopService(Class<? extends Service> cls, int flags) {
        stopService(cls, new Bundle(), flags);
    }

    /**
     * Stops a bundled flagged service
     *
     * @param cls
     * @param extras
     * @param flags
     */
    public void stopService(Class<? extends Service> cls, @NonNull Bundle extras, int flags) {
        getContext().stopService(new Intent(getContext(), cls).addFlags(flags).putExtras(extras));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// SEND BROADCASTS //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Sends a action main broadcast that requires empty string permission
     *
     * @param cls
     */
    public void sendBroadcast(Class<? extends Activity> cls) {
        getContext().sendBroadcast(new Intent(getContext(), cls).putExtra(Intent.ACTION_MAIN, Intent.ACTION_MAIN), new String(""));
    }

    /**
     * Broadcasts an action
     *
     * @param action
     */
    public void sendBroadcast(String action) {
        sendBroadcast(action, new Bundle());

    }

    /**
     * Broadcast a bundled action
     *
     * @param action
     * @param extras
     */
    public void sendBroadcast(String action, @NonNull Bundle extras) {
        sendBroadcast(action, extras, null);
    }

    /**
     * Broadcast a flagged action
     *
     * @param action
     * @param flags
     */
    public void sendBroadcast(String action, int flags) {
        sendBroadcast(action, flags, null);
    }

    /**
     * Broadcasat a flagged bundled action
     *
     * @param action
     * @param extras
     * @param flags
     */
    public void sendBroadcast(String action, @NonNull Bundle extras, int flags) {
        sendBroadcast(action, extras, flags, null);
    }

    /**
     * Broadcast a protected action
     *
     * @param action
     * @param permission
     */
    public void sendBroadcast(String action, String permission) {
        sendBroadcast(action, 0, permission);
    }

    /**
     * Broadcast a protected bundled action
     *
     * @param action
     * @param extras
     * @param permission
     */
    public void sendBroadcast(String action, @NonNull Bundle extras, String permission) {
        sendBroadcast(action, extras, 0, permission);
    }

    /**
     * Broadcast a protected flagged action
     *
     * @param action
     * @param flags
     * @param permission
     */
    public void sendBroadcast(String action, int flags, String permission) {
        sendBroadcast(action, new Bundle(), flags, permission);
    }

    /**
     * Broadcast a protected bundled action
     *
     * @param action
     * @param extras
     * @param flags
     * @param permission
     */
    public void sendBroadcast(String action, @NonNull Bundle extras, int flags, String permission) {
        getContext().sendBroadcast(new Intent(action).addFlags(flags).putExtras(extras), permission);
    }

    /**
     * Broadcast a protected intent
     *
     * @param intent
     * @param permission
     */
    public void sendBroadcast(Intent intent, String permission) {
        getContext().sendBroadcast(intent, permission);
    }

    /**
     * Broadcast an intent
     *
     * @param intent
     */
    public void sendBroadcast(Intent intent) {
        getContext().sendBroadcast(intent, null);
    }

    /**
     * Get a string in a text view
     *
     * @param textView
     * @return
     */
    @NonNull
    public String getString(@NonNull TextView textView) {
        return textView.getText().toString();
    }

    /**
     * Shows a toast
     *
     * @param text
     */
    public void toast(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * Shows a custom toast
     *
     * @param text
     * @param duration
     */
    public void toast(String text, int duration) {
        Toast toast = new Toast(getContext());
        TextView view = new TextView(getContext());
        view.setPadding(10, 10, 10, 10);
        view.setBackgroundColor(Color.rgb(180, 180, 180));
        view.setTextColor(Color.BLACK);
        view.setText(text);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    /**
     * Checks for null text views
     *
     * @param textViews textViews to validate
     * @return isEmpty
     */
    public boolean isEmpty(@NonNull TextView[] textViews) {
        boolean isEmpty = false;
        for (TextView textView : textViews) {
            if (textView.getText().length() == 0) {
                textView.setError("Cannot be null");
                textView.setHintTextColor(Color.RED);
                isEmpty = true;
            } else {
                textView.setHintTextColor(Color.GRAY);
            }
        }
        return isEmpty;
    }

    /**
     * Parses to an Array
     *
     * @param set
     * @return
     */
    @NonNull
    public ArrayList<String> formatToArray(@NonNull Set<String> set) {

        ArrayList<String> arrayList = new ArrayList<String>(set.size());
        for (int i = 0; i < set.size(); i++) {
            arrayList.add(set.iterator().next());
        }
        return arrayList;
    }

    /**
     * Hides views
     *
     * @param views
     */
    public void hideViews(@NonNull View[] views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Shows views
     *
     * @param views
     */
    public void showViews(@NonNull View[] views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Reset any Views that extend text view
     *
     * @param t
     * @param <T>
     */
    public <T extends TextView> void resetInputs(@NonNull T[] t) {
        for (T v : t) {
            v.setText("");
        }
    }

    /**
     * Checks whether a device is connected to the internet
     *
     * @param notificationId
     * @param alert          if not connected
     * @return
     */
    public boolean isNetworkConnected(int notificationId, boolean alert) {
        return isNetworkConnected(notificationId, alert, "Network Error", "Action Failed! You are not connected to the Internet");
    }

    /**
     * Checks if device is connected to the internet and alerts if not
     *
     * @param notificationId
     * @param alert
     * @param title
     * @param message
     * @return
     */
    public boolean isNetworkConnected(int notificationId, boolean alert, String title, String message) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean networkConnected = false;

        if (networkInfo != null) {
            networkConnected = networkInfo.isAvailable() && networkInfo.isConnected();
        }

        if (!networkConnected && alert) {
            sendNotification(notificationId, title, message);
        }
        return networkConnected;
    }

    /**
     * Sends a notification
     *
     * @param notificationId
     * @param title
     * @param message
     */
    public void sendNotification(int notificationId, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getContext());
        notificationCompat.build();
        notificationCompat.mContentTitle = title;
        notificationCompat.mContentText = message;
        notificationCompat.setSmallIcon(R.mipmap.ic_launcher);
        notificationCompat.setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher));
        notificationManager.notify(notificationId, notificationCompat.build());
    }

    /**
     * Loads images from file system
     *
     * @param profilePicUrl
     * @return
     * @throws FileNotFoundException
     */
    public Drawable getImageFromStorage(String profilePicUrl) throws FileNotFoundException {

        if (profilePicUrl == null) {
            return null;
        }
        return Drawable.createFromStream(new FileInputStream(new File(profilePicUrl)), profilePicUrl);
    }

    /**
     * Show info dialog
     *
     * @param title
     * @param message
     * @param positiveButtonText
     * @param intent
     */
    public void showInfoDialog(String title, String message, String positiveButtonText, Intent intent) {
        showInfoDialog(title, message, Color.RED, positiveButtonText, intent);
    }

    /**
     * Show info dialog
     *
     * @param title
     * @param message
     * @param textColor
     * @param positiveButtonText
     * @param positiveIntent
     */
    public void showInfoDialog(String title, String message, int textColor, String positiveButtonText, final Intent positiveIntent) {
        AlertDialog.Builder infoDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        infoDialog.setTitle(title);
        TextView mTvMessage = new TextView(getContext());
        mTvMessage.setPadding(16, 16, 16, 16);
        mTvMessage.setTextColor(textColor);
        mTvMessage.setText(message);
        infoDialog.setView(mTvMessage);

        infoDialog.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendBroadcast(positiveIntent);
            }
        });
        infoDialog.show();
        Log.i("DIALOG", "SHOWING");
    }

    /**
     * Get bitmap from file system
     *
     * @param fileUri
     * @return
     */
    public Bitmap getFileBitmap(String fileUri) {


        if (fileUri == null) {
            return null;
        }

        Bitmap bitmap = null;
        try {
            return bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(fileUri)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.error_icon);
    }

    /**
     * Show progress dialog
     *
     * @param title
     * @param message
     * @param cancelable
     * @return
     */
    public ProgressDialog showCustomProgressDialog(String title, String message, boolean cancelable) {
        ProgressDialog mDialog = new ProgressDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(cancelable);
        mDialog.show();
        return mDialog;
    }

    /**
     * Get an asset
     *
     * @param assetPath
     * @return
     * @throws IOException
     */
    public InputStream getAsset(String assetPath) throws IOException {
        return getContext().getResources().getAssets().open(assetPath);
    }

    /**
     * Reads a String stream from the assets
     *
     * @param assetFilePath
     * @return
     */
    public String readAssetsStringStream(String assetFilePath) {
        try {
            InputStream inputStream = getAsset(assetFilePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String readString = "";
            while ((readString = bufferedReader.readLine()) != null) {
                stringBuffer.append(readString);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a resorce
     *
     * @return
     */
    public Resources getResources() {
        return getContext().getResources();
    }

    /**
     * Gets a string resource
     *
     * @param resId
     * @return
     */
    public String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    /**
     * Commit preference
     *
     * @param editor
     * @param preference
     * @param preferenceType
     * @param <T>
     */
    public <T> void commit(SharedPreferences.Editor editor, String preference, T preferenceType) {


        if (preferenceType instanceof Boolean) {
            editor.putBoolean(preference, (Boolean) preferenceType);
        }
        if (preferenceType instanceof Float) {
            editor.putFloat(preference, (Float) preferenceType);
        }
        if (preferenceType instanceof Integer) {
            editor.putInt(preference, (Integer) preferenceType);
        }
        if (preferenceType instanceof Long) {
            editor.putLong(preference, (Long) preferenceType);
        }
        if (preferenceType instanceof String) {
            editor.putString(preference, (String) preferenceType);
        }
        editor.commit();
    }

    /**
     * Read preferences
     *
     * @param sharedPreferences
     * @param preference
     * @param preferenceType
     * @param defaultValue
     * @param <T>
     * @return
     */
    public <T> T read(SharedPreferences sharedPreferences, String preference, Class<T> preferenceType, T defaultValue) {


        if (preferenceType == Boolean.class) {
            return (T) Boolean.valueOf(sharedPreferences.getBoolean(preference, (Boolean) defaultValue));

        }
        if (preferenceType == Float.class) {
            return (T) Float.valueOf(sharedPreferences.getFloat(preference, (Float) defaultValue));
        }
        if (preferenceType == Integer.class) {
            return (T) Integer.valueOf(sharedPreferences.getInt(preference, (Integer) defaultValue));
        }
        if (preferenceType == Long.class) {
            return (T) Long.valueOf(sharedPreferences.getLong(preference, (Long) defaultValue));
        }
        if (preferenceType == String.class) {
            return (T) String.valueOf(sharedPreferences.getString(preference, (String) defaultValue));
        }
        return null;
    }

    /**
     * Checks if a service is running
     *
     * @param serviceClass the service class
     * @return isServiceRunning
     */
    public boolean isServiceRunning(Class serviceClass) {
        return isServiceRunning(serviceClass.getName());
    }

    /**
     * Checks if a service is running
     *
     * @param serviceClass the service class
     * @return isServiceRunning
     */
    public boolean isServiceRunning(String serviceClass) {
        if (serviceClass == null) {
            return false;
        }

        boolean running = false;
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
            if (runningServiceInfo.service.getClassName().equals(serviceClass)) {
                running = true;
            }
        }

        return running;
    }

    /**
     * Gets a String [] of json objects from a json array
     *
     * @param jsonArray
     * @return
     * @throws JSONException
     */
    public JSONObject[] getJSONObjects(String jsonArray) throws JSONException {
        JSONArray array = new JSONArray(jsonArray);
        JSONObject[] jsonObjects = new JSONObject[jsonArray.length()];
        for (int i = 0; i < array.length(); i++) {
            jsonObjects[i] = array.getJSONObject(i);
        }
        return jsonObjects;
    }

    /**
     * validates a url
     *
     * @param url
     * @return true if the url is valid else otherwise
     */
    public boolean isValidUrl(String url) {
        return patternMatches(Patterns.WEB_URL, url);
    }

    /**
     * Matches an input to a pattern
     *
     * @param pattern
     * @param input
     * @return
     */
    public boolean patternMatches(Pattern pattern, String input) {
        return Pattern.matches(pattern.pattern(), input);
    }

    /**
     * Log info
     *
     * @param tag
     * @param message
     */
    public void logInfo(String tag, String message) {
        Log.i(tag, message);
    }

    /**
     * Log info
     *
     * @param tag
     * @param message
     * @param throwable
     */
    public void logInfo(String tag, String message, Throwable throwable) {
        Log.i(tag, message, throwable);
    }

    /**
     * Get device name
     *
     * @return device product name
     */
    public String getDeviceName() {
        return Build.PRODUCT;
    }

    /**
     * Get device model
     *
     * @return device model
     */
    public String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * Get device version
     *
     * @return device version
     */
    public String getDeviceVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Get connected Google Accounts
     *
     * @return
     */
    public String getConnectedGoogleAccountEmail() {
        String connectedGoogleAccountEmail = "";
        AccountManager accountManager = (AccountManager) getContext().getSystemService(Context.ACCOUNT_SERVICE);

        Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            if (account.type.contains("google")) {
                connectedGoogleAccountEmail = account.name;
                break;
            }
        }

        return connectedGoogleAccountEmail;
    }

    /**
     * Get line 1 phone number
     *
     * @return
     */
    public String getLine1Number() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
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

    /**
     * Create an home screen shortcut
     *
     * @param launchIntent
     * @param shortcutIcon
     * @param shortcutLabel
     */
    public void createHomesScreenShortcut(Intent launchIntent, int shortcutIcon, String shortcutLabel) {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutLabel);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getContext(), shortcutIcon));
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        sendBroadcast(intent);
    }

    public void deleteHomesScreenShortcut(Intent launchIntent, int shortcutIcon, String shortcutLabel) {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutLabel);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getContext(), shortcutIcon));
        intent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        sendBroadcast(intent);

    }

    /**
     * Gets a date property of the passed pattern in the passed time in millis
     *
     * @param pattern
     * @param timeMillis
     * @return
     */
    public String getDateProperty(String pattern, long timeMillis) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(timeMillis));
    }

    /**
     * Show simple snack bar with  message and action text
     *
     * @param view
     * @param text
     * @param actionText
     * @param action
     */
    public void showSimpleSnackBar(View view, String text, String actionText, @NonNull final Intent action) {
        Snackbar snackbar =
                Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        if (actionText != null && action != null) {
            snackbar.setAction(actionText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (action != null) {
                        startActivity(action);
                    }
                }
            });
        }

        snackbar.show();
    }

    /**
     * Show simple snack bar
     *
     * @param view
     * @param text
     */
    public void showSimpleSnackBar(View view, String text) {
        showSimpleSnackBar(view, text, null, null);
    }

    /**
     * Start repeating alarm
     *
     * @param requestCode
     * @param intent
     * @param triggerAtMillis
     * @param intervalMillis
     */
    public void startRepeatingAlarm(int requestCode, Intent intent, long triggerAtMillis, long intervalMillis) {
        PendingIntent pendingintent = PendingIntent.getBroadcast(getContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        startRepeatingAlarm(triggerAtMillis, intervalMillis, pendingintent);
    }

    /**
     * Start repeating alarm
     *
     * @param triggerAtMillis
     * @param intervalMillis
     * @param operation
     */
    public void startRepeatingAlarm(long triggerAtMillis, long intervalMillis, PendingIntent operation) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, intervalMillis, operation);
    }

    /**
     * Stop repeating alarm
     *
     * @param operation
     */
    public void stopRepeatingAlarm(PendingIntent operation) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(operation);
    }

    /**
     * Get the time in milliseconds of this time
     *
     * @param pattern date format
     * @param time    time to convert
     * @return timeInMillis
     */
    public long parseTime(String pattern, String time) {
        return CalendarUtils.parseTime(pattern, time);
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
                return getDateProperty("EEE dd-MMM", millis);
            }

        }

        // old time
        if (millis < currentMillis) {
            long timeDiff = currentMillis - millis;
        }

        return friendlyDate;
    }
}
