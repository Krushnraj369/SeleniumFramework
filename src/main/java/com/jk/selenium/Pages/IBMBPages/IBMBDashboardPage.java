package com.jk.selenium.Pages.IBMBPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IBMBDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public IBMBDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Example: wait for some dashboard unique element visible to confirm page loaded
    public boolean waitForDashboardToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".dashboard-unique-element") // Replace with real dashboard element selector
        ));
        return false;
    }
}
