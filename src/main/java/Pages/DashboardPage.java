package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPage {
    private WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    // Replace this locator with an actual visible element from your dashboard
    private By welcomeBanner = By.id("dashboardHome");

    public WebElement getWelcomeBanner() {
        return driver.findElement(welcomeBanner);
    }

    public boolean isDashboardLoaded() {
        return getWelcomeBanner().isDisplayed();
    }
}
