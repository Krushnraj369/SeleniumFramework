package com.jk.selenium.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MCBDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator for profile dropdown button (to open profile menu)
    private By profileDropdownButton = By.cssSelector(".m-0.size-4.text-gray-700");

    public MCBDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /**
     * Wait until dashboard is loaded by checking profile dropdown button visible
     */
    public boolean waitForDashboardToLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(profileDropdownButton));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Click the profile dropdown button to open profile menu
     */
    public void clickProfileDropdown() {
        WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(profileDropdownButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
    }
}
