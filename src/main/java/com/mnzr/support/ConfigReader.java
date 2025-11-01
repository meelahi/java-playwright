package com.mnzr.support;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties props = new Properties();

    static {
        String path = "src/test/resources/config.properties";
        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
            Log.info("Loaded configuration from: " + path);
        } catch (IOException e) {
            System.err.println("Failed to load config file: " + e.getMessage());
        }
    }

    private ConfigReader() {}

	public static String get(String key, String... defaultValue) {
		String value = System.getProperty(key);
		if (value != null && !value.isEmpty()) {
			return value;
		}
		return props.getProperty(key, defaultValue.length > 0 ? defaultValue[0] : "");
	}

	public static int getInt(String key, int... defaultValue) {
		try {
			return Integer.parseInt(get(key));
		} catch (NumberFormatException e) {
			return defaultValue.length > 0 ? defaultValue[0] : 0;
		}
	}

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
