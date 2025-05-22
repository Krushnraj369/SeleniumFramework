package com.jk.selenium.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load config.properties file!", e);
        }
    }

    // Basic getter
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Optional getter with default
    public static String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
