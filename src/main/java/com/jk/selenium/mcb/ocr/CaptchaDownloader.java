package com.jk.selenium.mcb.ocr;

import org.openqa.selenium.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CaptchaDownloader {

    public static void captureCaptchaImage(WebDriver driver, String xpath, String savePath) {
        try {
            WebElement captchaImg = driver.findElement(By.xpath(xpath));
            File screenshot = captchaImg.getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("CAPTCHA image saved at: " + savePath);
        } catch (Exception e) {
            System.err.println("Error saving CAPTCHA image: " + e.getMessage());
        }
    }
}
