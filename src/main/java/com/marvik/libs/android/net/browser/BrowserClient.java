package com.marvik.libs.android.net.browser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Project - QueryBack
 * Package - com.marvik.libs.android.net.browser
 * <p>
 * Victor Mwenda
 * 0718034449
 * <p>
 * Android App Development Laptop
 * Created by victor on 9/9/2016.
 */
public class BrowserClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
    }
}
