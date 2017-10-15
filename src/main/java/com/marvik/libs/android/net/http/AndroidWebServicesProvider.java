package com.marvik.libs.android.net.http;

import android.content.Context;

import java.util.Map;


public abstract class AndroidWebServicesProvider<K, V> extends WebServicesProvider<K, V> {

    protected Context mContext;

    /**
     * Web services provide class that provides apis
     * for sending requests to the server and has call
     * backs to handle errors and provide information that is incoming from the server
     *
     * @param url
     * @param query
     */
    public AndroidWebServicesProvider(Context context, String url, String query, Map<K, V> requestProperties) {
        super(url, query, requestProperties);

        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}