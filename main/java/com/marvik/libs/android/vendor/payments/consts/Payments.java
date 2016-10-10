package com.marvik.libs.android.vendor.payments.consts;

import android.os.Environment;

/**
 * Payments
 * Contains constants used for handling payments
 * Created by victor on 7/31/2016.
 */
public class Payments {
    public static final String FIRST_RUN_TRACKER_FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
            + System.getProperty("file.separator") + "android.pmt";
}
