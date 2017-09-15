package com.marvik.libs.android.applications;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Project - android
 * Package - com.marvik.libs.android.applications
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 14-Sep-17 at 5:12 PM.
 */

public class IntentUtils {

    public static boolean startActivity(Context context, String action, Uri uri) {
        try {
            context.startActivity(new Intent(action, uri));
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public static boolean startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        return true;
    }

    /**
     * Starts an activity
     *
     * @param cls
     */
    public static void startActivity(Context context, Class<? extends Activity> cls) {
        startActivity(context, cls, new Bundle(), 0);
    }

    /**
     * Starts an activity using the provided action
     *
     * @param action
     * @param cls
     */
    public static void startActivity(Context context, String action, Class<? extends Activity> cls) {
        startActivity(context, action, cls, new Bundle(), 0);
    }

    /**
     * Starts an activity with the passed bundle
     *
     * @param cls
     * @param bundle
     */
    public static void startActivity(Context context, Class<? extends Activity> cls, @NonNull Bundle bundle) {
        startActivity(context, cls, bundle, 0);
    }

    /**
     * Starts an activity with the passed flags
     *
     * @param cls
     * @param flags
     */
    public static void startActivity(Context context, Class<? extends Activity> cls, int flags) {
        startActivity(context, cls, new Bundle(), flags);
    }

    /**
     * Starts a bundled flagged activity
     *
     * @param cls
     * @param extras
     * @param flags
     */
    public static void startActivity(Context context, Class<? extends Activity> cls, @NonNull Bundle extras, int flags) {
        startActivity(context, Intent.ACTION_MAIN, cls, extras, flags);
    }

    /**
     * Starts a flagged activity with a custom action
     *
     * @param action
     * @param cls
     * @param flags
     */
    public static void startActivity(Context context, String action, Class<? extends Activity> cls, int flags) {
        startActivity(context, action, cls, new Bundle(), flags);
    }

    /**
     * Starts a bundled flagged activity with a custom action
     *
     * @param action
     * @param cls
     * @param extras
     * @param flags
     */
    public static void startActivity(Context context, String action, Class<? extends Activity> cls, @NonNull Bundle extras, int flags) {
        context.startActivity(new Intent(context, cls).addFlags(flags).putExtras(extras).setAction(action));
    }

    /**
     * Starts a service
     *
     * @param cls
     */
    public static void startService(Context context, Class<? extends Service> cls) {
        startService(context, cls, new Bundle(), 0);
    }

    /**
     * Starts a bundled service
     *
     * @param cls
     * @param bundle
     */
    public static void startService(Context context, Class<? extends Service> cls, @NonNull Bundle bundle) {
        startService(context, cls, bundle, 0);
    }

    /**
     * Starts a flagged service
     *
     * @param cls
     * @param flags
     */
    public static void startService(Context context, Class<? extends Service> cls, int flags) {
        startService(context, cls, new Bundle(), flags);
    }

    /**
     * Starts a bundled flagged service
     *
     * @param cls
     * @param extras
     * @param flags
     */
    public static void startService(Context context, Class<? extends Service> cls, @NonNull Bundle extras, int flags) {
        context.startService(new Intent(context, cls).addFlags(flags).putExtras(extras));
    }

    /**
     * Stops a service
     *
     * @param cls
     */
    public static void stopService(Context context, Class<? extends Service> cls) {
        stopService(context, cls, new Bundle(), 0);
    }

    /**
     * Stops a bundled service
     *
     * @param cls
     * @param bundle
     */
    public static void stopService(Context context, Class<? extends Service> cls, @NonNull Bundle bundle) {
        stopService(context, cls, bundle, 0);
    }

    /**
     * Stops a flagged service
     *
     * @param cls
     * @param flags
     */
    public static void stopService(Context context, Class<? extends Service> cls, int flags) {
        stopService(context, cls, new Bundle(), flags);
    }

    /**
     * Stops a bundled flagged service
     *
     * @param cls
     * @param extras
     * @param flags
     */
    public static void stopService(Context context, Class<? extends Service> cls, @NonNull Bundle extras, int flags) {
        context.stopService(new Intent(context, cls).addFlags(flags).putExtras(extras));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// SEND BROADCASTS //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Sends a action main broadcast that requires empty string permission
     *
     * @param cls
     */
    public static void sendBroadcast(Context context, Class<? extends Activity> cls) {
        context.sendBroadcast(new Intent(context, cls).putExtra(Intent.ACTION_MAIN, Intent.ACTION_MAIN), new String(""));
    }

    /**
     * Broadcasts an action
     *
     * @param action
     */
    public static void sendBroadcast(Context context, String action) {
        sendBroadcast(context, action, new Bundle());

    }

    /**
     * Broadcast a bundled action
     *
     * @param action
     * @param extras
     */
    public static void sendBroadcast(Context context, String action, @NonNull Bundle extras) {
        sendBroadcast(context, action, extras, null);
    }

    /**
     * Broadcasat a flagged bundled action
     *
     * @param action
     * @param extras
     * @param flags
     */
    public static void sendBroadcast(Context context, String action, @NonNull Bundle extras, int flags) {
        sendBroadcast(context, action, extras, flags, null);
    }

    /**
     * Broadcast a protected action
     *
     * @param action
     * @param permission
     */
    public static void sendBroadcast(Context context, String action, String permission) {
        sendBroadcast(context, action, 0, permission);
    }

    /**
     * Broadcast a protected bundled action
     *
     * @param action
     * @param extras
     * @param permission
     */
    public static void sendBroadcast(Context context, String action, @NonNull Bundle extras, String permission) {
        sendBroadcast(context, action, extras, 0, permission);
    }

    /**
     * Broadcast a protected flagged action
     *
     * @param action
     * @param flags
     * @param permission
     */
    public static void sendBroadcast(Context context, String action, int flags, String permission) {
        sendBroadcast(context, action, new Bundle(), flags, permission);
    }

    /**
     * Broadcast a protected bundled action
     *
     * @param action
     * @param extras
     * @param flags
     * @param permission
     */
    public static void sendBroadcast(Context context, String action, @NonNull Bundle extras, int flags, String permission) {
        context.sendBroadcast(new Intent(action).addFlags(flags).putExtras(extras), permission);
    }

    /**
     * Broadcast a protected intent
     *
     * @param intent
     * @param permission
     */
    public static void sendBroadcast(Context context, Intent intent, String permission) {
        context.sendBroadcast(intent, permission);
    }

    /**
     * Broadcast an intent
     *
     * @param intent
     */
    public static void sendBroadcast(Context context, Intent intent) {
        context.sendBroadcast(intent, null);
    }

    /**
     * Send a broadcast
     *
     * @param action
     * @param flags
     * @param extras
     * @param <T>
     */
    protected <T> void sendBroadcast(Context context, String action, List<Integer> flags, Map<String, T> extras) {

        Intent intent = new Intent(action);

        //Add flags
        if (flags != null) {
            for (Integer flag : flags) {
                intent.setFlags(flag);
            }
        }

        if (extras != null) {
            //Add extras
            Set<String> keySet = extras.keySet();

            for (String key : keySet) {

                T value = extras.get(key);
                if (value instanceof ArrayList) {
                    intent.putExtra(key, (ArrayList) value);
                }
                if (value instanceof Boolean) {
                    intent.putExtra(key, (Boolean) value);
                }
                if (value instanceof Bundle) {
                    intent.putExtra(key, (Bundle) value);
                }
                if (value instanceof Byte) {
                    intent.putExtra(key, (Byte) value);
                }
                if (value instanceof Character) {
                    intent.putExtra(key, (Character) value);
                }
                if (value instanceof CharSequence) {
                    intent.putExtra(key, (CharSequence) value);
                }
                if (value instanceof Short) {
                    intent.putExtra(key, (Short) value);
                }
                if (value instanceof Integer) {
                    intent.putExtra(key, (Integer) value);
                }
                if (value instanceof Long) {
                    intent.putExtra(key, (Long) value);
                }
                if (value instanceof Float) {
                    intent.putExtra(key, (Float) value);
                }
                if (value instanceof Double) {
                    intent.putExtra(key, (Double) value);
                }
                if (value instanceof Parcelable) {
                    intent.putExtra(key, (Parcelable) value);
                }
                if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                }
                if (value instanceof Serializable) {
                    intent.putExtra(key, (Serializable) value);
                }
            }

        }

        context.sendBroadcast(intent);
    }

    /**
     * Send a text message
     *
     * @param address receiver address
     * @param message to send
     */
    public static void sendMessage(Context context, String address, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra("address", address);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        intent.putExtra(TelephonyManager.EXTRA_INCOMING_NUMBER, address);
        startActivity(context, intent);

    }

    /**
     * Call a phone number
     *
     * @param phoneNumber to call
     */
    public void call(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * Checks if a service is running
     *
     * @param serviceClass
     * @return
     */
    public boolean isServiceRunning(Context context, String serviceClass) {
        if (serviceClass == null) {
            return false;
        }

        boolean running = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
            if (runningServiceInfo.service.getClassName().equals(serviceClass)) {
                running = true;
            }
        }
        return running;
    }
}
