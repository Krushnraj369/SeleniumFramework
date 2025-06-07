package com.jk.selenium.Pages.AdminPanelPages;

import com.jk.selenium.mcb.ocr.OCRUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class LoginPage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    private final WebDriver driver;

    @FindBy(css = "input[name='userName']")
    private WebElement usernameField;

    @FindBy(css = "input[name='password']")
    private WebElement passwordField;

    @FindBy(css = "button.glow-on-hover")
    private WebElement loginButton;

    @FindBy(css = "img#captchaImage")  // Adjust the selector based on your actual page
    private WebElement captchaImage;

    @FindBy(css = "input[name='captcha']")
    private WebElement captchaInput;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Enter username
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
        logger.info("✅ Entered username: {}", username);
    }

    // Enter password
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        logger.info("✅ Entered password");
    }

    // Enter captcha text
    public void enterCaptcha(String captchaText) {
        captchaInput.clear();
        captchaInput.sendKeys(captchaText);
        logger.info("✅ Entered captcha text: {}", captchaText);
    }

    // Click login button
    public void clickLoginButton() {
        loginButton.click();
        logger.info("✅ Clicked login button");
    }

    /**
     * Capture captcha image and save as file.
     * @return File object pointing to saved captcha image.
     * @throws IOException if saving fails.
     */
    public File captureCaptchaImage() throws IOException {
        File srcFile = captchaImage.getScreenshotAs(OutputType.FILE);
        File destFile = new File(System.getProperty("user.dir") + "/captcha.png");
        FileUtils.copyFile(srcFile, destFile);
        logger.info("✅ Captured captcha image at: {}", destFile.getAbsolutePath());
        return destFile;
    }

    /**
     * Read captcha text using OCRUtil.
     * @return Extracted captcha text, or empty string on failure.
     */
    public String readCaptchaUsingOCR() {
        try {
            File captchaFile = captureCaptchaImage();
            String captchaText = OCRUtil.getCaptchaText(captchaFile);
            logger.info("✅ Extracted captcha text via OCR: {}", captchaText);
            return captchaText;
        } catch (IOException e) {
            logger.error("❌ Error while capturing captcha image", e);
            return "";
        } catch (Exception e) {
            logger.error("❌ OCR extraction failed", e);
            return "";
        }
    }

    // Getters for elements (optional)
    public WebElement getUsernameField() {
        return usernameField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getCaptchaImage() {
        return captchaImage;
    }

    public WebElement getCaptchaInput() {
        return captchaInput;
    }

    public void loginToMCB() {
    }
}