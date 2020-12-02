package com.paylocity.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Buffer {

	static String fileName = Constants.PROP_PATH;

	public static void checkFilePresent(String fileName) throws IOException {
		try {

			FileReader file = new FileReader(fileName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			FileOutputStream fos;
			fos = new FileOutputStream(fileName);
			fos.close();
		}

	}

	public static void writeToBuffer(String key, String value) throws IOException {
		checkFilePresent(fileName);
		Properties prop = new Properties();
		prop.load(new FileInputStream(fileName));
		prop.put(key, value);
		FileOutputStream fileOut = new FileOutputStream(fileName);
		prop.store(fileOut, "");
		fileOut.close();
	}

	public static String readFromBuffer(String key) throws IOException {
		checkFilePresent(fileName);
		FileReader file = new FileReader(fileName);
		Properties prop = new Properties();
		prop.load(file);
		if (prop.getProperty(key) == null) {
			System.out.println("Key is not Present");
			return null;
		}
		return prop.getProperty(key);
	}
}
