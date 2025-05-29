package com.jk.selenium.mcb.ocr;

import org.openqa.selenium.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CaptchaDownloader {

    /**
     * Captures the screenshot of CAPTCHA element located by XPath and saves it to the specified path.
     *
     * @param driver   Selenium WebDriver instance
     * @param xpath    XPath locator string for the CAPTCHA element
     * @param savePath Absolute or relative path where CAPTCHA image will be saved (including filename)
     */
    public static void captureCaptchaImage(WebDriver driver, String xpath, String savePath) {
        try {
            WebElement captchaImg = driver.findElement(By.xpath(xpath));

            // Take screenshot of just the CAPTCHA element (works on Selenium 4+)
            File screenshot = captchaImg.getScreenshotAs(OutputType.FILE);

            // Save the file to the desired location, replacing existing file if any
            Files.copy(screenshot.toPath(), Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✅ CAPTCHA image saved at: " + savePath);
        } catch (NoSuchElementException e) {
            System.err.println("❌ CAPTCHA element not found using XPath: " + xpath);
        } catch (WebDriverException e) {
            System.err.println("❌ WebDriver error capturing CAPTCHA image: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Unexpected error saving CAPTCHA image: " + e.getMessage());
        }
    }
}
