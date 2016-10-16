package com.marvik.libs.android.utils.device;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Field;

/**
 * Project - HackersWifi
 * Package - com.marvik.libs.android.utils.device
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 10/16/2016 at 10:55 AM.
 */

public class DeviceUtils {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getBaseOS() {
        return Build.VERSION.BASE_OS;
    }

    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getDevice() {
        return Build.DEVICE;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getProduct() {
        return Build.PRODUCT;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getBoard() {
        return Build.BOARD;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getSecurityPatch() {
        return Build.VERSION.SECURITY_PATCH;
    }

    public static String getCodeName() {
        return Build.VERSION.CODENAME;
    }

    /**
     * Checks if this version of os is debuggable.
     * Since this method is hidden from access in the android developer tools, this method
     * id reflected
     *
     * @return isDebuggable
     */
    public static String isDebuggable() {
        try {
            Class mClass = Class.forName("android.os.Build");
            Field debuggable = mClass.getField("IS_DEBUGGABLE");
            debuggable.setAccessible(true);
            return String.valueOf(debuggable.get(mClass));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return String.valueOf(false);
    }

    public static String getDisplay() {
        return Build.DISPLAY;
    }

    public static String getBootLoader() {
        return Build.BOOTLOADER;
    }

    public static String getRadio() {
        return Build.RADIO;
    }

    public static String getUser() {
        return Build.USER;
    }

    public static String getSerial() {
        return Build.SERIAL;
    }

    public static String getHardWare() {
        return Build.HARDWARE;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String[] getABIs() {
        return Build.SUPPORTED_ABIS;
    }

    public static String getLegacySupportedABIS() {
        return Build.CPU_ABI + "," + Build.CPU_ABI2;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String getSupportedABIS() {
        String abis = "";
        for (int i = 0; i < getABIs().length; i++) {
            abis += getABIs()[i];
            if (i < getABIs().length - 1) {
                abis += ",";
            }
        }
        return abis;
    }

    public static String getTag() {
        return Build.TAGS;
    }

}
