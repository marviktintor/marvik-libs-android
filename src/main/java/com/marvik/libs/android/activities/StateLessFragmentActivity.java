package com.marvik.libs.android.activities;

import android.app.Fragment;

/**
 * Project - marvik-libs-android
 * Package - com.marvik.libs.android.activities
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 10/20/2016 at 3:39 PM.
 */

public abstract class StateLessFragmentActivity extends StatelessActivity {

    /**
     * Returns the id of the parent container where the inflated fragments are attached
     *
     * @return layout resId
     */
    protected abstract int getParentContainerId();

    /**
     * Attaches a fragment to the parent container
     * This fragment is automatically added to the fragment back stack
     *
     * @param fragment to attach
     * @return backStackEntryCount
     */
    public int attachFragment(Fragment fragment) {
        return attachFragment(fragment, true);
    }

    /**
     * Attaches a fragment to the parent container
     *
     * @param fragment       to attach
     * @param addToBackStack add to back stack
     * @return backStackEntryCount
     */
    public int attachFragment(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack)
            getFragmentManager().beginTransaction().replace(getParentContainerId(), fragment)
                    .addToBackStack(getPackageName())
                    .commit();
        else
            getFragmentManager().beginTransaction().replace(getParentContainerId(), fragment).commit();

        return getFragmentManager().getBackStackEntryCount();
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
