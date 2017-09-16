package com.marvik.libs.android.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.marvik.libs.android.R;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Project - marvik-libs-android
 * Package - com.marvik.libs.android.activities
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 10/20/2016 at 3:46 PM.
 */

public abstract class MasterActivity extends AppCompatActivity {

    protected ProgressDialog mProgress;
    protected DrawerLayout mAppDrawer;

    protected NavigationView mAppNavigation;

    protected AppBarLayout mAppBarLayout;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Get the drawer layout associated with this activity
     *
     * @return
     */
    public DrawerLayout getDrawerLayout() {
        return mAppDrawer;
    }

    /**
     * Get the navigation view associated with this activity
     *
     * @return
     */
    public NavigationView getNavigationView() {
        return mAppNavigation;
    }

    /**
     * Get the action bar
     *
     * @return
     */
    public Toolbar getAppToolbar() {
        return mToolbar;
    }

    /**
     * Get the app bar layout
     *
     * @return
     */
    public AppBarLayout getAppBarLayout() {
        return mAppBarLayout;
    }

    /**
     * Lock the app navigation
     */
    public void lockNavigation() {
        getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getAppToolbar().setNavigationIcon(null);
    }

    /**
     * Unlock the app navigation
     */
    public void unlockNavigation() {
        getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getAppToolbar().setNavigationIcon(R.drawable.ic_navigation_white);
    }

    /**
     * Set the title of the activity
     *
     * @param activityTitle
     */
    public void setActivityTitle(String activityTitle) {
        getAppToolbar().setTitle(activityTitle != null ? activityTitle : getString(R.string.app_name));
    }


    /**
     * Set the subtitle of the activity
     *
     * @param activitySubTitle
     */
    public void setActivitySubtitle(String activitySubTitle) {
        getAppToolbar().setSubtitle(activitySubTitle != null ? activitySubTitle : "");
    }

    /**
     * Attaches a fragment to the parent container
     *
     * @param fragment       to attach
     * @param layout         the layout where this fragment is attached
     * @param addToBackStack add this fragment to the back stack
     * @return backStackEntryCount
     */
    public int attachFragment(Fragment fragment, int layout, boolean addToBackStack) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(getPackageName());
        }
        fragmentTransaction.commit();
        return getFragmentManager().getBackStackEntryCount();
    }

    /**
     * Attaches a fragment to the parent container
     *
     * @param fragment       to attach
     * @param addToBackStack add this fragment to the back stack
     * @return backStackEntryCount
     */
    public int attachFragment(Fragment fragment, boolean addToBackStack) {
        return attachFragment(fragment, getContainerId(), addToBackStack);
    }

    /**
     * Attaches a fragment to the parent container
     * This fragment is automatically added to the back stack
     *
     * @param fragment to attach
     * @param layout   the layout where this fragment is attached
     * @return backStackEntryCount
     */
    public int attachFragment(Fragment fragment, int layout) {
        return attachFragment(fragment, layout, true);
    }

    /**
     * Attaches a fragment to the parent container
     * This fragment is automatically added to the back stack
     *
     * @return backStackEntryCount
     */
    public int attachFragment(Fragment fragment) {
        return attachFragment(fragment, true);
    }

    /**
     * Clears the whole navigation stack trace
     */
    public void clearNavigationBackStack() {
        getFragmentManager().popBackStack(getPackageName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    /**
     * Create invite friend intent
     *
     * @return
     */
    public abstract Intent getInviteFriendIntent();

    /**
     * Get the root container id on which all views are children of
     *
     * @return
     */
    public abstract int getRootContainerId();

    /**
     * Get the container id of the view that fragments should be attached
     *
     * @return
     */
    public abstract int getContainerId();

    /**
     * Show an alert dialog
     *
     * @param context
     * @param title
     * @param message
     * @param positiveButtonLabel
     * @param positiveIntent
     * @param negativeButtonLabel
     * @param negativeIntent
     */
    public void showAlertDialog(Context context, String title, String message, String positiveButtonLabel,
                                Intent positiveIntent, String negativeButtonLabel, Intent negativeIntent) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);

        if (positiveButtonLabel != null) {
            alert.setPositiveButton(positiveButtonLabel, ((dialogInterface, i) -> {
                if (positiveIntent != null) {
                    sendBroadcast(positiveIntent);
                }
            }));
        }

        if (negativeButtonLabel != null) {
            alert.setNegativeButton(negativeButtonLabel, ((dialogInterface, i) -> {
                if (negativeIntent != null) {
                    sendBroadcast(negativeIntent);
                }
            }));
        }

        alert.show();
    }

    /**
     * Return a global progress dialog associated with the app main activity
     *
     * @return
     */
    public ProgressDialog getProgressDialog() {
        if (mProgress == null) {
            mProgress = new ProgressDialog(MasterActivity.this);
        }
        return mProgress;
    }

    /**
     * Show a progress dialog
     *
     * @param title
     * @param message
     * @param cancellable
     * @param cancellableOnTouchOutside
     */
    public void showProgressDialog(String title, String message, boolean cancellable,
                                   boolean cancellableOnTouchOutside) {

        if (getProgressDialog().isShowing()) {
            getProgressDialog().dismiss();
        }

        getProgressDialog().setTitle(title);
        getProgressDialog().setMessage(message);

        getProgressDialog().setCancelable(cancellable);
        getProgressDialog().setCanceledOnTouchOutside(cancellableOnTouchOutside);

        getProgressDialog().show();
    }

    /**
     * Try to close the progress dialog associated with the app main activity
     */
    public void tryCloseProgressDialog() {
        if (getProgressDialog().isShowing()) {
            getProgressDialog().dismiss();
        }
    }

    /**
     * Get the app play store download link
     *
     * @return
     */
    public abstract URL getAppPlayStoreDownloadLink() throws MalformedURLException;

    /**
     * Get app shortened play store download link
     *
     * @return
     */
    public abstract URL getAppShortenedPlayStoreDownloadLink() throws MalformedURLException;

}
