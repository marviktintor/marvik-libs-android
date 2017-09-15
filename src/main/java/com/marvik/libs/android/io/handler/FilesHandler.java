package com.marvik.libs.android.io.handler;

import android.content.Context;

import com.marvik.libs.android.io.reader.FileStreamReader;
import com.marvik.libs.android.io.writer.FileStreamWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FilesHandler {

    /**
     * getFileStreamReader
     *
     * @return FileStreamReader
     */
    public static FileStreamReader getFileStreamReader() {
        return new FileStreamReader();
    }

    /**
     * getFileStreamWriter
     *
     * @return FileStreamWriter
     */
    public static FileStreamWriter getFileStreamWriter() {
        return new FileStreamWriter();
    }

    /**
     * Creates a file if the file does not exist
     */
    public static final File createFile(String filePath) throws IOException {
        return FileStreamWriter.createFile(filePath);
    }

    /**
     * Deletes a file if the file exists
     */
    public static final boolean deleteFile(String filePath) {
        return FileStreamWriter.deleteFile(filePath);
    }

    /**
     * Deletes a file if the file exists
     */
    public static final boolean deleteFile(File file) {
        return FileStreamWriter.deleteFile(file);
    }

    /**
     * Writes a text file
     *
     * @param file
     * @param text
     * @throws IOException
     */
    public static void writeStream(File file, String text) throws IOException {
        FileStreamWriter.writeStream(file, text);
    }

    /**
     * Create Directories
     *
     * @param file
     * @return true if directory is created
     */
    public static boolean createDirectories(File file) {
        return FileStreamWriter.createDirectories(file);
    }

    /**
     * Create Directories
     *
     * @param directoryPath
     * @return true if directory is created
     */
    public static boolean createDirectories(String directoryPath) {
        return FileStreamWriter.createDirectories(new File(directoryPath));
    }

    /**
     * Reads the contents of a file
     */
    public static final String readFile(File file) throws IOException {
        return FileStreamReader.readFile(file);
    }

    /**
     * Reads the contents of a file
     */
    public static final String readFile(String filePath) throws IOException {
        return FileStreamReader.readFile(new File(filePath), true);
    }

    /**
     * Reads the contents of a file, add new file
     */
    public static final String readFile(String filePath, boolean addNewLines) throws IOException {
        return FileStreamReader.readFile(new File(filePath), addNewLines);
    }

    /**
     * Create Byte Weighed File - Creates a file and returns true if the written
     * data equals the data that was actually to be written
     *
     * @param filePath
     * @param fileData
     * @return
     * @throws IOException
     */
    public static boolean createByteWeighedFile(String filePath, String fileData) {

        try {
            // Write stream
            writeStream(new File(filePath), fileData);

            // Compare written data on disk and passed data
            return (new File(filePath)).exists() && readFile(filePath).contains(fileData);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets all the files in a directory
     *
     * @param directory
     * @return File Array
     */
    public static File[] getDirectoryFiles(String directory) {
        File file = new File(directory);
        if (file.isDirectory()) {
            return file.listFiles();
        }
        return null;
    }

    /**
     * Copy a file to a new file path
     *
     * @param from
     * @param to
     */
    public static void copyFile(String from, String to) {
        FileStreamWriter.copyFile(from, to);

    }

    /**
     * Copy a file to a new file path
     *
     * @param from
     * @param to
     */
    public static void copyFile(File from, File to) {
        FileStreamWriter.copyFile(from, to);

    }

    /**
     * Checks if this file path actually exists
     *
     * @param filePath
     * @return is exists
     */
    public static boolean isExists(String filePath) {
        return (new File(filePath)).exists();
    }

    /**
     * Get an asset
     *
     * @param assetPath
     * @return
     * @throws IOException
     */
    public static InputStream getAsset(Context context, String assetPath) throws IOException {
        return context.getResources().getAssets().open(assetPath);
    }

    /**
     * Reads a String stream from the assets
     *
     * @param assetFilePath
     * @return
     */
    public static String readAssetsStringStream(Context context, String assetFilePath) {
        try {
            InputStream inputStream = FilesHandler.getAsset(context, assetFilePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String readString = "";
            while ((readString = bufferedReader.readLine()) != null) {
                stringBuffer.append(readString);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
