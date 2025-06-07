package com.jk.selenium.Pages.MCBPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MCBProfilePage {
    private WebDriver driver;

    private By profileDropdown = By.cssSelector("div.relative a.flex.items-center.gap-2");
    private By profileName = By.cssSelector("span.text-xs.text-primary");
    private By logoutButton = By.xpath("//span[contains(text(),'Log Out')]");

    public MCBProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickProfileDropdown() {
        WebElement profile = driver.findElement(profileDropdown);
        new Actions(driver).moveToElement(profile).click().perform();
    }

    public String getProfileName() {
        return driver.findElement(profileName).getText();
    }

    public boolean isLogoutButtonVisible() {
        return driver.findElement(logoutButton).isDisplayed();
    }

    public void clickLogout() {
        driver.findElement(logoutButton).click();
    }

    public void clickProfileIcon() {
    }
}