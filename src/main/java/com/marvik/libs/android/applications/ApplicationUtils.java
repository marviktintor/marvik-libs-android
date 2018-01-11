package com.marvik.libs.android.applications;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Patterns;

import com.marvik.libs.android.io.handler.FilesHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ApplicationUtils {

    public static FilesHandler getFilesHandler() {
        return new FilesHandler();
    }


    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    /**
     * Get list of all installed apps with the provided flag
     *
     * @param context
     * @param flags
     * @return
     */
    public static List<ApplicationInfo> getInstalledApplications(Context context, int flags) {
        return getPackageManager(context).getInstalledApplications(flags);

    }

    /**
     * Get application name
     *
     * @param context
     * @param applicationInfo
     * @return
     */
    public static String getApplicationName(Context context, ApplicationInfo applicationInfo) {
        return getPackageManager(context).getApplicationLabel(applicationInfo).toString();
    }

    /**
     * Get application name
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getApplicationName(Context context, String packageName) {
        try {
            return getApplicationName(context, getPackageManager(context).getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get list of permission groups
     *
     * @param context
     * @param flag
     * @return
     */
    public static List<PermissionGroupInfo> getPermissionGroups(Context context, int flag) {
        return getPackageManager(context).getAllPermissionGroups(flag);
    }

    /**
     * Get package info
     *
     * @param context
     * @param packageName
     * @param flags
     * @return
     */
    public static PackageInfo getPackageInfo(Context context, String packageName, int flags) {
        try {
            return getPackageManager(context).getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the permissions requested by a package
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String[] getRequestedPermissions(Context context, String packageName) {
        String[] requestedPermissions = getPackageInfo(context, packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
        return requestedPermissions == null ? new String[]{} : requestedPermissions;
    }

    /**
     * Get the broadcast receivers of a package
     *
     * @param context
     * @param packageName
     * @return
     */
    public static ActivityInfo[] getReceivers(Context context, String packageName) {
        return getPackageInfo(context, packageName, PackageManager.GET_RECEIVERS).receivers;
    }

    /**
     * Get the version name of a package
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getApplicationVersionName(Context context, String packageName) {
        return getPackageInfo(context, packageName, PackageManager.GET_META_DATA).versionName;
    }

    /**
     * Get the first install date of a package
     *
     * @param context
     * @param packageName
     * @return
     */
    public static long getApplicationFirstInstallDate(Context context, String packageName) {
        return getPackageInfo(context, packageName, PackageManager.GET_META_DATA).firstInstallTime;
    }

    /**
     * Get the last update time of a package
     *
     * @param context
     * @param packageName
     * @return
     */
    public static long getApplicationLastUpdateTime(Context context, String packageName) {
        return getPackageInfo(context, packageName, PackageManager.GET_META_DATA).lastUpdateTime;
    }

    /**
     * Get the apk file of a package
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getBaseApk(Context context, String packageName) {
        return getPackageInfo(context, packageName, PackageManager.GET_META_DATA).applicationInfo.sourceDir;
    }

    /**
     * Get a package launch intent
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Intent getApplicationLaunchIntent(Context context, String packageName) {
        return getPackageManager(context).getLaunchIntentForPackage(packageName);
    }

    /**
     * Open a package, if it contains a launch intent
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean openApplication(Context context, String packageName) {
        Intent launchIntent = getApplicationLaunchIntent(context, packageName);
        if (launchIntent == null) {
            return false;
        }
        context.startActivity(launchIntent);
        return true;

    }

    /**
     * Open intent to share a package
     *
     * @param context
     * @param packageName
     */
    public static void shareApplication(Context context, String packageName) {
        String filePath = getBaseApk(context, packageName);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("file/application");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        context.startActivity(intent);
    }

    /**
     * Create a home screen short cut of a package
     *
     * @param context
     * @param packageName
     */
    public static void createShortcut(Context context, String packageName) {
        int appHomeScreenIcon = getPackageInfo(context, packageName, PackageManager.GET_META_DATA).applicationInfo.icon;
        String applicationName = getApplicationName(context, packageName);
        Intent launchIntent = getApplicationLaunchIntent(context, packageName);
        createHomesScreenShortcut(context, launchIntent, appHomeScreenIcon, applicationName);
    }

    /**
     * Create an home screen shortcut for an intent
     *
     * @param launchIntent
     * @param shortcutIcon
     * @param shortcutLabel
     */
    public static void createHomesScreenShortcut(Context context, Intent launchIntent, int shortcutIcon, String shortcutLabel) {

        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutLabel);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, shortcutIcon));
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        context.sendBroadcast(intent);


    }

    /**
     * Back up a packag
     *
     * @param context
     * @param packageName
     * @param storeDir
     * @return
     */
    public static boolean backupPackage(Context context, String packageName, File storeDir) {

        File baseApk = new File(getBaseApk(context, packageName));
        String applicationName = getApplicationName(context, packageName);
        String appVersion = getApplicationVersionName(context, packageName);
        String fileSrc = storeDir.getAbsolutePath() + File.separator + packageName + File.separator + applicationName + appVersion + ".apk";

        getFilesHandler().createDirectories(new File(fileSrc).getParentFile());

        File appBackupFile = new File(fileSrc);
        if (appBackupFile.exists()) {
            if (appBackupFile.length() != baseApk.length()) {
                getFilesHandler().copyFile(baseApk.getAbsolutePath(), appBackupFile.getAbsolutePath());
                return true;
            } else {
                return false;
            }
        } else {
            getFilesHandler().copyFile(baseApk.getAbsolutePath(), appBackupFile.getAbsolutePath());
            return true;
        }

    }

    /**
     * View package in google play store
     *
     * @param context
     * @param packageName
     * @throws ActivityNotFoundException
     */
    public static void viewInPlayStore(Context context, String packageName) throws ActivityNotFoundException {

        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (ActivityNotFoundException exception2) {
                throw new ActivityNotFoundException(exception.getMessage());
            }
        }
    }

    /**
     * Rate package in google play store
     *
     * @param context
     * @param packageName
     */
    public static void rateApplication(Context context, String packageName) {
        viewInPlayStore(context, packageName);
    }

    /**
     * View package in Settings app
     *
     * @param context
     * @param packageName
     */
    public static void viewInSettings(Context context, String packageName) {
        Uri data = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * Uninstall package
     *
     * @param context
     * @param packageName
     */
    public static void uninstallPackage(Context context, String packageName) {
        Uri data = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * Get connected Google Accounts
     *
     * @return
     */
    public static List<String> getConnectedGoogleAccountEmail(Context context) {
        return getConnectedAccountEmail(context, "google");
    }

    /**
     * Get connected account emails of a specific domain
     *
     * @param context
     * @param domain  if domain is null, returns all email addresses
     * @return
     */
    public static List<String> getConnectedAccountEmail(Context context, String domain) {

        List<String> connectedAccountEmail = new ArrayList<>();

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account[] accounts = accountManager.getAccounts();

        for (Account account : accounts) {

            if (account.name.matches(Patterns.EMAIL_ADDRESS.pattern()) || account.type.matches(Patterns.EMAIL_ADDRESS.pattern())) {

                if (domain != null) {
                    if (account.type.contains(domain)) {
                        connectedAccountEmail.add(account.name);
                    }
                } else {
                    connectedAccountEmail.add(account.name);
                }
            }
        }

        return connectedAccountEmail;
    }

    /**
     * Start repeating alarm
     *
     * @param requestCode
     * @param intent
     * @param triggerAtMillis
     * @param intervalMillis
     */
    public void startRepeatingAlarm(Context context, int requestCode, Intent intent, long triggerAtMillis, long intervalMillis) {
        PendingIntent pendingintent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        startRepeatingAlarm(context, triggerAtMillis, intervalMillis, pendingintent);
    }

    /**
     * Start repeating alarm
     *
     * @param triggerAtMillis
     * @param intervalMillis
     * @param operation
     */
    public void startRepeatingAlarm(Context context, long triggerAtMillis, long intervalMillis, PendingIntent operation) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, intervalMillis, operation);
    }

    /**
     * Stop repeating alarm
     *
     * @param operation
     */
    public void stopRepeatingAlarm(Context context, PendingIntent operation) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(operation);
    }

    /**
     * Get notification builder
     *
     * @return
     */
    public static NotificationCompat.Builder getNotificationBuilder(Context context) {
        return new NotificationCompat.Builder(context);
    }

    /**
     * Creates a notification and sends it
     *
     * @param smallIcon
     * @param largeIcon
     * @param title
     * @param contentText
     * @param actions
     * @param contentIntent
     * @param defaults
     */
    public static void sendExplicitNotification(Context context, int smallIcon, Bitmap largeIcon, String title, String contentText,
                                                NotificationCompat.Action[] actions, PendingIntent contentIntent,
                                                int defaults, int priority) {

        NotificationCompat.Builder builder = getNotificationBuilder(context).
                setContentTitle(title).setContentText(contentText)
                .setDefaults(defaults).setContentIntent(contentIntent)
                .setSmallIcon(smallIcon).setPriority(priority)
                .setLargeIcon(largeIcon);

        if (actions != null) {
            for (NotificationCompat.Action action : actions) {
                builder.addAction(action);
            }
        }


        sendNotification(context, builder.build(), 1);
    }

    /**
     * Send a notification
     *
     * @param notification
     */
    public static void sendNotification(Context context, Notification notification, int notificationId) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notificationId, notification);
    }

    /**
     * Cancel a notification
     *
     * @param context
     * @param notificationId
     */
    public static void cancelNotification(Context context, int notificationId) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.cancel(notificationId);
    }
}
