package com.marvik.libs.android.security.utils;

import com.marvik.libs.android.security.encryption.EncryptionAlgorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SecurityUtils
 * Security utilities
 * Created by victor on 7/9/2016.
 */
public class SecurityUtils {

    /**
     * Encrypts the passed text using the set encryption algorithm
     *
     * @param plainText           text to encrypt
     * @param encryptionAlgorithm algorithm to use
     * @return encryptedText
     */
    public static String encrypt(String plainText, EncryptionAlgorithm encryptionAlgorithm) {

        String encryptedText = null;
        String algorithm = null;
        switch (encryptionAlgorithm) {
            case MD5:
                algorithm = "MD5";
                break;
            case SHA1:
                algorithm = "SHA";
                break;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] input = plainText.getBytes();
            messageDigest.update(input);
            byte[] digest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte _digest : digest) {
                stringBuffer.append(Integer.toHexString(_digest & 0xff));
            }
            encryptedText = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedText;
    }
}
