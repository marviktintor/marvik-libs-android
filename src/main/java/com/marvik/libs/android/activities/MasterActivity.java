package com.marvik.libs.android.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    protected AlertDialog.Builder mAlert;

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
        attachActivityEventListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceivers();
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
        unregisterReceivers();
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
     * Attach activity event listeners. Called after init all views
     */
    protected abstract void attachActivityEventListeners();

    /**
     * Registers the intents to be listened by the broadcast receiver
     */
    protected abstract void registerReceivers();

    /**
     * Unregisters the intents listened by the broadcasts receiver
     */
    protected abstract void unregisterReceivers();


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
     * Get the container layout where content from other fragments is attached
     *
     * @return
     */
    public abstract ViewGroup getContent();

    /**
     * Get the navigation view associated with this activity
     *
     * @return
     */
    public abstract NavigationView getNavigationView();

    /**
     * Return header view at index
     *
     * @param index
     * @return headerView
     */
    public View getHeaderView(int index) {
        return getNavigationView().getHeaderView(index);
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
     * Hides the action bar
     */
    public void hideActionBars() {
        setToolBarVisibility(Toolbar.INVISIBLE);
        setAppToolBarVisibility(AppBarLayout.INVISIBLE);
    }

    /**
     * removes the action bar
     */
    public void blowActionBars() {
        setToolBarVisibility(Toolbar.GONE);
        setAppToolBarVisibility(AppBarLayout.GONE);
    }

    /**
     * removes the action bar
     */
    public void showActionBars() {
        setToolBarVisibility(Toolbar.VISIBLE);
        setAppToolBarVisibility(AppBarLayout.VISIBLE);
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

    public void setAppToolBarVisibility(int visibility) {
        getAppBarLayout().setVisibility(visibility);
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
        showActionBars();
        getAppToolbar().setNavigationIcon(navigationIcon);
        getAppToolbar().setOnMenuItemClickListener(this::onMenuItemClick);
        getAppToolbar().setNavigationOnClickListener(this::onClickNavigationIcon);
        getNavigationView().setNavigationItemSelectedListener(this::onNavigationItemClick);
    }

    /**
     * Unlock the app navigation after authentication
     */
    public void unlockNavigationAfterAuthentication(int navigationIcon) {
        showAppBar();
        getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getAppToolbar().setNavigationIcon(navigationIcon);
        getAppToolbar().setOnMenuItemClickListener(this::onMenuItemClick);
        getAppToolbar().setNavigationOnClickListener(this::onClickNavigationIcon);
        getNavigationView().setNavigationItemSelectedListener(this::onNavigationItemClick);
    }

    /**
     * Called when toolbar menu item is clicked
     *
     * @param menuItem
     * @return
     */
    public abstract boolean onMenuItemClick(MenuItem menuItem);

    /**
     * Called when navigation menu item is clicked
     *
     * @param menuItem
     * @return
     */
    public abstract boolean onNavigationItemClick(MenuItem menuItem);

    /**
     * Called when the navigation icon is clicked
     *
     * @param view
     * @return
     */
    public abstract boolean onClickNavigationIcon(View view);

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
        return attachFragment(fragment, getContentId(), addToBackStack);
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
    public Intent getInviteFriendIntent(String messageText) {
        try {
            String playStoreDownload = getAppShortenedPlayStoreDownloadLink().toString();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, messageText);
            return intent;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Create rate application intent
     *
     * @return
     */

    public Intent getRateApplicationIntent() {
        try {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format("market://details?id=%s", getPackageName())));
        } catch (ActivityNotFoundException e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format("https://play.google.com/store/app/details?id=%s", getPackageName())));
        }
    }


    /**
     * Get the root container id on which all views are children of
     *
     * @return
     */
    public abstract int getRootContainerId();

    /**
     * Get the container id of the parent view that fragments should be attached
     *
     * @return
     */
    public abstract int getContainerId();

    /**
     * Get the content id of the view that fragments should be attached
     *
     * @return
     */
    public abstract int getContentId();

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
                                final Intent positiveIntent, String negativeButtonLabel,
                                final Intent negativeIntent) {
        this.showAlertDialog(context, title, message, positiveButtonLabel, positiveIntent,
                negativeButtonLabel, negativeIntent, true);
    }

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
                                final Intent positiveIntent, String negativeButtonLabel, final Intent negativeIntent,
                                boolean cancellable) {
        if (mAlert == null) {
            mAlert = new AlertDialog.Builder(context);
        }

        if (mAlert.create().isShowing()) {
            mAlert.create().cancel();
        }

        mAlert.setTitle(title);
        mAlert.setMessage(message);

        if (positiveButtonLabel != null) {
            mAlert.setPositiveButton(positiveButtonLabel, ((dialogInterface, i) -> {
                if (positiveIntent != null) {
                    sendBroadcast(positiveIntent);
                }
            }));
        }

        if (negativeButtonLabel != null) {
            mAlert.setNegativeButton(negativeButtonLabel, ((dialogInterface, i) -> {
                if (negativeIntent != null) {
                    sendBroadcast(negativeIntent);
                }
            }));
        }
        mAlert.setCancelable(cancellable);
        mAlert.show();
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

    public abstract <T extends MasterFragment> void navigateTo(T fragment);

    abstract protected <T extends MasterFragment> T getPreviousFragment();

    abstract protected <T extends MasterFragment> T getCurrentFragment();

    /**
     * Apply profile colors for specific fragments when previewing specific images
     *
     * @param palette
     * @param defaultDarkColor
     * @param defaultLightColor
     */
    public void applyProfileColors(Palette palette, int defaultDarkColor, int defaultLightColor) {

        int darkVibrantColor = palette.getVibrantColor(defaultDarkColor);
        int lightVibrantColor = palette.getLightVibrantColor(defaultLightColor);

        Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();

        if (getAppToolbar() != null) {
            getAppToolbar().setBackgroundColor(lightVibrantColor);

            if (darkVibrantSwatch != null) {
                getAppToolbar().setTitleTextColor(darkVibrantSwatch.getTitleTextColor());
            }
        } else {
            Log.i(getClass().getSimpleName().toUpperCase(), "applyProfileColors: FAILED");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darkVibrantColor);
        }
    }

    /**
     * Apply default profile colors
     *
     * @param defaultDarkColor
     * @param defaultLightColor
     */
    public void resetProfileColors(int defaultDarkColor, int defaultLightColor, int defaultTitleTextColor) {


        if (getAppToolbar() != null) {
            getAppToolbar().setBackgroundColor(defaultLightColor);


            getAppToolbar().setTitleTextColor(defaultTitleTextColor);

        } else {
            Log.i(getClass().getSimpleName().toUpperCase(), "resetProfileColors: FAILED");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(defaultDarkColor);
        }
    }

    /**
     * Customize toolbar colors
     *
     * @param colorPrimary
     * @param colorPrimaryDark
     * @param titleColor
     * @param subtitleColor
     */
    public void customizeToolbar(int colorPrimary, int colorPrimaryDark, int titleColor, int subtitleColor) {


        if (getAppToolbar() != null) {
            getAppToolbar().setBackgroundColor(colorPrimary);
            getAppToolbar().setTitleTextColor(titleColor);
            getAppToolbar().setSubtitleTextColor(subtitleColor);

        } else {
            Log.i(getClass().getSimpleName().toUpperCase(), "resetProfileColors: FAILED");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
    }
}
