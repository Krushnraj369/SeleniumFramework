package com.jk.selenium.Pages.MCBPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BeneficiaryManagementPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators based on your input
    private By beneficiaryMenu = By.cssSelector(".group[href='/beneficiary'] > .opacity-1");
    private By otherBankLink = By.cssSelector("[href='/beneficiary/otherBank']");
    private By beneficiarySection = By.cssSelector(".items-start");

    private By accountNameInput = By.cssSelector("[placeholder='Enter Beneficiary Account Name']");
    private By accountNumberInput = By.cssSelector("[placeholder='Enter Beneficiary Account Number']");
    private By mobileNumberInput = By.cssSelector("[placeholder='Enter Beneficiary Mobile Number']");

    private By bankDropdown = By.cssSelector(".css-3513rj-control .css-19bb58m");
    private By selectedBankName = By.cssSelector("div.css-1dimb5e-singleValue");

    public BeneficiaryManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Click Beneficiary Menu to open beneficiary section
    public void openBeneficiaryMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(beneficiaryMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(beneficiarySection));
    }

    // Click "Other Bank" link under beneficiary section
    public void clickOtherBank() {
        wait.until(ExpectedConditions.elementToBeClickable(otherBankLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountNameInput));
    }

    // Enter Beneficiary Account Name
    public void enterAccountName(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(accountNameInput));
        input.clear();
        input.sendKeys(name);
    }

    // Enter Beneficiary Account Number
    public void enterAccountNumber(String accountNumber) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(accountNumberInput));
        input.clear();
        input.sendKeys(accountNumber);
    }

    // Enter Beneficiary Mobile Number
    public void enterMobileNumber(String mobileNumber) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNumberInput));
        input.clear();
        input.sendKeys(mobileNumber);
    }

    // Select bank from dropdown (click dropdown and then select the value)
    public void selectBank(String bankName) {
        wait.until(ExpectedConditions.elementToBeClickable(bankDropdown)).click();
        // Wait for dropdown options to load - here we assume options appear as div with bank name
        By bankOption = By.xpath("//div[text()='" + bankName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(bankOption)).click();

        // Verify selected bank
        String selected = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedBankName)).getText();
        if (!selected.equals(bankName)) {
            throw new NoSuchElementException("Bank '" + bankName + "' not selected correctly. Found: " + selected);
        }
    }

    // You can add methods like click Save/Add beneficiary button here, if needed.

}