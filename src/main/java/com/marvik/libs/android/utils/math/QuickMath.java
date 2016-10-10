package com.marvik.libs.android.utils.math;

/**
 * Class for doing simple Arithmetic
 * Created by victor on 7/2/2016.
 */
public class QuickMath {

    /**
     * Checks whether a number is prime
     *
     * @param number numberToBeChecked
     * @return true if prime false otherwise
     */
    public static boolean isPrime(int number) {
        if (number % 2 == 0) return false;

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

    /**
     * Checks whether a number is odd
     *
     * @param number numberToBeChecked
     * @return true if odd false otherwise
     */
    public static boolean isOdd(int number) {
        return Math.abs(number % 2) == 1;

    }

    /**
     * Checks whether a number is even
     *
     * @param number numberToBeChecked
     * @return true if odd false otherwise
     */
    public static boolean isEven(int number) {
        return Math.abs(number % 2) == 0;

    }
}
