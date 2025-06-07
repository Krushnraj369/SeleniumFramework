package com.jk.selenium.Pages.AdminPanelPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class OtpPage {
    private WebDriver driver;

    @FindBy(css = "input[data-testid='input']")
    private List<WebElement> otpInputs;

    @FindBy(css = "button.MuiButton-root.gradient-btn[type='button']")
    private WebElement verifyButton;

    public OtpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Existing methods
    public List<WebElement> getOtpFields() {
        return otpInputs;
    }

    public void enterOtp(String otp) {
        for (int i = 0; i < otp.length(); i++) {
            otpInputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
        }
    }

    // New minimal verification methods
    public WebElement getVerifyButton() {
        return verifyButton;
    }

    public boolean isVerifyButtonEnabled() {
        return verifyButton.isEnabled();
    }

    public void clickVerifyButton() {
        verifyButton.click();
    }
}