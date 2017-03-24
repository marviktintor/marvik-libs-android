package com.marvik.libs.android.downloader;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Downloader
 * Provides an API to download files and save the downloaded file to the file system
 */
public final class Downloader {

    private static Context context;

    private Downloader() {
    }

    /**
     * Returns an instance of this class allowing singleton access
     *
     * @param context
     * @return
     */
    public static Downloader getInstance(Context context) {
        Downloader.context = context;
        return new Downloader();
    }

    /**
     * Download file and save on the file path
     *
     * @param fileUri       the url of the file
     * @param filePath      the storage path of the file
     * @param successIntent success intent to send when file downloaded and saved
     * @param errorIntent   error intent to send when file download fails
     */
    public void downloadFile(final String fileUri, final String filePath, Intent successIntent,
                             Intent errorIntent) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(fileUri);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();

                    String filename = new File(fileUri).getName();

                    int count = 0;
                    byte[] buffer = new byte[1024];

                    File fileDir = new File(filePath).getParentFile();

                    if (!fileDir.exists()) {
                        fileDir.mkdirs();
                    }

                    File downloadFile = new File(fileDir + filename);

                    //Ensure that we do not always download existing files
                    if (downloadFile.exists()) {
                        return;
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(downloadFile);

                    while ((count = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, count);
                    }

                    context.sendBroadcast(successIntent);

                } catch (MalformedURLException e) {

                    e.printStackTrace();

                    errorIntent.putExtra("error", e.getMessage());
                    context.sendBroadcast(errorIntent);

                } catch (IOException e) {

                    e.printStackTrace();

                    errorIntent.putExtra("error", e.getMessage());
                    context.sendBroadcast(errorIntent);

                }
            }
        }).start();

    }
}
