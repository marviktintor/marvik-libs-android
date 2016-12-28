package com.marvik.libs.android.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Project - HackersWifi
 * Package - com.marvik.libs.android.activities
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 12/28/2016 at 11:35 AM.
 */

public class LoadedActivity extends AppCompatActivity {
    /**
     * Attaches a fragment to a container
     *
     * @param containerId    the view to hold the fragment
     * @param fragment       to attach
     * @param addToBackStack to enable back navigation
     */
    protected <T> void attachFragment(int containerId, @NonNull T fragment, boolean addToBackStack) {


        if (addToBackStack) {

            if (fragment instanceof android.app.Fragment) {
                getFragmentManager().beginTransaction().replace(containerId,
                        (android.app.Fragment) fragment).addToBackStack(getPackageName()).commit();
            } else if (fragment instanceof android.support.v4.app.Fragment) {
                getSupportFragmentManager().beginTransaction().replace(containerId,
                        (android.support.v4.app.Fragment) fragment).addToBackStack(getPackageName()).commit();
            } else {
                throw new IllegalArgumentException(fragment.getClass().getName() + " is not a sub class of android.app.Fragment or android.support.v4.app.Fragment");
            }

        } else {
            if (fragment instanceof android.app.Fragment) {
                getFragmentManager().beginTransaction().replace(containerId,
                        (android.app.Fragment) fragment).commit();
            } else if (fragment instanceof android.support.v4.app.Fragment) {
                getSupportFragmentManager().beginTransaction().replace(containerId,
                        (android.support.v4.app.Fragment) fragment).commit();
            } else {
                throw new IllegalArgumentException(fragment.getClass().getName() + " is not a sub class of android.app.Fragment or android.support.v4.app.Fragment");
            }
        }

    }
}
