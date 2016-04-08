package com.marvik.libs.android.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.marvik.libs.android.applications.AppsManager;
import com.marvik.libs.android.fragments.FragmentWrapper;
import com.marvik.libs.android.utils.Utilities;

import com.marvik.libs.android.R;

public abstract class ActivityWrapper extends AppCompatActivity implements FragmentWrapper.OnCreateFragment {


    private Utilities utils;

    private InterstitialAd interstitialAd;
    private AdRequest adRequest;
    private String lastKnownFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLibraries();
        onCreateActivity(savedInstanceState);
        initInterstitialAds();

    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyActivity();
    }

    @Override
    public void setActivityTitle(String activityTitle) {
        if (activityTitle == null) {
            setTitle(new AppsManager(getApplicationContext()).getApplicationName(getPackageName()));
            return;
        }
        setTitle(activityTitle);
    }

    @Override
    public void onBackPressed() {
        detachFragment();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    /**
     * Performs initialization of all loaders
     * Called when the activity is created
     *
     * @param savedInstanceState
     */
    protected abstract void onCreateActivity(Bundle savedInstanceState);

    /**
     * Called when the activity is resumed
     */
    protected abstract void onResumeActivity();

    /**
     * Called when the activity is paused
     */
    protected abstract void onPauseActivity();

    /**
     * Called when the activity is destroyed
     */
    protected abstract void onDestroyActivity();

    /**
     * Returns the id of the fragment container view
     */
    public abstract int getFragmentsContainerId();

    /**
     * Called when a new fragment is attached to the the activity
     *
     * @param fragment
     * @param addToBackStack
     */
    public abstract void onFragmentAttached(Fragment fragment, boolean addToBackStack);

    /**
     * Removes an attached fragment from the container
     *
     * @param fragment
     */
    public void detachFragment(@NonNull Fragment fragment) {
        getFragmentManager().beginTransaction().remove(fragment).commit();
    }

    /**
     * Attaches a fragment to a container
     *
     * @param fragment
     * @param addToBackStack
     */
    public void attachFragment(@NonNull Fragment fragment, boolean addToBackStack) {

        if (getLastKnownFragment() != null) {

            //Ensure that the user does not set the same fragment severally
            if (getLastKnownFragment() == fragment.getClass().getCanonicalName()) {
                return;
            }
        }

        if (addToBackStack)
            getFragmentManager().beginTransaction().replace(getFragmentsContainerId(), fragment).addToBackStack(getPackageName()).commit();
        else
            getFragmentManager().beginTransaction().replace(getFragmentsContainerId(), fragment).commit();

        setLastKnownFragment(fragment.getClass().getCanonicalName());

        onFragmentAttached(fragment, addToBackStack);
    }

    private void clearBackStack() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStackImmediate();
        }

    }

    public void clearBackStackAll() {
        int backStacks = getFragmentManager().getBackStackEntryCount();
        for (int i = backStacks; i > 0; i--) {
            getFragmentManager().popBackStack();
        }
    }

    public int getBackStackSize() {
        return getFragmentManager().getBackStackEntryCount();
    }

    private void detachFragment() {
        if (getFragmentManager().getBackStackEntryCount() > 1)
            getFragmentManager().popBackStack();
        else finish();

        setLastKnownFragment(FragmentWrapper.class.getCanonicalName());
    }

    public String getLastKnownFragment() {
        return lastKnownFragment;
    }

    public void setLastKnownFragment(String fragment) {
        this.lastKnownFragment = fragment;
    }

    /**
     * Utilities#getUtilities
     *
     * @return an instance of the utils class
     */
    public Utilities getUtilities() {
        return utils;
    }

    private void initLibraries() {
        utils = new Utilities(getApplicationContext());
    }

    private void initInterstitialAds() {
        interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId(getUtilities().getString(R.string.hackers_wifi_interstitial_ads));
        adRequest = new AdRequest.Builder().setRequestAgent(getUtilities().getString(R.string.hackers_wifi_interstitial_ads)).build();
        interstitialAd.loadAd(adRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adRequest = new AdRequest.Builder().setRequestAgent(getUtilities().getString(R.string.hackers_wifi_interstitial_ads)).build();
                interstitialAd.loadAd(adRequest);
            }
        });

    }

    public void toggleDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerLayout_drawer);
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    public void closeDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerLayout_drawer);
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }
}
