package com.marvik.libs.android.telephony;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
        return "unknown";
    }

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
        return -1;
    }

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

    public boolean isNetworkRoaming() {
        return getTelephonyManager().isNetworkRoaming();
    }

    public String getMobileNetwork() {
        return getTelephonyManager().getNetworkOperator();
    }

    public String getMobileNetworkCode() {
        return getTelephonyManager().getNetworkOperator();
    }

    public CharSequence getNetworkOperator() {
        return getTelephonyManager().getNetworkOperator();
    }

    public String getNetworkOperatorName() {
        return getTelephonyManager().getNetworkOperatorName();
    }

    public String getNetworkType() {
        return lookUpNetworkType(getTelephonyManager().getNetworkType());
    }

    @NonNull
    private String lookUpNetworkType(int networkType) {
        switch (networkType) {
            /** Network type is unknown */
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "Unknown";
            /** Current network is GPRS */
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            /** Current network is EDGE */
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            /** Current network is UMTS */
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            /** Current network is CDMA: Either IS95A or IS95B*/
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            /** Current network is EVDO revision 0*/
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO r0";
            /** Current network is EVDO revision A*/
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO rA";
            /** Current network is 1xRTT*/
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            /** Current network is HSDPA */
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            /** Current network is HSUPA */
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            /** Current network is HSPA */
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            /** Current network is iDen */
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDen";
            /** Current network is EVDO revision B*/
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO rB";
            /** Current network is LTE */
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            /** Current network is eHRPD */
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "eHRPD";
            /** Current network is HSPA+ */
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            /** Current network is GSM   */
            case 16:
                return "GSM";
            /** Current network is TD_SCDMA   */
            case 17:
                return "TD_SCDMA";
            /** Current network is IWLAN  */
            case 18:
                return "IWLAN";
        }
        return "Unknown";
    }

    public String getMobileCountryCode() {
        return getTelephonyManager().getNetworkCountryIso();
    }

    public String getMMSUserAgentProfURL() {
        return getTelephonyManager().getMmsUAProfUrl();
    }

    public String getMMSUserAgent() {
        return getTelephonyManager().getMmsUserAgent();
    }

    public String getVoiceMailAlphaTag() {
        return getTelephonyManager().getVoiceMailAlphaTag();
    }

    public String getVoiceMailNumber() {
        return getTelephonyManager().getVoiceMailNumber();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getVoiceMailNetworkType() {
        return lookUpNetworkType(getTelephonyManager().getVoiceNetworkType());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isSMSCapable() {
        return getTelephonyManager().isSmsCapable();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public boolean isVoiceCapable() {
        return getTelephonyManager().isVoiceCapable();
    }

    public String getDeviceSoftwareVersion() {
        return getTelephonyManager().getDeviceSoftwareVersion();
    }

    public String getGroupIdLevel() {
        return getTelephonyManager().getGroupIdLevel1();
    }

    @RequiresApi(Build.VERSION_CODES.M)
    public boolean hasHearingAidCompatibility() {
        return getTelephonyManager().isHearingAidCompatibilitySupported();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean hasTTYMode() {
        return getTelephonyManager().isTtyModeSupported();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public boolean hasCarriePrivileges() {
        return getTelephonyManager().hasCarrierPrivileges();
    }

    public String getCellLocation() {
        return getTelephonyManager().getCellLocation().toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean canChangeDTMFToneLength() {
        return getTelephonyManager().canChangeDtmfToneLength();
    }

    public boolean hasICCCard() {
        return getTelephonyManager().hasIccCard();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isWorldPhone() {
        return getTelephonyManager().isWorldPhone();
    }
}
