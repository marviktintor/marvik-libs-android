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

public class NavigationViewHolder extends RecyclerView.ViewHolder {

    private AppCompatImageView mNavigationIcon;
    private AppCompatTextView mNavigationTitle;
    private AppCompatTextView mNavigationDescription;

    /**
     * Navigation View Holder
     *
     * @param itemView
     */
    public NavigationViewHolder(View itemView) {
        super(itemView);

        mNavigationIcon = itemView.findViewById(R.id.navigation_icon);
        mNavigationTitle = itemView.findViewById(R.id.navigation_title);
        mNavigationDescription = itemView.findViewById(R.id.navigation_description);
    }

    public void bindData(NavigationItems navigationItems) {
        switch (navigationItems.getIconType()) {
            case RES_ICON:
                getNavigationIconView().setImageResource(navigationItems.getResIcon());
                break;
            case BITMAP_ICON:
                getNavigationIconView().setImageBitmap(navigationItems.getBitmapIcon());
                break;
            case URI_ICON:
                getNavigationIconView().setImageURI(navigationItems.getUriIcon());
                break;
            case DRAWABLE_ICON:
                getNavigationIconView().setImageDrawable(navigationItems.getDrawableIcon());
                break;
        }

        mNavigationTitle.setText(navigationItems.getTitle());
        mNavigationDescription.setText(navigationItems.getSubTitle());
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
