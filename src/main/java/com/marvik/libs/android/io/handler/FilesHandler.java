package com.marvik.libs.android.io.handler;

import com.marvik.libs.android.io.reader.FileStreamReader;
import com.marvik.libs.android.io.writer.FileStreamWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FilesHandler {

    private FileStreamReader fileStreamReader;
    private FileStreamWriter fileStreamWriter;

    /**
     * Perform various I/O functions
     */
    public FilesHandler() {
        fileStreamReader = new FileStreamReader();
        fileStreamWriter = new FileStreamWriter();
    }

    /**
     * getFileStreamReader
     *
     * @return FileStreamReader
     */
    public FileStreamReader getFileStreamReader() {
        return fileStreamReader;
    }

    /**
     * getFileStreamWriter
     *
     * @return FileStreamWriter
     */
    public FileStreamWriter getFileStreamWriter() {
        return fileStreamWriter;
    }

    /**
     * Creates a file if the file does not exist
     */
    public final File createFile(String filePath) throws IOException {
        return getFileStreamWriter().createFile(filePath);
    }

    /**
     * Deletes a file if the file exists
     */
    public final boolean deleteFile(String filePath) {
        return getFileStreamWriter().deleteFile(filePath);
    }

    /**
     * Deletes a file if the file exists
     */
    public final boolean deleteFile(File file) {
        return getFileStreamWriter().deleteFile(file);
    }

    /**
     * Writes a text file
     *
     * @param file
     * @param text
     * @throws IOException
     */
    public void writeStream(File file, String text) throws IOException {
        getFileStreamWriter().writeStream(file, text);
    }

    public File writeStream(final File file, final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(data, 0, data.length);
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return file;
    }

    /**
     * Create Directories
     *
     * @param file
     * @return true if directory is created
     */
    public boolean createDirectories(File file) {
        return getFileStreamWriter().createDirectories(file);
    }

    /**
     * Create Directories
     *
     * @param directoryPath
     * @return true if directory is created
     */
    public boolean createDirectories(String directoryPath) {
        return getFileStreamWriter().createDirectories(new File(directoryPath));
    }

    /**
     * Reads the contents of a file
     */
    public final String readFile(File file) throws IOException {
        return getFileStreamReader().readFile(file);
    }

    /**
     * Reads the contents of a file
     */
    public final String readFile(String filePath) throws IOException {
        return getFileStreamReader().readFile(new File(filePath), true);
    }

    /**
     * Reads the contents of a file, add new file
     */
    public final String readFile(String filePath, boolean addNewLines) throws IOException {
        return getFileStreamReader().readFile(new File(filePath), addNewLines);
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
    public boolean createByteWeighedFile(String filePath, String fileData) {

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
    public File[] getDirectoryFiles(String directory) {
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
    public void copyFile(String from, String to) {
        fileStreamWriter.copyFile(from, to);

    }

    /**
     * Copy a file to a new file path
     *
     * @param from
     * @param to
     */
    public void copyFile(File from, File to) {
        fileStreamWriter.copyFile(from, to);

    }

    /**
     * Checks if this file exists
     *
     * @param file the file
     * @return isExists
     */
    public boolean isExists(File file) {
        return file.exists();
    }

    /**
     * Get the file as an input stream
     *
     * @param file to read
     * @return
     */
    public InputStream getFileStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
