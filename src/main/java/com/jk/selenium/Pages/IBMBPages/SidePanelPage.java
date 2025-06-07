package com.jk.selenium.Pages.IBMBPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SidePanelPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Side Panel Toggle Button
    @FindBy(css = ".side-panel-toggle")
    private WebElement sidePanelToggle;

    public SidePanelPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Open/Close Panel
    public void openSidePanel() {
        try {
            if (!isSidePanelOpen()) {
                wait.until(ExpectedConditions.elementToBeClickable(sidePanelToggle)).click();
                wait.until(ExpectedConditions.attributeContains(sidePanelToggle, "aria-expanded", "true"));
            }
        } catch (Exception e) {
            System.out.println("Error while opening side panel: " + e.getMessage());
            throw e;
        }
    }

    public void closeSidePanel() {
        try {
            if (isSidePanelOpen()) {
                wait.until(ExpectedConditions.elementToBeClickable(sidePanelToggle)).click();
                wait.until(ExpectedConditions.attributeContains(sidePanelToggle, "aria-expanded", "false"));
            }
        } catch (Exception e) {
            System.out.println("Error while closing side panel: " + e.getMessage());
            throw e;
        }
    }

    public boolean isSidePanelOpen() {
        try {
            return sidePanelToggle.getAttribute("aria-expanded").equals("true");
        } catch (Exception e) {
            return false;
        }
    }

    // Dynamic Menu Navigation
    public void navigateToSubMenu(String mainMenu, String subMenu) {
        openSidePanel();
        try {
            WebElement mainMenuElement = driver.findElement(By.xpath("//span[text()='" + mainMenu + "']/ancestor::a"));
            wait.until(ExpectedConditions.elementToBeClickable(mainMenuElement)).click();
        } catch (Exception e) {
            System.out.println("Main menu not found: " + mainMenu);
            throw e;
        }

        try {
            WebElement subMenuElement = driver.findElement(By.xpath("//span[text()='" + subMenu + "']/ancestor::a"));
            wait.until(ExpectedConditions.elementToBeClickable(subMenuElement)).click();
        } catch (Exception e) {
            System.out.println("Sub-menu not found: " + subMenu);
            throw e;
        }
    }

    // Utility
    public boolean isMenuVisible(String menuName) {
        openSidePanel();
        try {
            WebElement menu = driver.findElement(By.xpath("//span[text()='" + menuName + "']"));
            return menu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
