package com.jk.selenium.Pages.MCBPages;

import com.jk.selenium.mcb.ocr.OCRUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;

public class MCBLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By corporateIdField = By.cssSelector("input[placeholder='Enter Corporate ID']");
    private final By loginIdField = By.cssSelector("input[placeholder='Enter Login ID']");
    private final By passwordField = By.cssSelector("input[placeholder='Enter Password']");
    private final By captchaField = By.cssSelector("input[placeholder='Enter Captcha']");
    private final By captchaImage = By.cssSelector("img[alt='Captcha verification']");
    private final By loginButton = By.cssSelector(".group");
    private final By loginForm = By.cssSelector(".mx-auto.mt-0");

    public MCBLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

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
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(captchaField));
        element.clear();

        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);

        try { Thread.sleep(200); } catch (InterruptedException e) {}

        element.sendKeys(captchaText);

        try { Thread.sleep(300); } catch (InterruptedException e) {}

        String entered = element.getAttribute("value");
        System.out.println("Entered Captcha: " + entered);

        // If sendKeys failed, fallback with JS value set
        if (!entered.equalsIgnoreCase(captchaText)) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, captchaText);
            System.out.println("Captcha input set by JS fallback");
        }
    }

    public void clickLogin() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        button.click();
    }

    public By getLoginFormLocator() {
        return loginForm;
    }

    public WebElement getLoginForm() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginForm));
    }

    public String readCaptchaTextUsingOCR() {
        try {
            WebElement captchaImgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaImage));
            Thread.sleep(500); // wait for image stability

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
}