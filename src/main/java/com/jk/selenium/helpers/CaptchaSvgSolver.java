package com.jk.selenium.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CaptchaSvgSolver {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CaptchaSvgSolver(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String readCaptchaFromSvg() {
        StringBuilder captcha = new StringBuilder();
        try {
            // 1. SVG लोड होने का इंतजार करें
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//svg")));

            // 2. सभी टेक्स्ट एलिमेंट्स प्राप्त करें (Fixed XPath syntax)
            List<WebElement> textElements = driver.findElements(
                    By.xpath("//svg//*[local-name()='text' and not(ancestor::*[contains(@style,'display:none')])]")
            );

            // 3. CAPTCHA टेक्स्ट एकत्रित करें
            for (WebElement element : textElements) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    captcha.append(text);
                }
            }

            // 4. वैलिडेशन
            if (captcha.length() == 0) {
                throw new RuntimeException("No CAPTCHA text found in SVG elements");
            }

            return captcha.toString();

        } catch (Exception e) {
            System.err.println("CAPTCHA reading failed: " + e.getMessage());
            // Optional: fallback mechanism if any (e.g., return default or empty string)
            return "";
        }
    }
}
