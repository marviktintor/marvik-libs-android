package com.marvik.libs.android.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.marvik.libs.android.fragments.MasterFragment;

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

    protected NavigationView mAppNavigation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initDependencies();
        initViews();
        onInitViews();
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
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Get the layout resid associated with this activity
     *
     * @return
     */
    @LayoutRes
    protected abstract int getActivityLayout();

    /**
     * init the application
     */
    protected abstract void initApplication();

    /**
     * Init all app dependencies
     */
    protected abstract void initDependencies();

    /**
     * Called after setContentView
     * Initializes all the views in the activity
     */
    protected abstract void initViews();

    /**
     * Called after all views have been init and its the right time to attach event listeners
     */
    protected abstract void onInitViews();

    /**
     * Get the root container layout associated with this activity
     *
     * @return
     */
    public abstract ViewGroup getRootContainer();

    /**
     * Get the container layout associated with this activity
     *
     * @return
     */
    public abstract ViewGroup getContainer();

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
    public abstract Toolbar getAppToolbar();

    /**
     * Get the app bar layout
     *
     * @return
     */
    public abstract AppBarLayout getAppBarLayout();

    /**
     * Returns the drawer layout associated with this activity;
     *
     * @return
     */
    public abstract DrawerLayout getDrawer();

    /**
     * Get the default navigation icon for the application
     *
     * @return
     */
    @DrawableRes
    public abstract int getDefaultNavigationIcon();

    /**
     * Should be called after authentication when user information is available and it is time to
     * update the navigation drawer among other entities showing rich user information
     */
    public abstract void onUserInformationAvailable();

    /**
     * Hides the app app bar
     */
    public void hideAppBar() {
        getAppBarLayout().setVisibility(AppBarLayout.GONE);
    }

    /**
     * Hides the app app bar
     */
    public void showAppBar() {
        getAppBarLayout().setVisibility(AppBarLayout.VISIBLE);
    }

    /**
     * Removes tool bar from the view
     */
    public void hideToolBar() {
        setToolBarVisibility(Toolbar.GONE);
    }

    /**
     * Hides the toolbar
     */
    public void hideToolBarLazy() {
        setToolBarVisibility(Toolbar.INVISIBLE);
    }

    /**
     * Shows the toolbar
     */
    public void showToolBar() {
        setToolBarVisibility(Toolbar.VISIBLE);
    }

    public void setToolBarVisibility(int visibility) {
        getAppToolbar().setVisibility(visibility);
    }

    /**
     * Lock the app navigation
     */
    public void lockNavigation() {
        getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getAppToolbar().setNavigationIcon(null);
    }

    /**
     * Lock the app navigation for authentication
     */
    public void lockNavigationForAuthentication() {
        hideAppBar();
        getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getAppToolbar().setNavigationIcon(null);
    }

    /**
     * Unlock the app navigation
     */
    public void unlockNavigation(int navigationIcon) {
        getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getAppToolbar().setNavigationIcon(navigationIcon);
        getAppToolbar().setOnMenuItemClickListener(this::onMenuItemClick);
        getAppToolbar().setNavigationOnClickListener(this::onNavigationOnClick);
    }

    /**
     * Unlock the app navigation after authentication
     */
    public void unlockNavigationAfterAuthentication(int navigationIcon) {
        showAppBar();
        getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getAppToolbar().setNavigationIcon(navigationIcon);
        getAppToolbar().setOnMenuItemClickListener(this::onMenuItemClick);
        getAppToolbar().setNavigationOnClickListener(this::onNavigationOnClick);
    }

    /**
     * Called when menu item is clicked
     *
     * @param menuItem
     * @return
     */
    public abstract boolean onMenuItemClick(MenuItem menuItem);

    /**
     * Called when the navigation icon is clicked
     *
     * @param view
     * @return
     */
    public abstract boolean onNavigationOnClick(View view);

    /**
     * Opend drawer
     */
    public void openDrawer() {
        if (!getDrawer().isDrawerOpen(getDrawerGravity())) {
            getDrawer().openDrawer(getDrawerGravity());
        }
    }

    /**
     * Toggle drawer
     */
    public void toggleDrawer() {
        if (getDrawer().isDrawerOpen(getDrawerGravity())) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    /**
     * Close drawer
     */
    public void closeDrawer() {
        if (getDrawer().isDrawerOpen(getDrawerGravity())) {
            getDrawer().closeDrawer(getDrawerGravity());
        }
    }

    /**
     * Get drawer gravity
     *
     * @return
     */
    public abstract int getDrawerGravity();

    /**
     * Set the title of the activity
     *
     * @param activityTitle
     */
    public void setActivityTitle(String activityTitle, String appName) {
        getAppToolbar().setTitle(activityTitle != null ? activityTitle : appName);
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
                                final Intent positiveIntent, String negativeButtonLabel, final Intent negativeIntent) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);

        if (positiveButtonLabel != null) {
            alert.setPositiveButton(positiveButtonLabel, (new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (positiveIntent != null) {
                        sendBroadcast(positiveIntent);
                    }
                }
            }));
        }

        if (negativeButtonLabel != null) {
            alert.setNegativeButton(negativeButtonLabel, (new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (negativeIntent != null) {
                        MasterActivity.this.sendBroadcast(negativeIntent);
                    }
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


    /**
     * On pre navigate callback
     * Called before user navigates to a new fragment
     *
     * @param fragment
     * @param <T>
     */
    abstract protected <T extends MasterFragment> void onPreNavigate(T fragment);

    /**
     * Called before
     *
     * @param fragment
     * @param <T>
     */
    abstract protected <T extends MasterFragment> void onNavigate(T fragment);

    abstract protected <T extends MasterFragment> void onPostNavigate(T newFragment, T oldFragment);

    abstract protected <T extends MasterFragment> void navigateTo(T fragment);

    abstract protected <T extends MasterFragment> T getPreviousFragment();

    abstract protected <T extends MasterFragment> T getCurrentFragment();
}
