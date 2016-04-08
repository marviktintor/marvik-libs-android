package com.marvik.libs.android.applications;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.net.Uri;
import android.provider.Settings;

import com.marvik.libs.android.constants.Constants;
import com.marvik.libs.android.io.handler.FilesHandler;
import com.marvik.libs.android.utils.Utilities;

import com.marvik.libs.android.R;

import java.io.File;
import java.util.List;


public class AppsManager {

    private Context context;

    private FilesHandler filesHandler;

    public AppsManager(Context context) {
        this.context = context;
        utilities = new Utilities(getContext());
        filesHandler = new FilesHandler();
    }

    public Context getContext() {
        return context;
    }

    public Utilities utilities;

    public Utilities getUtilities() {
        return utilities;
    }

    public FilesHandler getFilesHandler() {
        return filesHandler;
    }

    /**
     * Get package manager
     *
     * @return package manager
     */
    public PackageManager getPackageManager() {
        return getContext().getPackageManager();
    }

    /**
     * Get installed application that have the specified flags
     *
     * @param flags
     * @return list of application info
     */
    public List<ApplicationInfo> getInstalledApplications(int flags) {
        return getPackageManager().getInstalledApplications(flags);

    }

    /**
     * Get application name
     *
     * @param applicationInfo
     * @return application name
     */
    public String getApplicationName(ApplicationInfo applicationInfo) {
        return getPackageManager().getApplicationLabel(applicationInfo).toString();
    }

    /**
     * Get application name
     *
     * @param packageName
     * @return application name
     */
    public String getApplicationName(String packageName) {
        try {
            return getApplicationName(getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get permission groups
     *
     * @param flag
     * @return list of permission groups
     */
    public List<PermissionGroupInfo> getPermissionGroups(int flag) {
        return getPackageManager().getAllPermissionGroups(flag);
    }

    /**
     * Gets Package info based on the requested flags
     *
     * @param packageName
     * @param flags
     * @return package info
     */
    public PackageInfo getPackageInfo(String packageName, int flags) {
        try {
            return getPackageManager().getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get requested permission
     *
     * @param packageName
     * @return string array of all the permission a package has requested for in the uses-permission tag in the manifest
     */
    public String[] getRequestedPermissions(String packageName) {
        String[] requestedPermissions = getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
        return requestedPermissions == null ? new String[]{} : requestedPermissions;
    }

    /**
     * Gets the receivers in an application
     *
     * @param packageName
     * @return receivers
     */
    public ActivityInfo[] getReceivers(String packageName) {
        return getPackageInfo(packageName, PackageManager.GET_RECEIVERS).receivers;
    }

    /**
     * Get application version name
     *
     * @param packageName
     * @return version name of the application
     */
    public String getApplicationVersionName(String packageName) {
        return getPackageInfo(packageName, PackageManager.GET_META_DATA).versionName;
    }

    /**
     * Get the first time an application was installed on the device
     *
     * @param packageName
     * @return first install time
     */
    public long getApplicationFirstInstallDate(String packageName) {
        return getPackageInfo(packageName, PackageManager.GET_META_DATA).firstInstallTime;
    }

    /**
     * Get last update time of an application
     *
     * @param packageName
     * @return last update time
     */
    public long getApplicationLastUpdateTime(String packageName) {
        return getPackageInfo(packageName, PackageManager.GET_META_DATA).lastUpdateTime;
    }

    /**
     * Get application base apk
     *
     * @param packageName
     * @return an application base apk
     */
    private String getBaseApk(String packageName) {
        return getPackageInfo(packageName, PackageManager.GET_META_DATA).applicationInfo.sourceDir;
    }

    /**
     * Gets an app launch intent
     *
     * @param packageName
     * @return Application Launch Intent
     */
    private Intent getApplicationLaunchIntent(String packageName) {
        return getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * Opens the application if the application has a launcher activity
     *
     * @param packageName
     * @return
     */
    public boolean openApplication(String packageName) {
        Intent launchIntent = getApplicationLaunchIntent(packageName);
        if (launchIntent == null) {
            return false;
        }
        getUtilities().startActivity(launchIntent);
        return true;

    }

    /**
     * Create an Intent.createChooser for sharing an installed application
     *
     * @param packageName
     */
    public void shareApplication(String packageName) {
        String filePath = getBaseApk(packageName);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("file/application");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        getUtilities().startActivity(intent);
    }

    /**
     * Create an app shortcut on the homescreen of the device if the app shortcut is not already in the homescreen
     *
     * @param packageName
     */

    public void createShortcut(String packageName) {
        int appHomeScreenIcon = getPackageInfo(packageName, PackageManager.GET_META_DATA).applicationInfo.icon;
        String applicationName = getApplicationName(packageName);
        Intent launchIntent = getApplicationLaunchIntent(packageName);
        getUtilities().createHomesScreenShortcut(launchIntent, appHomeScreenIcon, applicationName);
    }

    /**
     * Create a copy of an installed application
     *
     * @param packageName
     */
    public void backupApplication(String packageName) {
        File baseApk = new File(getBaseApk(packageName));
        String applicationName = getApplicationName(packageName);
        String appVersion = getApplicationVersionName(packageName);
        String fileSrc = Constants.MARVIK_APPS_STORAGE_PATH + File.separator + getUtilities().getString(R.string.app_name) + File.separator + applicationName + ".apk";

        getFilesHandler().createDirectories(new File(fileSrc).getParentFile());

        File appBackupFile = new File(fileSrc);
        if (appBackupFile.exists()) {
            if (appBackupFile.length() != baseApk.length()) {
                getFilesHandler().copyFile(baseApk.getAbsolutePath(), appBackupFile.getAbsolutePath());
                getUtilities().toast(applicationName + " backed up");
            } else {
                getUtilities().toast(applicationName + " already backed up");
            }
        } else {
            getFilesHandler().copyFile(baseApk.getAbsolutePath(), appBackupFile.getAbsolutePath());
            getUtilities().toast(applicationName + " backed up");
        }

    }

    /**
     * View an application in the Google play store
     *
     * @param packageName
     */
    public void viewInPlayStore(String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!getUtilities().startActivity(intent)) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MARKET_APP_URL + packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getUtilities().startActivity(intent);
        }
    }

    /**
     * View an application in the Google play store
     *
     * @param packageName
     */
    public void rateApplication(String packageName) {
        viewInPlayStore(packageName);
    }

    /**
     * Open an application in the settings application
     *
     * @param packageName
     */
    public void viewInSettings(String packageName) {
        Uri data = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(data);
        getUtilities().startActivity(intent);
    }

    /**
     * Uninstall an application that is installed in the device
     *
     * @param packageName
     */
    public void uninstallApplication(String packageName) {
        Uri data = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(data);
        getUtilities().startActivity(intent);
    }
}
