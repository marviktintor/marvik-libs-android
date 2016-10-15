package com.marvik.libs.android.security;

import android.os.Build;
import android.util.Log;

import java.io.IOException;

/**
 * Project - HackersWifi
 * Package - com.marvik.libs.android.security
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 10/15/2016 at 2:10 PM.
 */

public final class PhoneSecurity {

    public static boolean isRooted() {

        boolean rooted = false;
        try {
            Runtime.getRuntime().exec("su");
            rooted = true;
        } catch (IOException e) {
            Log.i("HackersWifi", "Your device " + Build.DEVICE + " is not rooted\nError : " + e.toString());
        }
        return rooted;
    }
}
