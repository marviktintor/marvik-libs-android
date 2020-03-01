package com.marvik.libs.android.net.http;


import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public abstract class HTTPSWebServicesProvider<K, V> {

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

    public static final String REQUEST_GET = "GET";
    public static final String REQUEST_POST = "POST";
    public static final String REQUEST_PUT = "PUT";
    public static final String REQUEST_DELETE = "DELETE";

    public static final String LINE_FEED = "\r\n";

    /**
     * Web services provide class that provides apis
     * for sending requests to the server and has call
     * backs to handle errors and provide information that is incoming from the server
     *
     * @param url
     * @param query
     * @param requestProperties
     */
    public HTTPSWebServicesProvider(String url, String query, Map<K, V> requestProperties) {
        setQuery(query);
        setUrl(url);
        setRequestProperties(requestProperties);

    }

    /**
     * Web services provide class that provides apis
     * for sending requests to the server and has call
     * backs to handle errors and provide information that is incoming from the server
     *
     * @param urlBuilder
     * @param requestProperties
     */
    public HTTPSWebServicesProvider(URLBuilder urlBuilder, Map<K, V> requestProperties) {
        this.urlBuilder = urlBuilder;
        setQuery(urlBuilder.getQuery());
        setUrl(urlBuilder.getQuery());
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
     * Get wrapped trust managers
     * Returns the first
     *
     * @param trustManagers
     * @return
     */
    protected TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        if (trustManagers.length > 0) {
            X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
            return new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    try {
                        originalTrustManager.checkClientTrusted(chain, authType);
                    } catch (CertificateException exception) {
                        exception.printStackTrace();
                    }

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    try {
                        originalTrustManager.checkServerTrusted(chain, authType);
                    } catch (CertificateException exception) {
                        exception.printStackTrace();
                    }
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return originalTrustManager.getAcceptedIssuers();
                }
            }};
        }
        return null;
    }

    /**
     * Get SSL Socket Factory
     *
     * @return
     */
    protected abstract SSLSocketFactory getSSLSocketFactory();

    /**
     * Get host name verifier
     *
     * @return
     */
    protected abstract HostnameVerifier getHostNameVerifier();

    /**
     * Performs an HTTP Request and return the server response in form of a String
     *
     * @return
     * @throws IOException
     */
    public String doHttpRequest(String requestMethod) throws IOException, JSONException {
        String dataStream = null;

        if (getUrl() == null) {
            onConnectionError(ERROR_TYPE_EMPTY_URL);
            throw new IllegalArgumentException("URL Cannot be null");
        }

        if (!isValidUrl(getUrl())) {
            onConnectionError(ERROR_TYPE_INVALID_URL);
            throw new IllegalArgumentException("Invalid URL [" + getUrl() + "]");
        }

        onStart();

        URL url = new URL(getUrl());
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod(requestMethod);

        httpsURLConnection.setConnectTimeout(getCustomConnectionTimeout());
        httpsURLConnection.setReadTimeout(getCustomReadTimeout());

        httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
        httpsURLConnection.setHostnameVerifier(getHostNameVerifier());

        if (getRequestProperties() != null) {
            for (Map.Entry<K, V> entries : getRequestProperties().entrySet()) {
                httpsURLConnection.setRequestProperty(String.valueOf(entries.getKey()), String.valueOf(entries.getValue()));
            }
        }


        if (requestMethod.equalsIgnoreCase(REQUEST_GET)) {

            httpsURLConnection.setDoInput(true);

            onConnect(httpsURLConnection.getResponseCode());

            InputStream inputStream = httpsURLConnection.getInputStream();

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

            onFinish();

            return dataStream;

        } else if (requestMethod.equalsIgnoreCase(REQUEST_POST) ||
                requestMethod.equalsIgnoreCase(REQUEST_PUT) ||
                requestMethod.equalsIgnoreCase(REQUEST_DELETE)) {

            httpsURLConnection.setDoOutput(true);

            OutputStream outputStream = httpsURLConnection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            if (getQuery() != null) {
                dataOutputStream.writeBytes(getQuery());
                onSendQuery();
            }

            dataOutputStream.flush();
            dataOutputStream.close();

            onConnect(httpsURLConnection.getResponseCode());

            InputStream inputStream = httpsURLConnection.getInputStream();

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

            onFinish();

            httpsURLConnection.disconnect();

            return dataStream;

        } else {
            throw new UnknownServiceException(String.format(Locale.getDefault(), "Unknown request method %s ", requestMethod));
        }


    }

    /**
     * Do Multipart file upload over HTTPS
     *
     * @param fileparams
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public String doMultipartFileUploadsHttps(Map<String, File> fileparams) throws IOException, JSONException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        String dataStream = null;

        if (getUrl() == null) {
            onConnectionError(ERROR_TYPE_EMPTY_URL);
            throw new IllegalArgumentException("URL Cannot be null");
        }

        if (!isValidUrl(getUrl())) {
            onConnectionError(ERROR_TYPE_INVALID_URL);
            throw new IllegalArgumentException("Invalid URL [" + getUrl() + "]");
        }

        onStart();

        URL url = new URL(getUrl());
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        httpsURLConnection.setConnectTimeout(getCustomConnectionTimeout());
        httpsURLConnection.setReadTimeout(getCustomReadTimeout());

        httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
        httpsURLConnection.setHostnameVerifier(getHostNameVerifier());

        httpsURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        if (getRequestProperties() != null) {
            for (Map.Entry<K, V> entries : getRequestProperties().entrySet()) {
                httpsURLConnection.setRequestProperty(String.valueOf(entries.getKey()), String.valueOf(entries.getValue()));
            }
        }

        httpsURLConnection.setUseCaches(true);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setRequestMethod(REQUEST_POST);

        OutputStream outputStream = httpsURLConnection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        Map<String, String> params = getUrlBuilder().getParams();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            writeMultipartTextData(writer, boundary, entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, File> entry : fileparams.entrySet()) {
            writeMultipartFileData(outputStream, writer, boundary, entry.getKey(), entry.getValue());
        }

        writer.append(LINE_FEED).flush();
        writer.append("--").append(boundary).append("--").append(LINE_FEED);
        writer.flush();
        writer.close();

        outputStream.flush();
        outputStream.close();

        onConnect(httpsURLConnection.getResponseCode());

        InputStream inputStream = httpsURLConnection.getInputStream();

        onReceiveResponse();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder builder = new StringBuilder();

        while ((dataStream = bufferedReader.readLine()) != null) {
            onReadResponse(dataStream);

            builder.append(dataStream);
            onAppendResponse(builder.toString());
        }

        inputStream.close();

        dataStream = builder.toString();


        setHTTPResponse(dataStream);

        onFinishedReadingResponse(dataStream);

        onFinish();

        httpsURLConnection.disconnect();

        return dataStream;
    }

    /**
     * Do multipart file upload over HTTP
     *
     * @param fileparams
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public String doMultipartFileUploadsHttp(Map<String, File> fileparams) throws IOException, JSONException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        String dataStream = null;

        if (getUrl() == null) {
            onConnectionError(ERROR_TYPE_EMPTY_URL);
            throw new IllegalArgumentException("URL Cannot be null");
        }

        if (!isValidUrl(getUrl())) {
            onConnectionError(ERROR_TYPE_INVALID_URL);
            throw new IllegalArgumentException("Invalid URL [" + getUrl() + "]");
        }

        onStart();

        URL url = new URL(getUrl());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setConnectTimeout(getCustomConnectionTimeout());
        httpURLConnection.setReadTimeout(getCustomReadTimeout());

        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        if (getRequestProperties() != null) {
            for (Map.Entry<K, V> entries : getRequestProperties().entrySet()) {
                httpURLConnection.setRequestProperty(String.valueOf(entries.getKey()), String.valueOf(entries.getValue()));
            }
        }

        httpURLConnection.setUseCaches(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(REQUEST_POST);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        Map<String, String> params = getUrlBuilder().getParams();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            writeMultipartTextData(writer, boundary, entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, File> entry : fileparams.entrySet()) {
            writeMultipartFileData(outputStream, writer, boundary, entry.getKey(), entry.getValue());
        }

        writer.append(LINE_FEED).flush();
        writer.append("--").append(boundary).append("--").append(LINE_FEED);
        writer.flush();
        writer.close();

        outputStream.flush();
        outputStream.close();

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

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        dataStream = builder.toString();


        setHTTPResponse(dataStream);

        onFinishedReadingResponse(dataStream);

        onFinish();

        httpURLConnection.disconnect();

        return dataStream;
    }

    /**
     * Write Multipart text data
     *
     * @param writer
     * @param boundary
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    private boolean writeMultipartTextData(OutputStreamWriter writer, String boundary, String key, String value) throws IOException {

        writer.append("--").append(boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data;name=" + key).append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=UTF-8").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();

        return true;
    }

    /**
     * Write Multipart file data
     *
     * @param outputStream
     * @param writer
     * @param boundary
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    private boolean writeMultipartFileData(OutputStream outputStream, OutputStreamWriter writer, String boundary, String key, File value) throws IOException {

        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=" + key + "; filename=" + value.getName()).append(LINE_FEED);
        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(value.getName())).append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        FileInputStream fileInputStream = new FileInputStream(value);
        int fileSize = fileInputStream.available();

        byte[] buffer = new byte[fileSize];
        int bytesRead = -1;

        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        fileInputStream.close();

        writer.append(LINE_FEED);
        writer.flush();

        return true;
    }

    /**
     * Returns this is a valid url
     *
     * @param url
     * @return
     */

    protected boolean isValidUrl(String url) {
        return url != null;
    }

    /**
     * Performs a GET HTTP Request and return the server response in form of a String
     *
     * @return server response
     * @throws IOException
     */
    public String doGetHttpRequest() throws IOException, JSONException {
        return doHttpRequest("GET");
    }

    /**
     * Performs a POST HTTP Request and return the server response in form of a String
     *
     * @return server response
     * @throws IOException
     */
    public String doPostHttpRequest() throws IOException, JSONException {
        return doHttpRequest("POST");
    }

    /**
     * Performs a PUT HTTP Request and return the server response in form of a String
     *
     * @return server response
     * @throws IOException
     */
    public String doPutHttpRequest() throws IOException, JSONException {
        return doHttpRequest("PUT");
    }

    /**
     * Performs a DELETE HTTP Request and return the server response in form of a String
     *
     * @return server response
     * @throws IOException
     */
    public String doDeleteHttpRequest() throws IOException, JSONException {
        return doHttpRequest("DELETE");
    }

    /**
     * Called when the url is set
     *
     * @return the set url
     */
    public abstract String onSetURL();

    /**
     * Called when the HTTP Process starts
     */
    public abstract void onStart();


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
     * Called when the HTTP Process ends
     */
    public abstract void onFinish();

    /**
     * Called when an error has occurred making a HTTP_CONNECTION
     *
     * @param errorCode
     */
    public abstract void onConnectionError(int errorCode);

    /**
     * Set connection timeout
     *
     * @return
     */
    protected abstract int getCustomConnectionTimeout();

    /**
     * Set Read timeout
     *
     * @return
     */
    protected abstract int getCustomReadTimeout();

    /**
     * AndroidHTTPSWebServicesProvider#onHTTPResultsFailed
     * <p>
     * Called when a the http results have failed
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    public abstract void onHttpResultsFailed(String resultText, String client, String clientAction, String clientIntent, String build);

    /**
     * AndroidHTTPSWebServicesProvider#onHTTPResultsSuccessful
     * <p>
     * Called when a the http results are successful
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    public abstract void onHttpResultsSuccessful(String resultText, String client, String clientAction, String clientIntent, String build) throws JSONException;

    /**
     * AndroidHTTPSWebServicesProvider#onHttpResultsAmbiguous
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

    /**
     * Set URL Builder
     *
     * @param urlBuilder
     * @return
     */
    public HTTPSWebServicesProvider setURLBuilder(URLBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
        return this;
    }
}