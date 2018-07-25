package com.marvik.libs.android.exception;

public class DeprecatedClassException extends NoSuchMethodError {

    /**
     * Called when you instantiate a class that has been deprecated
     *
     * @param s
     */
    public DeprecatedClassException(String s) {
        super(s);
    }
}
