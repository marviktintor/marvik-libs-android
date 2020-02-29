package com.marvik.libs.android.downloader;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;


public abstract class Downloader {

    public static final String EXTRA_FILEPATH = "filepath";

    private Context mContext;

    public Downloader(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
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
     * Called when a successful connection has been created to the server
     *
     * @param statusCode
     */
    public abstract void onConnect(int statusCode);

    /**
     * Called when an error has occurred making a HTTP_CONNECTION
     *
     * @param errorCode
     */
    public abstract void onConnectionError(int errorCode);

    /**
     * Downloads a file
     *
     * @param context
     * @param downloadURI
     * @param storeDir
     * @param fileName
     * @param overWrite
     * @param downloadIntent
     */
    public void downloadFile(final Context context, final String downloadURI, final String storeDir,
                             @Nullable String fileName, final boolean overWrite, final Intent downloadIntent, final Intent failedIntent) {

        new Thread(() -> {
            try {
                URL url = new URL(parseUrl(downloadURI));
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

                httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
                httpsURLConnection.setHostnameVerifier(getHostNameVerifier());

                int responseCode = httpsURLConnection.getResponseCode();

                onConnect(responseCode);

                if (responseCode == 200) {
                    InputStream inputStream = httpsURLConnection.getInputStream();

                    File downloadFile = new File(storeDir + File.separator + fileName);

                    int count = 0;

                    byte[] buffer = new byte[1024];

                    File fileDir = new File(storeDir);

                    if (!fileDir.exists()) {
                        fileDir.mkdirs();
                    }

                    if (!overWrite) {
                        //Ensure that we do not always download existing files
                        if (downloadFile.exists()) {
                            return;
                        }
                    }


                    FileOutputStream fileOutputStream = new FileOutputStream(downloadFile);

                    while ((count = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    downloadIntent.putExtra(Downloader.EXTRA_FILEPATH, downloadFile.getAbsolutePath());
                    context.sendBroadcast(downloadIntent);
                } else {
                    context.sendBroadcast(failedIntent);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /**
     * Parse fileUri
     * Replaces bad format characters like an empty space from a file uri
     *
     * @param fileUri
     * @return
     */
    public static String parseUrl(String fileUri) {
        return fileUri.replace(" ", "%20");
    }
}
