package com.marvik.libs.android.net.http;

import java.util.HashMap;
import java.util.Map;

/**
 * URL Builder
 * <p/>
 * Provides apis for quickly building URLS
 */
public class URLBuilder {

    private String url;

    private String query;

    private Map<String, String> params;

    /**
     * URL Builder accepts a url which it builds
     *
     * @param url
     */
    public URLBuilder(String url) {
        this.url = url;
        params = new HashMap<>();
    }

    /**
     * Return the url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the query
     *
     * @return query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Append new name value pairs to the query
     *
     * @param key
     * @param value
     * @return a cascade object of this class
     */
    public URLBuilder append(String key, String value) {

        if (getQuery() == null) {
            query = new String();
            query = key + "=" + value;
        } else {
            query += "&" + key + "=" + value;
        }

        params.put(key, value);

        return this;
    }

    /**
     * Returns the built query
     *
     * @return
     */
    public URLBuilder build() {
        return this;
    }

    /**
     * Returns the built query which is appended to the url
     *
     * @return
     */
    public String buildUpon() {
        return getUrl() + getQuery();
    }

    /**
     * Return the query as a string
     *
     * @return the query
     */
    @Override
    public String toString() {
        return buildUpon();
    }

    /**
     * Returns the params that have been added to the form
     *
     * @return
     */
    public Map<String, String> getParams() {
        return params;
    }
}
