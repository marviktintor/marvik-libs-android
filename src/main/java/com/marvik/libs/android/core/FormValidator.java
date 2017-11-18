package com.marvik.libs.android.core;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Project - android
 * Package - com.marvik.libs.android.core
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 19-Nov-17 at 1:07 AM.
 */

public final class FormValidator {

    /**
     * Validates person names
     *
     * @param text
     * @return
     */
    public static boolean isPersonName(String text) {
        return text.matches(Pattern.compile("[a-zA-z]").pattern()) && text.split(" ").length > 1;
    }

    /**
     * Validates phone numbers
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        return validate(Patterns.PHONE.pattern(), phone);
    }

    /**
     * Validates email address
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return validate(Patterns.EMAIL_ADDRESS.pattern(), email);
    }

    /**
     * Validates nationa id.
     * Should contains numbers and length greater than 7
     *
     * @param nationId
     * @return
     */
    public static boolean isNationalId(String nationId) {
        return nationId.matches(Pattern.compile("[0-9]{7,}").pattern());
    }
    public static boolean validate(String pattern, String text) {
        return text.matches(pattern);
    }
}
