package com.jk.selenium.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH);
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            properties.load(reader);
            System.out.println("✅ Config properties loaded from: " + CONFIG_PATH);
        } catch (IOException e) {
            System.err.println("❌ Failed to load config.properties file at: " + CONFIG_PATH);
            e.printStackTrace();
            throw new RuntimeException("❌ Could not load config.properties. Please check the file path and format.", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            System.err.println("⚠️ Missing or empty config value for key: " + key);
        }
        return value;
    }

    public static String getOrDefault(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            System.out.println("ℹ️ Using default value for key [" + key + "]: " + defaultValue);
            return defaultValue;
        }
        return value;
    }

    // New method to get OTP directly
    public static String getOtp() {
        return getProperty("mcb.otp");
    }
}
