package com.marvik.libs.android.models.navigation;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.util.Locale;

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

    private IconType iconType;
    private int resIcon;
    private Bitmap bitmapIcon;
    private Uri uriIcon;
    private Drawable drawableIcon;
    private String title;
    private String subTitle;
    private Fragment fragment;

    /**
     * Navigation item
     *
     * @param bitmapIcon of the navigation item
     * @param title      of the navigation item
     * @param subTitle   of the navigation item
     * @param fragment   fragment to attach if this navigation item is selected
     */
    public NavigationItems(Bitmap bitmapIcon, String title, String subTitle, Fragment fragment) {
        iconType = IconType.BITMAP_ICON;
        setBitmapIcon(bitmapIcon);
        this.title = title;
        this.subTitle = subTitle;
        this.fragment = fragment;
    }

    /**
     * Navigation item
     *
     * @param resIcon  of the navigation item
     * @param title    of the navigation item
     * @param subTitle of the navigation item
     * @param fragment fragment to attach if this navigation item is selected
     */
    public NavigationItems(int resIcon, String title, String subTitle, Fragment fragment) {
        iconType = IconType.RES_ICON;
        setResIcon(resIcon);
        this.title = title;
        this.subTitle = subTitle;
        this.fragment = fragment;
    }

    /**
     * Navigation item
     *
     * @param drawableIcon of the navigation item
     * @param title        of the navigation item
     * @param subTitle     of the navigation item
     * @param fragment     fragment to attach if this navigation item is selected
     */
    public NavigationItems(Drawable drawableIcon, String title, String subTitle, Fragment fragment) {
        iconType = IconType.DRAWABLE_ICON;
        setDrawableIcon(drawableIcon);
        this.title = title;
        this.subTitle = subTitle;
        this.fragment = fragment;
    }

    /**
     * Navigation item
     *
     * @param uriIcon  of the navigation item
     * @param title    of the navigation item
     * @param subTitle of the navigation item
     * @param fragment fragment to attach if this navigation item is selected
     */
    public NavigationItems(Uri uriIcon, String title, String subTitle, Fragment fragment) {
        iconType = IconType.URI_ICON;
        setUriIcon(uriIcon);
        this.title = title;
        this.subTitle = subTitle;
        this.fragment = fragment;
    }

    public IconType getIconType() {
        return iconType;
    }

    public void setIconType(IconType iconType) {

        switch (iconType) {
            case BITMAP_ICON:
            case RES_ICON:
            case URI_ICON:
            case DRAWABLE_ICON:
                this.iconType = iconType;
                break;
            default:
                throw new IllegalArgumentException(String.format(Locale.getDefault(),
                        "Cannot set icon of type %s ", iconType.toString()));
        }
    }

    public Bitmap getBitmapIcon() {
        if (getIconType() == IconType.BITMAP_ICON)
            return bitmapIcon;
        else
            throw new IllegalStateException(String.format("%s : %s", "Cannot return bitmap icon found"
                    , getIconType().toString()));
    }

    public void setBitmapIcon(Bitmap bitmapIcon) {
        if (getIconType() == IconType.BITMAP_ICON)
            this.bitmapIcon = bitmapIcon;
        else
            throw new IllegalArgumentException(String.format("Expected bitmap res, provided %s ", getIconType().toString()));


    }

    public int getResIcon() {
        if (getIconType() == IconType.RES_ICON)
            return resIcon;
        else
            throw new IllegalStateException(String.format("%s : %s", "Cannot return resource icon found"
                    , getIconType().toString()));
    }

    public void setResIcon(int resIcon) {
        if (getIconType() == IconType.RES_ICON)
            this.resIcon = resIcon;
        else
            throw new IllegalArgumentException(String.format("Expected icon resource, provided %s ", getIconType().toString()));
    }

    public Drawable getDrawableIcon() {
        if (getIconType() == IconType.DRAWABLE_ICON)
            return drawableIcon;

        else
            throw new IllegalStateException(String.format("%s : %s", "Cannot return drawable icon found"
                    , getIconType().toString()));
    }

    public void setDrawableIcon(Drawable drawableIcon) {

        if (getIconType() == IconType.DRAWABLE_ICON)
            this.drawableIcon = drawableIcon;
        else
            throw new IllegalArgumentException(String.format("Expected drawable res, provided %s ", getIconType().toString()));
    }

    public Uri getUriIcon() {
        if (getIconType() == IconType.URI_ICON)
            return uriIcon;
        else
            throw new IllegalStateException(String.format("%s : %s", "Cannot return uri icon found"
                    , getIconType().toString()));
    }

    public void setUriIcon(Uri uriIcon) {
        if (getIconType() == IconType.URI_ICON)
            this.uriIcon = uriIcon;
        else
            throw new IllegalArgumentException(String.format("Expected uri res, provided %s ", getIconType().toString()));
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
