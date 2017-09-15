package com.marvik.libs.android.net.http;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.webkit.URLUtil.isValidUrl;


public abstract class WebServicesProvider {

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
    private String query;

    //The url
    private String url;

    //Context
    private Context context;

    //url builder to help in building url
    private URLBuilder urlBuilder;

    /**
     * Web services provide class that provides apis
     * for sending requests to the server and has call
     * backs to handle errors and provide information that is incoming from the server
     *
     * @param context
     * @param url
     * @param query
     */
    public WebServicesProvider(Context context, String url, String query) {
        this.context = context;
        urlBuilder = new URLBuilder(url);

        setQuery(query);
        setUrl(url);

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

    public Context getContext() {
        return context;
    }

    /**
     * Performs an HTTP Request and return the server response in form of a String
     *
     * @return
     * @throws IOException
     */
    public String performHTTPRequest() throws IOException, JSONException {
        String dataStream = null;

        if (getUrl() == null) {
            onConnectionError(ERROR_TYPE_EMPTY_URL);
            throw new NullPointerException("URL Cannot be null");
        }

        if (!isValidUrl(getUrl())) {
            onConnectionError(ERROR_TYPE_INVALID_URL);
            throw new NullPointerException("Invalid URL ");
        }

        URL url = new URL(getUrl());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);


        OutputStream outputStream = httpURLConnection.getOutputStream();
        //onConnect(httpURLConnection.getResponseCode());
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBytes(getQuery());
        dataOutputStream.flush();
        dataOutputStream.close();
        onSendQuery();

        InputStream inputStream = httpURLConnection.getInputStream();
        onReceiveResponse();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuffer stringBuffer = new StringBuffer();

        while ((dataStream = bufferedReader.readLine()) != null) {
            onReadResponse(dataStream);

            stringBuffer.append(dataStream);
            onAppendResponse(stringBuffer.toString());
        }

        dataStream = stringBuffer.toString();

        onFinishedReadingResponse(dataStream);

        Log.i("SERVER_RESPONSE", dataStream);

        return dataStream;

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
    public abstract void onFinishedReadingResponse(String readResponse) throws JSONException;

    /**
     * Called when an error has occurred making a HTTP_CONNECTION
     *
     * @param errorCode
     */
    public abstract void onConnectionError(int errorCode);

    /**
     * WebServicesProvider#onHTTPResultsFailed
     * <p/>
     * Called when a the http results are successful
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    public abstract void onHTTPResultsFailed(String resultText, String client, String clientAction, String clientIntent, String build);

    /**
     * WebServicesProvider#onHTTPResultsSuccessful
     * <p/>
     * Called when a the http results are successful
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    public abstract void onHTTPResultsSuccessful(String resultText, String client, String clientAction, String clientIntent, String build);
}
