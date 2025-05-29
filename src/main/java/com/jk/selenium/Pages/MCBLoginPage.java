package com.jk.selenium.Pages;

import com.jk.selenium.mcb.ocr.OCRUtil;
import com.jk.selenium.Util.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class MCBLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By corporateIdField = By.cssSelector("input[placeholder='Enter Corporate ID']");
    private final By loginIdField = By.cssSelector("input[placeholder='Enter Login ID']");
    private final By passwordField = By.cssSelector("input[placeholder='Enter Password']");
    private final By captchaField = By.cssSelector("input[placeholder='Enter Captcha']");
    private final By captchaImage = By.cssSelector("img[alt='Captcha verification']");
    private final By loginButton = By.cssSelector(".group");
    private final By loginForm = By.cssSelector(".mx-auto.mt-0");

    public MCBLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Input methods
    public void enterCorporateId(String corpId) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(corporateIdField));
        element.clear();
        element.sendKeys(corpId);
    }

    public void enterLoginId(String loginId) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loginIdField));
        element.clear();
        element.sendKeys(loginId);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    public void enterCaptcha(String captchaText) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaField));
        element.clear();
        element.sendKeys(captchaText);
    }

    public void clickLogin() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        button.click();
    }

    public By getLoginFormLocator() {
        return loginForm;
    }

    /**
     * Capture captcha image screenshot and use OCR to extract text.
     *
     * @return Captcha text or empty string if extraction fails.
     */
    public String readCaptchaTextUsingOCR() {
        try {
            WebElement captchaImgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaImage));
            File screenshot = captchaImgElement.getScreenshotAs(OutputType.FILE);

            String captchaText = OCRUtil.getCaptchaText(screenshot);

            System.out.println("Extracted CAPTCHA via OCR: " + captchaText);

            return captchaText != null ? captchaText.trim() : "";
        } catch (TimeoutException te) {
            System.err.println("Timeout waiting for captcha image element.");
            return "";
        } catch (Exception e) {
            System.err.println("Exception while reading CAPTCHA via OCR:");
            e.printStackTrace();
            return "";
        }
    }
}
