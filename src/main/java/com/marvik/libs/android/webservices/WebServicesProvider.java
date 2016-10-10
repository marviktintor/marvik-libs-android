package com.marvik.libs.android.webservices;

import android.content.Context;
import android.util.Log;

import com.marvik.libs.android.utils.Utilities;
import com.marvik.libs.android.utils.system.SystemUtilities;
import com.marvik.libs.android.webservices.action.HTTPRequestAction;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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

    //Utilities class that allows extra action
    private Utilities utilities;

    //url builder to help in building url
    private URLBuilder urlBuilder;

    //Http Request Action
    private HTTPRequestAction HTTPRequestAction;

    //The HTTP Response
    private String httpResponse;

    private SystemUtilities systemUtilities;

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
        utilities = new Utilities(context);
        urlBuilder = new URLBuilder(url);
        systemUtilities = new SystemUtilities(getContext());

        setQuery(query);
        setUrl(url);

    }

    /**
     * Returns an object of utilities
     *
     * @return utilities
     */
    public Utilities getUtilities() {
        return utilities;
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
     * Get an handle of the system utils to do core Android functions
     *
     * @return systemUtilities
     */
    public SystemUtilities getSystemUtilities() {
        return systemUtilities;
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

        if (!getUtilities().isValidUrl(getUrl())) {
            onConnectionError(ERROR_TYPE_INVALID_URL);
            throw new NullPointerException("Invalid URL [" + getUrl() + "]");
        }

        URL url = new URL(getUrl());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        //onConnect(httpURLConnection.getResponseCode());
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        if (getQuery() != null) {
            dataOutputStream.writeBytes(getQuery());
            onSendQuery();
        }
        dataOutputStream.flush();
        dataOutputStream.close();


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

        setHTTPResponse(dataStream);

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

    /**
     * Set the HTTP Request Action
     *
     * @param HTTPRequestAction the http request action
     */
    public void setHTTPRequestAction(HTTPRequestAction HTTPRequestAction) {
        this.HTTPRequestAction = HTTPRequestAction;
    }

    /**
     * Get the HTTP Request Action
     *
     * @return HTTPRequestAction
     */
    public HTTPRequestAction getHTTPRequestAction() {
        return HTTPRequestAction;
    }

    public String getHTTPResponse() {
        return httpResponse;
    }

    public void setHTTPResponse(String httpResponse) {
        this.httpResponse = httpResponse;
    }

    /**
     * Upload a file to the server
     *
     * @param filePath
     * @return
     */
    public String uploadFile(String filePath) {

        String responseString = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(getUrl());

        try {
            org.apache.http.entity.mime.MultipartEntity entity = new org.apache.http.entity.mime.MultipartEntity();

            File sourceFile = new File(filePath);
            entity.addPart("file", new FileBody(sourceFile));
            entity.addPart(new FormBodyPart("params", new StringBody(getParams())));

            httppost.setEntity(entity);

            HttpResponse response = httpclient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();
            onConnect(statusCode);

            if (statusCode == 200) {
                onReceiveResponse();
                responseString = EntityUtils.toString(response.getEntity());
                onReadResponse(responseString);
                onAppendResponse(responseString);
                onFinishedReadingResponse(responseString);
                onHTTPResultsSuccessful(responseString, null, null, null, null);
            } else {
                String errorMessage = "Error occurred! Http Status Code: " + statusCode;
                onHTTPResultsSuccessful(errorMessage, null, null, null, null);
                throw new IOException(errorMessage);
            }

        } catch (ClientProtocolException e) {
            responseString = e.toString();
        } catch (IOException e) {
            responseString = e.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseString;
    }
}
