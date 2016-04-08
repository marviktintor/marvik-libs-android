package com.marvik.libs.android.io.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class FileStreamReader {

	/**
	 * Reads the contents of a file, add new file
	 */
	public final String readFile(File file, boolean addNewLines) throws IOException {
		if (!file.exists()) {
			throw new IOException("The file [" + file.getAbsolutePath() + "] does not exist");
		}

		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder stringBuilder = new StringBuilder();
		String lineSeparator = System.getProperty("line.separator");
		String readString = "";
		while ((readString = bufferedReader.readLine()) != null) {
			stringBuilder.append(readString);
			if (addNewLines) {
				stringBuilder.append(lineSeparator);
			}
		}
		bufferedReader.close();
		inputStreamReader.close();
		fileInputStream.close();
		return stringBuilder.toString();
	}

	/**
	 * Reads the contents of a file line by line and appends the new lines
	 */
	public final String readFile(String filePath, boolean addNewLines) throws IOException {
		return readFile(new File(filePath), true);
	}

	/**
	 * Reads the contents of a file and automatically skips new lines
	 */
	public final String readFile(File file) throws IOException {
		return readFile(file, false);
	}

	/**
	 * Reads the contents of a file
	 */
	public final String readFile(String filePath) throws IOException {
		if (filePath == null) {
			throw new NullPointerException("Template file path cannot be null");
		}
		return readFile(new File(filePath));
	}

}
