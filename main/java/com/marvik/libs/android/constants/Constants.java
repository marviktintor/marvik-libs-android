package com.marvik.libs.android.constants;

import android.os.Environment;

import java.io.File;

public class Constants {

    /**
     * The URL for every app in the google play store
     */
    public static final String MARKET_APP_URL = "http://play.google.com/store/apps/details?id=";


    /**
     * The public folder for all marvik apps
     */
    public static final String MARVIK_APPS_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MarvikApps";
}
