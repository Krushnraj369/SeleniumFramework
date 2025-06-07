package com.jk.selenium.Pages.IBMBPages;

import com.jk.selenium.mcb.ocr.OCRUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;

public class IBMBLoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By loginIdField = By.cssSelector("input[placeholder='Enter Login ID']");
    private final By passwordField = By.cssSelector("input[placeholder='Enter Password']");
    private final By captchaField = By.cssSelector("input[placeholder='Enter Captcha']");
    private final By captchaImage = By.cssSelector("img[alt='Captcha verification']");
    private final By loginButton = By.cssSelector(".group");
   // private final By loginForm = By.cssSelector(".mx-auto.mt-0");
    private final By beneficiaryLink = By.cssSelector("a[href='/beneficiary']");  // Added beneficiary locator

    public IBMBLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Enters the login ID into the login ID field.
     * @param loginId Login ID string to enter
     */
    public void enterLoginId(String loginId) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loginIdField));
        element.clear();
        element.sendKeys(loginId);
    }

    /**
     * Enters the password into the password field.
     * @param password Password string to enter
     */
    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    /**
     * Enters the CAPTCHA text into the CAPTCHA input field, with JS fallback.
     * @param captchaText CAPTCHA string to enter
     */
    public void enterCaptcha(String captchaText) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(captchaField));
        element.clear();

        // Focus using JS and small wait to stabilize before typing
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
        sleepMillis(200);
        element.sendKeys(captchaText);
        sleepMillis(300);

        // Verify input; if mismatch, set via JS fallback
        String enteredValue = element.getAttribute("value");
        System.out.println("Entered Captcha: " + enteredValue);

        if (!enteredValue.equalsIgnoreCase(captchaText)) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, captchaText);
            System.out.println("Captcha input set by JS fallback");
        }
    }

    /**
     * Clicks the Login button.
     */
    public void clickLogin() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        button.click();
    }

    /**
     * Returns the locator for the login form container.
     */


    /**
     * Returns the login form WebElement.
     */


    /**
     * Reads the CAPTCHA text using OCR from the CAPTCHA image.
     * @return Extracted CAPTCHA string, or empty string if failed
     */
    public String readCaptchaTextUsingOCR() {
        try {
            WebElement captchaImgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaImage));
            sleepMillis(500);

            File screenshot = captchaImgElement.getScreenshotAs(OutputType.FILE);
            BufferedImage bufferedImage = ImageIO.read(screenshot);

            String captchaText = OCRUtil.getCaptchaTextFromBufferedImage(bufferedImage);

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

    /**
     * Clicks on Beneficiary link.
     */
    public void clickBeneficiaryLink() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(beneficiaryLink));
        element.click();
    }

    /**
     * Utility method to sleep without checked exception clutter.
     * @param millis milliseconds to sleep
     */
    private void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    // You can add retryCaptcha, extractCaptchaUsingOCR, clickLoginButton etc. as needed below

}
