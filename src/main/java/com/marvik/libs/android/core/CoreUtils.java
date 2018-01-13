package com.marvik.libs.android.core;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;


public class CoreUtils {




    /**
     * Parses to an Array
     *
     * @param set
     * @return
     */
    @NonNull
    public ArrayList<String> formatToArray(@NonNull Set<String> set) {

        ArrayList<String> arrayList = new ArrayList<String>(set.size());
        for (int i = 0; i < set.size(); i++) {
            arrayList.add(set.iterator().next());
        }
        return arrayList;
    }









    /**
     * Commit preference
     *
     * @param editor
     * @param preference
     * @param preferenceType
     * @param <T>
     */
    public <T> void commit(SharedPreferences.Editor editor, String preference, T preferenceType) {


        if (preferenceType instanceof Boolean) {
            editor.putBoolean(preference, (Boolean) preferenceType);
        }
        if (preferenceType instanceof Float) {
            editor.putFloat(preference, (Float) preferenceType);
        }
        if (preferenceType instanceof Integer) {
            editor.putInt(preference, (Integer) preferenceType);
        }
        if (preferenceType instanceof Long) {
            editor.putLong(preference, (Long) preferenceType);
        }
        if (preferenceType instanceof String) {
            editor.putString(preference, (String) preferenceType);
        }
        editor.commit();
    }

    /**
     * Read preferences
     *
     * @param sharedPreferences
     * @param preference
     * @param preferenceType
     * @param defaultValue
     * @param <T>
     * @return
     */
    public <T> T read(SharedPreferences sharedPreferences, String preference, Class<T> preferenceType, T defaultValue) {


        if (preferenceType == Boolean.class) {
            return (T) Boolean.valueOf(sharedPreferences.getBoolean(preference, (Boolean) defaultValue));

        }
        if (preferenceType == Float.class) {
            return (T) Float.valueOf(sharedPreferences.getFloat(preference, (Float) defaultValue));
        }
        if (preferenceType == Integer.class) {
            return (T) Integer.valueOf(sharedPreferences.getInt(preference, (Integer) defaultValue));
        }
        if (preferenceType == Long.class) {
            return (T) Long.valueOf(sharedPreferences.getLong(preference, (Long) defaultValue));
        }
        if (preferenceType == String.class) {
            return (T) String.valueOf(sharedPreferences.getString(preference, (String) defaultValue));
        }
        return null;
    }




    /**
     * Gets a String [] of json objects from a json array
     *
     * @param jsonArray
     * @return
     * @throws JSONException
     */
    public static JSONObject[] getJSONObjects(String jsonArray) throws JSONException {
        JSONArray array = new JSONArray(jsonArray);
        JSONObject[] jsonObjects = new JSONObject[jsonArray.length()];
        for (int i = 0; i < array.length(); i++) {
            jsonObjects[i] = array.getJSONObject(i);
        }
        return jsonObjects;
    }
}
