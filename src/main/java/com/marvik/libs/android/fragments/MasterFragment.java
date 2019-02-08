package com.marvik.libs.android.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marvik.libs.android.activities.MasterActivity;

/**
 * Project - marvik-libs-android
 * Package - com.marvik.libs.android.fragments
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 10/20/2016 at 3:46 PM.
 */

public abstract class MasterFragment extends Fragment {

    private View baseView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(hasOptionsMenu());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getFragmentTitle() != null) {
            setFragmentTitle(getFragmentTitle());
        }

        if (getFragmentSubTitle() != null) {
            setFragmentSubTitle(getFragmentSubTitle());
        } else {
            setFragmentSubTitle(null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Get the title of the fragment
     *
     * @return
     */
    public abstract String getFragmentTitle();

    /**
     * Get the subtitle of the fragment
     *
     * @return
     */
    public abstract String getFragmentSubTitle();

    /**
     * Returns the Res id of the layout to be populated as the View of this layout
     *
     * @return
     */
    public abstract int getLayout();

    public View getFragmentView(LayoutInflater layoutInflater, ViewGroup container) {
        return this.baseView = layoutInflater.inflate(getLayout(), container, false);
    }

    /**
     * Set the title of the fragment
     *
     * @param fragmentTitle
     */
    public void setFragmentTitle(String fragmentTitle) {
        ((MasterActivity) getActivity()).setActivityTitle(fragmentTitle, "");
    }

    /**
     * Set the subtitle of the fragment
     *
     * @param fragmentSubTitle
     */
    public void setFragmentSubTitle(String fragmentSubTitle) {
        ((MasterActivity) getActivity()).setActivitySubtitle(fragmentSubTitle);
    }


    /**
     * Show alert dialog
     *
     * @param title
     * @param message
     * @param positiveButtonLabel
     * @param positiveIntent
     * @param negativeButtonLabel
     * @param negativeIntent
     */
    protected void showAlertDialog(String title, String message, String positiveButtonLabel,
                                   Intent positiveIntent, String negativeButtonLabel, Intent negativeIntent) {
        ((MasterActivity) getActivity()).showAlertDialog(getActivity(), title, message, positiveButtonLabel,
                positiveIntent, negativeButtonLabel, negativeIntent, true);
    }

    /**
     * Show alert dialog
     *
     * @param title
     * @param message
     * @param positiveButtonLabel
     * @param positiveIntent
     * @param negativeButtonLabel
     * @param negativeIntent
     */
    protected void showAlertDialog(String title, String message, String positiveButtonLabel,
                                   Intent positiveIntent, String negativeButtonLabel, Intent negativeIntent, boolean cancellable) {
        ((MasterActivity) getActivity()).showAlertDialog(getActivity(), title, message, positiveButtonLabel,
                positiveIntent, negativeButtonLabel, negativeIntent, cancellable);
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
        ((MasterActivity) getActivity()).showProgressDialog(title, message, cancellable, cancellableOnTouchOutside);
    }

    protected void closeProgressDialog() {
        ((MasterActivity) getActivity()).tryCloseProgressDialog();
    }

    /**
     * Return true if this fragment has an options menu
     * false otherwise
     *
     * @return
     */
    protected abstract boolean hasOptionsMenu();

    /**
     * Return the base view which is inflated for this fragment
     *
     * @return
     */
    protected View getBaseView() {
        return this.baseView;
    }
}
