package com.jk.selenium.Pages.IBMBPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IBMBBeneficiaryManagementPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Beneficiary management link ka locator (aap apne according update kar sakte ho)
    private By beneficiaryLink = By.cssSelector("a[href='/beneficiary']");

    public IBMBBeneficiaryManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method to click on Manage Beneficiary link
    public void clickManageBeneficiary() {
        wait.until(ExpectedConditions.elementToBeClickable(beneficiaryLink));
        driver.findElement(beneficiaryLink).click();
    }

    // Agar aur koi Beneficiary page ke elements hain to yahan add kar sakte ho
}
