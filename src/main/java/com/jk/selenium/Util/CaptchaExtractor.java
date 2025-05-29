package com.jk.selenium.Util;

import org.openqa.selenium.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CaptchaExtractor {

    /**
     * Captures screenshot of the CAPTCHA element and saves it to the given filePath.
     * @param driver Selenium WebDriver instance
     * @param captchaElement WebElement of the CAPTCHA image
     * @param filePath Absolute or relative path where cropped CAPTCHA image should be saved
     * @throws IOException If any file IO error occurs
     */
    public static void captureCaptchaImage(WebDriver driver, WebElement captchaElement, String filePath) throws IOException {
        // Full page screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        // Get location and size of CAPTCHA element
        Point point = captchaElement.getLocation();
        int eleWidth = captchaElement.getSize().getWidth();
        int eleHeight = captchaElement.getSize().getHeight();

        // Crop the CAPTCHA image
        BufferedImage captchaImg = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);

        // Save the cropped image to filePath
        ImageIO.write(captchaImg, "png", new File(filePath));

        System.out.println("✅ Captcha cropped and saved at: " + filePath);
    }

    // ConfigReader should ideally be in separate file, but for now keeping here per your snippet
    public static class ConfigReader {
        private static final Properties properties = new Properties();

        static {
            try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
                properties.load(fis);
            } catch (IOException e) {
                System.err.println("⚠️ Failed to load config.properties: " + e.getMessage());
                // You can also rethrow RuntimeException if you want to fail fast
                // throw new RuntimeException("Failed to load config.properties", e);
            }
        }

        public static String getProperty(String key) {
            String value = properties.getProperty(key);
            if (value == null) {
                System.err.println("⚠️ Property key not found: " + key);
            }
            return value;
        }
    }
}
