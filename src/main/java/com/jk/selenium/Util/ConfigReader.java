package com.jk.selenium.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("❌ Failed to load config.properties file. Please check the file path and format.");
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to load config.properties file!", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            System.err.println("⚠️ Config key missing or empty: " + key);
        }
        return value;
    }

    public static String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
