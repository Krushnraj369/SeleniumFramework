package com.jk.selenium.Util;

import org.openqa.selenium.*;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CaptchaExtractor {

    /**
     * Captcha image ko download kar ke local file mein save karta hai.
     *
     * @param driver Selenium WebDriver instance
     * @param fileName File ka naam jisme captcha save karna hai (e.g. "captcha.png")
     * @throws IOException Agar file save nahi ho payi
     */
    public static void download(WebDriver driver, String fileName) throws IOException {
        try {
            // Locate captcha image element (update selector as per your page)
            WebElement captchaImg = driver.findElement(By.cssSelector("img[alt='Captcha verification']"));

            // Screenshot le ke temporary file bana lo
            File screenshot = captchaImg.getScreenshotAs(OutputType.FILE);

            // Destination file
            File destination = new File(System.getProperty("user.dir") + "/" + fileName);

            // Temporary screenshot ko destination par copy karo
            Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (NoSuchElementException e) {
            throw new IOException("Captcha image element not found", e);
        } catch (WebDriverException e) {
            throw new IOException("Failed to capture captcha image", e);
        }
    }
}
