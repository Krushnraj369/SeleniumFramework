package com.jk.selenium.Pages.AdminPanelPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators
    private final By rootDiv = By.id("root");
    private final By searchBar = By.cssSelector("input[placeholder^='Search in']");
    private final By searchResultsContainer = By.id("search-results-container");

    // Wait for dashboard root div visibility to confirm dashboard loaded
    public boolean waitForDashboardToLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(rootDiv));
            return true;
        } catch (TimeoutException e) {
            System.err.println("Dashboard root element not visible after waiting.");
            return false;
        }
    }

    // Wait for search bar to be clickable and return the WebElement
    public WebElement waitForSearchBarToBeClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(searchBar));
        } catch (TimeoutException e) {
            System.err.println("Search bar not clickable after waiting.");
            throw e;
        }
    }

    // Click on search bar safely
    public void clickSearchBar() {
        try {
            waitForSearchBarToBeClickable().click();
        } catch (Exception e) {
            System.err.println("Failed to click on search bar: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Enters text into the search bar, triggers the search, and waits for results.
     * Uses scrollIntoView and JavaScript fallback for reliability.
     *
     * @param text text to enter into the search bar
     */
    public void enterSearchText(String text) {
        try {
            WebElement searchField = waitForSearchBarToBeClickable();

            // Scroll into view to avoid ElementNotInteractableException
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchField);

            // Clear existing text
            wait.until(ExpectedConditions.visibilityOf(searchField));
            searchField.clear();

            // Try normal sendKeys
            try {
                searchField.sendKeys(text);
            } catch (Exception e) {
                System.err.println("sendKeys failed, trying JavaScript fallback: " + e.getMessage());
                // JavaScript fallback for setting value
                ((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];", searchField, text);
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", searchField);
            }

            // Press Enter to trigger search (if required)
            searchField.sendKeys(Keys.ENTER);

            // Wait for search results to appear
            waitForSearchResults();

        } catch (TimeoutException e) {
            System.err.println("Timeout while entering text or waiting for results: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error while entering search text: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Waits for the search results container to become visible with retry logic.
     */
    private void waitForSearchResults() {
        int retries = 3;
        for (int i = 0; i < retries; i++) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(searchResultsContainer));
                return;
            } catch (TimeoutException e) {
                System.err.println("Attempt " + (i + 1) + ": search results container not visible yet.");
                if (i == retries - 1) throw e;

                try {
                    Thread.sleep(200); // brief pause before retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during wait retry", ie);
                }
            }
        }
    }
}