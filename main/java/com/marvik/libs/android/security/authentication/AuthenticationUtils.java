package com.marvik.libs.android.security.authentication;

import com.marvik.libs.android.utils.math.QuickMath;

/**
 * Authentication utils class used to perform authentication purposes
 * Created by victor on 7/2/2016.
 */
public class AuthenticationUtils {
    public static String getPasswordHint(String password) {
        String passwordHint = "";
        int passwordLength = password.length();
        for (int i = 0; i < passwordLength; i++) {
            if (QuickMath.isPrime(i))
                passwordHint += (password.charAt(i));
            else passwordHint += "*";
        }
        return passwordHint;
    }
}
