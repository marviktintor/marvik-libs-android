package com.marvik.libs.android.adt.arrays;

import java.util.List;

/**
 * ArrayUtils
 * Helper class used to do quick array manipulations
 * Created by victor on 7/7/2016.
 */
public class ArrayUtils {

    /**
     * Creates a string of comma separated array items
     *
     * @param items items of the array
     * @return comma separated items of the array
     */
    public static String asString(List<String> items) {
        String elements = "";
        for (int i = 0; i < items.size(); i++) {
            elements += items.get(i);
            if (i < items.size() - 1) {
                elements += ",";
            }
        }
        return elements;
    }
}
