package com.marvik.libs.android.views;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marvik.libs.android.R;
import com.marvik.libs.android.models.navigation.NavigationItems;

/**
 * Project - android
 * Package - com.marvik.libs.android.views
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 06-Nov-17 at 8:28 AM.
 */

public  class NavigationViewHolder extends RecyclerView.ViewHolder {

    protected AppCompatImageView mNavigationIcon;
    protected AppCompatTextView mNavigationTitle;
    protected AppCompatTextView mNavigationDescription;

    /**
     * Navigation View Holder
     *
     * @param itemView
     */
    public NavigationViewHolder(View itemView) {
        super(itemView);
    }


    public AppCompatImageView getNavigationIconView() {
        return mNavigationIcon;
    }

    public AppCompatTextView getNavigationDescriptionView() {
        return mNavigationDescription;
    }

    public AppCompatTextView getNavigationTitleView() {
        return mNavigationTitle;
    }
}
