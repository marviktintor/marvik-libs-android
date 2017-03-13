package com.marvik.libs.android.models.navigation;

import android.app.Fragment;
import android.graphics.Bitmap;

/**
 * Project - marvik-libs-android
 * Package - com.marvik.libs.android.models.navigation
 * <p>
 * Victor Mwenda
 * +254(0)718034449
 * vmwenda.vm@gmail.com
 * <p>
 * Android App Development Laptop
 * Created by victor on 10/21/2016 at 3:11 PM.
 */

public class NavigationItems {

    private Bitmap icon;
    private String title;
    private String subTitle;
    private Fragment fragment;

    /**
     * Navigation item
     *
     * @param icon     of the navigation item
     * @param title    of the navigation item
     * @param subTitle of the navigation item
     * @param fragment fragment to attach if this navigation item is selected
     */
    public NavigationItems(Bitmap icon, String title, String subTitle, Fragment fragment) {
        this.icon = icon;
        this.title = title;
        this.subTitle = subTitle;
        this.fragment = fragment;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
