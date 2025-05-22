package com.jk.selenium.Util;

import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CaptchaExtractor {

    public static void captureCaptchaImage(WebDriver driver, WebElement captchaElement, String filePath) throws Exception {
        // Step 1: Full page screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        // Step 2: Get location and size of the captcha
        Point point = captchaElement.getLocation();
        int eleWidth = captchaElement.getSize().getWidth();
        int eleHeight = captchaElement.getSize().getHeight();

        // Step 3: Crop captcha from full image
        BufferedImage captchaImg = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(captchaImg, "png", new File(filePath));

        System.out.println("✅ Cropped captcha saved at: " + filePath);
    }

    public static class ConfigReader {
        private static final Properties properties = new Properties();

        static {
            try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
                properties.load(fis);
            } catch (IOException e) {
                System.err.println("⚠️ Failed to load config.properties: " + e.getMessage());
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
