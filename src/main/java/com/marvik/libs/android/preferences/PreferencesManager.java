package com.marvik.libs.android.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * PreferencesManager
 * Provides low level generic API's to fast write and read from shared preferences
 * https://github.com/victormwenda/marvik-libs-android/blob/master/src/main/java/com/marvik/libs/android/preferences/PreferencesManager.java
 */
public class PreferencesManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferencesManager(@NonNull Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    protected Context getContext() {
        return context;
    }

    protected SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private SharedPreferences.Editor getEditor() {
        if (editor == null) {
            editor = getSharedPreferences().edit();
        }
        return editor;
    }

    /**
     * Commit the preference
     *
     * @param preference
     * @param preferenceType
     * @param <T>
     */
    protected <T> void commit(String preference, T preferenceType) {

        if (preferenceType instanceof Boolean) {
            getEditor().putBoolean(preference, (Boolean) preferenceType);
        }

        if (preferenceType instanceof Double) {
            getEditor().putLong(preference, Double.doubleToRawLongBits((Double) preferenceType));
        }

        if (preferenceType instanceof Float) {
            getEditor().putFloat(preference, (Float) preferenceType);
        }
        if (preferenceType instanceof Integer) {
            getEditor().putInt(preference, (Integer) preferenceType);
        }
        if (preferenceType instanceof Long) {
            getEditor().putLong(preference, (Long) preferenceType);
        }
        if (preferenceType instanceof String || preference == null) {
            getEditor().putString(preference, (String) preferenceType);

        }
        if (preferenceType instanceof HashSet) {
            getEditor().putStringSet(preference, (HashSet<String>) preferenceType);

        }

        getEditor().commit();

    }

    /**
     * Read the shared preference of this class
     *
     * @param preference
     * @param preferenceType
     * @param defaultValue
     * @param <T>
     * @return
     */
    protected <T> T read(String preference, Class<T> preferenceType, T defaultValue) {
        if (preferenceType == Boolean.class) {
            return (T) Boolean.valueOf(getSharedPreferences().getBoolean(preference, (Boolean) defaultValue));

        }
        if (preferenceType == Double.class) {
            return (T) Double.valueOf(Double.longBitsToDouble(getSharedPreferences().getLong(preference, Double.doubleToRawLongBits((Double) defaultValue))));
        }
        if (preferenceType == Float.class) {
            return (T) Float.valueOf(getSharedPreferences().getFloat(preference, (Float) defaultValue));
        }
        if (preferenceType == Integer.class) {
            return (T) Integer.valueOf(getSharedPreferences().getInt(preference, (Integer) defaultValue));
        }
        if (preferenceType == Long.class) {
            return (T) Long.valueOf(getSharedPreferences().getLong(preference, (Long) defaultValue));
        }
        if (preferenceType == String.class) {
            return (T) String.valueOf(getSharedPreferences().getString(preference, (String) defaultValue));
        }
        if (preferenceType == Set.class) {
            return (T) getSharedPreferences().getStringSet(preference, (Set<String>) defaultValue);
        }

        return null;
    }

    /**
     * Clear preferences
     */
    public void clearPreferences() {
        getEditor().clear().commit();
    }
}
