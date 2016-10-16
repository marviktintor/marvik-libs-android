package com.marvik.libs.android.utils.telephone;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Project - HackersWifi
 * Package - com.marvik.libs.android.utils.telephone
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 10/16/2016 at 1:16 PM.
 */

public class TelephonyUtils {

    private SubscriptionManager subscriptionManager;
    private TelephonyManager telephonyManager;
    private Context context;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public TelephonyUtils(Context context) {
        this.context = context;
        telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        subscriptionManager = (SubscriptionManager) getContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    public Context getContext() {
        return context;
    }

    public TelephonyManager getTelephonyManager() {
        return telephonyManager;
    }

    public SubscriptionManager getSubscriptionManager() {
        return subscriptionManager;
    }

    public String getCountryIso() {
        return getTelephonyManager().getSimCountryIso();
    }

    public boolean isDualSim() {
        boolean isDualSim = false;
        try {
            Method method = getTelephonyManager().getClass().getMethod("isMultiSimEnabled");
            method.setAccessible(true);
            isDualSim = (boolean) method.invoke(telephonyManager);
            return isDualSim;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return isDualSim;
    }


    public String getOperatorName() {
        return getTelephonyManager().getSimOperatorName();
    }

    public String getOperator() {
        return getTelephonyManager().getSimOperator();
    }

    public int getSlotId(int subId) {
        try {
            Method method = SubscriptionManager.class.getMethod("getSlotId");
            return (Integer) method.invoke(getSubscriptionManager());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public String getPhoneType() {

        switch (getTelephonyManager().getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_GSM:
                return "GSM";
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.PHONE_TYPE_SIP:
                return "SIP";
            default:
                return "None";

        }
    }

    public String getSlotSoftwareVersion() {
        return getTelephonyManager().getDeviceSoftwareVersion();
    }

    public String getSubscriberId() {
        return getTelephonyManager().getSubscriberId();
    }

    public String getSerialNumber() {
        return getTelephonyManager().getSimSerialNumber();
    }

    public String getLine1Number() {
        return getTelephonyManager().getLine1Number();
    }
    public String getLineNumber() {
        try {
            Method method = getTelephonyManager().getClass().getMethod("getLine1Number", Integer.class);
            return method.invoke(getTelephonyManager(), getSubId()).toString();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "unknown";}

    public int getSubId() {
        try {
            Method method = SubscriptionManager.class.getMethod("getSubId");
            return (Integer) method.invoke(getSubscriptionManager());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public List<SubscriptionInfo> getActiveSubscriptionInfoList() {
        return getSubscriptionManager().getActiveSubscriptionInfoList();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public int getSlotsNumber() {
        return getActiveSubscriptionInfoList().size();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public String getSlotNumber(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getNumber();
        else return "Invalid slot";
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public CharSequence getSlotCarrier(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getCarrierName();
        else return "Invalid slot";
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public String getSlotCountryIso(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getCountryIso();
        else return "Invalid slot";
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public String getSlotIccId(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getIccId();
        else return "Invalid slot";
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public CharSequence getSlotDisplayName(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getDisplayName();
        else return "Invalid slot";
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public int getSlotIndex(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getSimSlotIndex();
        else return -1;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public int getSlotDataRoaming(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getDataRoaming();
        else return -1;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public int getSlotIconTint(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getIconTint();
        else return -1;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public int getSlotMcc(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getMcc();
        else return -1;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public int getSlotMnc(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getMnc();
        else return -1;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public int getSubscriptionId(int slotNumber) {
        if (slotNumber > getActiveSubscriptionInfoList().size())
            return getActiveSubscriptionInfoList().get(slotNumber).getSubscriptionId();
        else return -1;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public String getSlotImei(int slotNumber) {
        try {
            Method method = getTelephonyManager().getClass().getMethod("getSimSerialNumber", Integer.class);
            return method.invoke(getTelephonyManager(), getSubscriptionId(slotNumber)).toString();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "unknown";
    }
}
