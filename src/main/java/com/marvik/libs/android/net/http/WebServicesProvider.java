package com.marvik.libs.android.net.http;

import android.util.Patterns;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.regex.Pattern;


public abstract class WebServicesProvider<K, V> {

    /**
     * HTTP ERROR
     */
    public static final int ERROR_TYPE_HTTP_ERROR = 1;

    /**
     * INVALID URL
     */
    public static final int ERROR_TYPE_EMPTY_URL = 2;

    /**
     * INVALID URL
     */
    public static final int ERROR_TYPE_INVALID_URL = 3;

    /**
     * NETWORK ERROR
     */
    public static final int ERROR_TYPE_NETWORK_ERROR = 4;

    /**
     * EMPTY QUERY
     */
    public static final int ERROR_TYPE_EMPTY_QUERY = 5;

    //The query to send
    protected String query;

    //The url
    protected String url;


    //url builder to help in building url
    protected URLBuilder urlBuilder;

    //The HTTP Response
    protected String httpResponse;

    //The request properties sent to the server
    protected Map<K, V> requestProperties;

    /**
     * Web services provide class that provides apis
     * for sending requests to the server and has call
     * backs to handle errors and provide information that is incoming from the server
     *
     * @param url
     * @param query
     */
    public WebServicesProvider(String url, String query, Map<K, V> requestProperties) {
        urlBuilder = new URLBuilder(url);

        setQuery(query);
        setUrl(url);
        setRequestProperties(requestProperties);

    }


    /**
     * Get an handle of the url builder to help in building urls
     *
     * @return urlBuilder
     */
    public URLBuilder getUrlBuilder() {
        return urlBuilder;
    }

    /**
     * Sets the url that is being requested
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
        onSetURL();
    }

    /**
     * Created a query that is appended to the query
     *
     * @param query
     */
    public void setQuery(String query) {
        this.query = query;
        onSetQuery();
    }

    /**
     * Returns the URL being executed
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the query that is appended to the url while sending HTTP Request
     *
     * @return query
     */
    public String getQuery() {
        return query;
    }

    public void setRequestProperties(Map<K, V> properties) {
        this.requestProperties = properties;
    }

    public Map<K, V> getRequestProperties() {
        return requestProperties;
    }

    /**
     * Returns the query that is appended to the url while sending HTTP Request
     *
     * @return query
     */
    public String getParams() {
        return getQuery();
    }


    /**
     * Returns the sent query
     *
     * @return
     */
    public String getSentQuery() {
        return getQuery();
    }


    /**
     * Performs an HTTP Request and return the server response in form of a String
     *
     * @return
     * @throws IOException
     */
    public String doHttpRequest(String requestMethod) throws IOException {
        String dataStream = null;

        if (getUrl() == null) {
            onConnectionError(ERROR_TYPE_EMPTY_URL);
            throw new IllegalArgumentException("URL Cannot be null");
        }

        if (!isValidUrl(getUrl())) {
            onConnectionError(ERROR_TYPE_INVALID_URL);
            throw new IllegalArgumentException("Invalid URL [" + getUrl() + "]");
        }

        URL url = new URL(getUrl());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(requestMethod);


        if (getRequestProperties() != null) {
            for (Map.Entry<K, V> entries : getRequestProperties().entrySet()) {
                httpURLConnection.setRequestProperty(String.valueOf(entries.getKey()), String.valueOf(entries.getValue()));
            }
        }


        httpURLConnection.setDoOutput(true);
        //httpURLConnection.setDoInput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        if (getQuery() != null) {
            dataOutputStream.writeBytes(getQuery());
            onSendQuery();
        }

        dataOutputStream.flush();
        dataOutputStream.close();

        onConnect(httpURLConnection.getResponseCode());

        InputStream inputStream = httpURLConnection.getInputStream();
        onReceiveResponse();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder builder = new StringBuilder();

        while ((dataStream = bufferedReader.readLine()) != null) {
            onReadResponse(dataStream);

            builder.append(dataStream);
            onAppendResponse(builder.toString());
        }

        dataStream = builder.toString();


        setHTTPResponse(dataStream);

        onFinishedReadingResponse(dataStream);


        return dataStream;

    }

    /**
     * Returns this is a valid url
     *
     * @param url
     * @return
     */
    protected boolean isValidUrl(String url) {
        return Pattern.matches(Patterns.WEB_URL.pattern(), url);
    }

    /**
     * Performs a GET HTTP Request and return the server response in form of a String
     *
     * @return server response
     * @throws IOException
     */
    public String doGetHttpRequest() throws IOException {
        return doHttpRequest("GET");
    }

    /**
     * Performs a POST HTTP Request and return the server response in form of a String
     *
     * @return server response
     * @throws IOException
     */
    public String doPostHttpRequest() throws IOException {
        return doHttpRequest("POST");
    }

    /**
     * Called when the url is set
     *
     * @return the set url
     */
    public abstract String onSetURL();


    /**
     * Called when a successful connection has been created to the server
     *
     * @param statusCode
     */
    public abstract void onConnect(int statusCode);

    /**
     * Called when the query to send to the server has been set
     *
     * @return the query that had been set
     */
    public abstract String onSetQuery();

    /**
     * Called when the set query has been sent to the server
     *
     * @return the sent query which is now appended to the url
     */
    public abstract String onSendQuery();

    /**
     * Called when a response has been received from the server
     */
    public abstract void onReceiveResponse();

    /**
     * Called when the server response has started to be read the received response
     *
     * @param readResponse
     */
    public abstract void onReadResponse(String readResponse);

    /**
     * Called when the current read response has been appended to the previously read response
     *
     * @param appendedResponse
     */
    public abstract void onAppendResponse(String appendedResponse);

    /**
     * Called when the reader has finished reading the response sent from the server
     *
     * @param readResponse
     */
    public abstract void onFinishedReadingResponse(String readResponse);

    /**
     * Called when an error has occurred making a HTTP_CONNECTION
     *
     * @param errorCode
     */
    public abstract void onConnectionError(int errorCode);

    /**
     * AndroidWebServicesProvider#onHTTPResultsFailed
     * <p>
     * Called when a the http results fail
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    public abstract void onHttpResultsFailed(String resultText, String client, String clientAction, String clientIntent, String build);

    /**
     * AndroidWebServicesProvider#onHTTPResultsSuccessful
     * <p>
     * Called when a the http results are successful
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    public abstract void onHttpResultsSuccessful(String resultText, String client, String clientAction, String clientIntent, String build);

    /**
     * AndroidWebServicesProvider#onHttpResultsAmbiguous
     * <p>
     * Called when a the http results are ambiguous
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    public abstract void onHttpResultsAmbiguous(String resultText, String client, String clientAction, String clientIntent, String build);


    /**
     * @return the set http response
     */
    public String getHTTPResponse() {
        return httpResponse;
    }

    /**
     * Set HTTP Response
     *
     * @param httpResponse
     */
    public void setHTTPResponse(String httpResponse) {
        this.httpResponse = httpResponse;
    }

}