package com.marvik.libs.android.exception;

public class DeprecatedMethodException extends NoSuchMethodError {

    /**
     * Called when you call a method that has been deprecated
     *
     * @param s
     */
    public DeprecatedMethodException(String s) {
        super(s);
    }
}
