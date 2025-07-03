package ru.wg.web.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class AppParameters {

	public static final String APP_REAL_PATH = "app.realPath";

	private static Properties properties = null;

	public static String getProperty(String key) {
		return getProperties().getProperty(key);
	}

	public static void setProperty(String key, String value) {
		getProperties().setProperty(key, value);
	}

	public static int getIntProperty(String key, int value) {
		String v = getProperties().getProperty(key);
		if (v == null)
			return value;
		return Integer.valueOf(v);
	}

	public static void init(String fileName) throws IOException {
		properties = new Properties();
		InputStreamReader fis = new InputStreamReader(new FileInputStream(fileName), "utf-8");
		properties.load(fis);
		fis.close();
	}

	private static Properties getProperties() {
		if (properties == null)
			properties = new Properties();
		return properties;
	}
}
