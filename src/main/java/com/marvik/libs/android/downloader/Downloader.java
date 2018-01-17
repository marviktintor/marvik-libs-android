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
import java.net.URLConnection;


public final class Downloader {

    public static final String EXTRA_FILEPATH = "filepath";

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
    public static void downloadFile(final Context context, final String downloadURI, final String storeDir,
                                    @Nullable String fileName, final boolean overWrite, final Intent downloadIntent) {

        new Thread(() -> {
            try {
                URL url = new URL(parseUrl(downloadURI));
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

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
