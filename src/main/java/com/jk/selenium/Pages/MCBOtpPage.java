package com.jk.selenium.Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MCBOtpPage {
    private static final Logger logger = LogManager.getLogger(MCBOtpPage.class);
    private final WebDriver driver;

    // Locators based on provided HTML
    @FindBy(css = "#otp input")
    private List<WebElement> otpFields;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//span[contains(text(),'Resend')]")
    private WebElement resendOtpLink;

    // Constructor
    public MCBOtpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Getters
    public List<WebElement> getOtpFields() {
        return otpFields;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getResendOtpLink() {
        return resendOtpLink;
    }

    /**
     * Enter OTP digits into each OTP input field.
     * @param otp OTP string (e.g., "1234")
     */
    public void enterOtpDigits(String otp) {
        try {
            if (otp == null || otp.trim().isEmpty()) {
                logger.warn("⚠️ OTP input is empty or null.");
                return;
            }

            otp = otp.trim();
            int fieldCount = otpFields.size();
            int otpLength = otp.length();

            logger.info("ℹ️ OTP Fields detected: {}, OTP length: {}", fieldCount, otpLength);

            if (fieldCount != otpLength) {
                logger.warn("⚠️ OTP length ({}) does not match number of OTP fields ({}).", otpLength, fieldCount);
            }

            for (int i = 0; i < otpLength && i < fieldCount; i++) {
                WebElement field = otpFields.get(i);
                field.click();       // focus field
                field.clear();       // clear any default value ("0")
                field.sendKeys(String.valueOf(otp.charAt(i)));
            }

            logger.info("✅ Entered OTP '{}' successfully.", otp);

        } catch (Exception e) {
            logger.error("❌ Error while entering OTP digits.", e);
            throw e;
        }
    }

    /**
     * Click the Login button to submit OTP.
     */
    public void clickLoginButton() {
        try {
            if (loginButton.isDisplayed() && loginButton.isEnabled()) {
                loginButton.click();
                logger.info("✅ Clicked Login button successfully.");
            } else {
                logger.warn("⚠️ Login button is not clickable (check page load/state).");
            }
        } catch (Exception e) {
            logger.error("❌ Failed to click Login button.", e);
            throw e;
        }
    }

    /**
     * Click the Resend OTP link if needed.
     */
    public void clickResendOtp() {
        try {
            if (resendOtpLink.isDisplayed() && resendOtpLink.isEnabled()) {
                resendOtpLink.click();
                logger.info("✅ Clicked Resend OTP link successfully.");
            } else {
                logger.warn("⚠️ Resend OTP link is not clickable.");
            }
        } catch (Exception e) {
            logger.error("❌ Failed to click Resend OTP link.", e);
            throw e;
        }
    }
}
