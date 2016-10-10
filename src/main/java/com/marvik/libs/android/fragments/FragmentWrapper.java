package com.marvik.libs.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.marvik.libs.android.activities.ActivityWrapper;
import com.marvik.libs.android.utils.Utilities;

import com.marvik.libs.android.R;

public abstract class FragmentWrapper extends Fragment {

    protected OnCreateFragment onCreateFragment;
    View.OnClickListener toolBarNavigationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onCreateFragment.onNavigationClickListener();
        }
    };
    private Toolbar tbAppToolBar;
    private AppBarLayout alAppBarLayout;
    private ActivityWrapper activityWrapper;
    private View wrapper;
    private RelativeLayout rlFragmentWrapperParentContainer;
    private Utilities utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLibraries();
        onCreateFragment(savedInstanceState);
        receiveBundle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        wrapper = getActivity().getLayoutInflater().inflate(R.layout.fragment_wrapper, container, false);

        initViews(wrapper);

        getFragmentWrapperParentContainer().addView(initFragmentViews(onCreateFragmentView(inflater, container, savedInstanceState)));

        consumeBundle();

        attachViewsData();

        return wrapper;
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeFragment();
        performPartialSync();
        onCreateFragment.setActivityTitle(getActivityTitle());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onCreateFragment = (OnCreateFragment) getActivity();
        onAttachFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onCreateFragment = (OnCreateFragment) getActivity();
        onAttachFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
        onPauseFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyFragment();
    }

    private void initLibraries() {
        activityWrapper = (ActivityWrapper) getActivity();
        utils = new Utilities(getActivity());
    }

    private void initViews(@NonNull View view) {
        rlFragmentWrapperParentContainer = (RelativeLayout) view.findViewById(R.id.fragment_wrapper_relativeLayout_wrapper);
        initBannerAds(view);

        alAppBarLayout = (AppBarLayout) view.findViewById(R.id.fragment_wrapper_app_bar_layout);

        tbAppToolBar = (Toolbar) view.findViewById(R.id.fragment_wrapper_toolbar_tool_bar);
        getActivityWrapper().setSupportActionBar(tbAppToolBar);
        Drawable drawable = getActivityWrapper().getResources().getDrawable(R.mipmap.ic_action_drawer_handle);
        ColorFilter colorFilter = new LightingColorFilter(Color.BLACK, Color.WHITE);
        drawable.setColorFilter(colorFilter);
        tbAppToolBar.setNavigationIcon(drawable);
        tbAppToolBar.setTitleTextColor(Color.WHITE);
        tbAppToolBar.setNavigationOnClickListener(toolBarNavigationClickListener);

    }

    /**
     * Init banner ads
     *
     * @param view
     */
    private void initBannerAds(View view) {
        if (view.findViewById(R.id.adView) != null) {
            final AdView adView = (AdView) view.findViewById(R.id.adView);
            adView.setVisibility(AdView.GONE);

            AdRequest adRequest = new AdRequest.Builder()
                    .setRequestAgent(getString(R.string.hackers_wifi_banner_ads)).build();
            adView.loadAd(adRequest);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    if (adView.getVisibility() == AdView.VISIBLE) {
                        adView.setVisibility(AdView.GONE);
                    }
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (adView.getVisibility() == AdView.GONE) {
                        adView.setVisibility(AdView.VISIBLE);
                    }
                }
            });
        }

    }

    /**
     * Return the relative layout on which all other layouts are attached  on
     *
     * @return
     */
    public RelativeLayout getFragmentWrapperParentContainer() {
        return rlFragmentWrapperParentContainer;
    }

    /**
     * Called when the fragment is created
     *
     * @param savedInstanceState
     */
    public abstract void onCreateFragment(@Nullable Bundle savedInstanceState);

    /**
     * Used for setting the title of the Activity
     *
     * @return activity title
     */
    @Nullable
    public abstract String getActivityTitle();

    /**
     * Callback used to receive any bundle passed to the fragment when this fragment was created
     */
    public abstract void receiveBundle();

    /**
     * Used for creating a custom view for the fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Nullable
    public abstract View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * Initializes the child views for the fragment being created.
     */

    public abstract View initFragmentViews(View view);

    /**
     * Callback used to hold methods that performHTTPRequest all the contents of the bundle passed to the fragment
     */
    public abstract void consumeBundle();

    /**
     * Callback called to attach data to all the views in the fragment
     */
    public abstract void attachViewsData();

    /**
     * Called when a fragment is first attached to its context.
     * {#onCreate(Bundle)} will be called after this.
     */
    public abstract void onAttachFragment();

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    public abstract void onResumeFragment();

    /**
     * Called to provide implementation to initiate the syncing of the contents of the current fragment
     */
    public abstract void performPartialSync();

    /**
     * Called when only the contents of the current fragment are synced
     */
    public abstract void onPerformPartialSync();

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to  Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    public abstract void onPauseFragment();

    /**
     * Called when the fragment is no longer in use.  This is called
     * after onStop()} and before onDetach()}.
     */
    public abstract void onDestroyFragment();

    /**
     * Returns the layout resource id for the layout used to populate the view for this fragment
     *
     * @return the layout resource id
     */
    public abstract int getParentLayout();

    public Utilities getUtilities() {
        return utils;
    }

    public ActivityWrapper getActivityWrapper() {
        return activityWrapper;
    }

    public interface OnCreateFragment {

        /**
         * Set the title of the fragment
         *
         * @param activityTitle
         */
        void setActivityTitle(String activityTitle);

        /**
         * Provide methods for opening and closing the navigation drawer
         */
        void onNavigationClickListener();

    }
}
