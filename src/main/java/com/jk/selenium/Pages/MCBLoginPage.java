package com.jk.selenium.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.OutputType;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.time.Duration;

public class MCBLoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By corporateIdField = By.xpath("//input[@placeholder='Enter Corporate ID']");
    private final By loginIdField = By.xpath("//input[@placeholder='Enter Login ID']");
    private final By passwordField = By.xpath("//input[@placeholder='Enter Password']");
    private final By captchaField = By.xpath("//input[@placeholder='Enter Captcha']");
    private final By loginButton = By.xpath("//button[@type='submit' and contains(@class, 'bg-primary') and contains(@class, 'text-light')]");
    private final By captchaImage = By.xpath("//img[@alt='Captcha verification']");

    public MCBLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterCorporateId(String corporateId) {
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(corporateIdField));
        ele.clear();
        ele.sendKeys(corporateId);
    }

    public void enterLoginId(String loginId) {
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(loginIdField));
        ele.clear();
        ele.sendKeys(loginId);
    }

    public void enterPassword(String password) {
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        ele.clear();
        ele.sendKeys(password);
    }

    public void enterCaptcha(String captcha) {
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaField));
        ele.clear();
        ele.sendKeys(captcha);
    }

    public void clickLogin() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        btn.click();
    }

    /**
     * Captures screenshot of the captcha image element and saves it as "captcha.png"
     * @return File object pointing to the saved captcha image file
     * @throws IOException if file saving fails
     */
    public File captureCaptchaImage() throws IOException {
        WebElement captchaImg = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaImage));
        File src = captchaImg.getScreenshotAs(OutputType.FILE);
        File dest = new File("captcha.png");
        FileUtils.copyFile(src, dest);
        return dest;
    }
}
