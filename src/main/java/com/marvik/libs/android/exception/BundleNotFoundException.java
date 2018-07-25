package com.marvik.libs.android.exception;

public class BundleNotFoundException extends NullPointerException {

    /**
     * Called when method expects bundle to be passed via intent but bundle was not passed
     *
     * @param s
     */
    public BundleNotFoundException(String s) {
        super(s);
    }
}
