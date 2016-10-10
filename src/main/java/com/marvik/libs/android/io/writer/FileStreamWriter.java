package com.marvik.libs.android.io.writer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileStreamWriter {

    /**
     * Writes a text file
     *
     * @param file
     * @param text
     * @throws IOException
     */
    public void writeStream(File file, String text) throws IOException {

        // Check if directory exists
        if (!file.exists()) {

			/*
             * Create all missing directories - omit the file name when passing
			 * the directory file name.
			 */
            createDirectories(new File(file.getParent()));
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        if (text == null) {
            return;
        }
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.write(text);
        osw.flush();
        osw.close();
        fos.close();

        System.out.println("Created file [" + file.getAbsolutePath() + "]");
    }

    /**
     * Create Directories
     *
     * @param file
     * @return true if directory is created
     */
    public boolean createDirectories(File file) {
        return file.mkdirs();
    }

    /**
     * Create Directories
     *
     * @param directoryPath
     * @return true if directory is created
     */
    public boolean createDirectories(String directoryPath) {
        return createDirectories(new File(directoryPath));
    }

    /**
     * Creates a file if the file does not exist
     */
    public final File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }
        file.createNewFile();

        return file;
    }

    /**
     * Deletes a file if the file exists
     */
    public final boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    /**
     * Deletes a file if the file exists
     */
    public final boolean deleteFile(File file) {

        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }

    /**
     * Creates a file on the disk
     *
     * @param oldFile
     * @param newFile
     */
    public void copyFile(File oldFile, File newFile) {
        copyFile(oldFile.getAbsoluteFile(), newFile.getAbsoluteFile());
    }

    /**
     * Creates a file on the disk
     *
     * @param oldFile
     * @param newFile
     */
    public void copyFile(String oldFile, String newFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(oldFile));
            int count = 0;
            byte[] buffer = new byte[1024];

            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            FileOutputStream fileOutputStream = new FileOutputStream(new File(newFile));

            while ((count = bufferedInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, count);
            }

            fileOutputStream.close();

            bufferedInputStream.close();

            fileInputStream.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
