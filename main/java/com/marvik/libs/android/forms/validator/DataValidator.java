package com.marvik.libs.android.forms.validator;

import java.util.regex.Pattern;

/**
 * Created by victor on 6/30/2016.
 */
public class DataValidator {
    /**
     * method to quickly validate
     *
     * @param regularExpression
     * @param input
     * @return
     */
    public static boolean is(String regularExpression, String input) {
        return Pattern.matches(regularExpression, input);
    }

    /**
     * Validates full name
     *
     * @param fullname
     * @return
     */
    public static boolean isPersonFullName(String fullname) {
        return isPersonName(fullname);
    }

    /**
     * Validates a person name
     *
     * @param name
     * @return
     */
    public static boolean isPersonName(String name) {
        return isText(name);
    }

    /**
     * Validates id number
     *
     * @param idNumber
     * @return
     */
    public static boolean isKenyanIdNumber(String idNumber) {
        return is("\\d{7,8}", idNumber);
    }

    /**
     * Validates phone
     *
     * @param phonenumber
     * @return
     */
    public static boolean isPhoneNumber(String phonenumber) {
        return is("07\\d{8}", phonenumber);
    }

    /**
     * Validates text
     *
     * @param text
     * @return
     */
    public static boolean isText(String text) {
        return is("\\.+", text);
    }
}
