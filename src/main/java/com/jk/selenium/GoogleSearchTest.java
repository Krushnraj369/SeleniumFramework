package com.jk.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleSearchTest {

    @Test
    public void testGoogleSearch() {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        // Open Google
        driver.get("https://www.google.com");

        // Verify the title contains "Google"
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Google"));

        // Close the browser
        driver.quit();
    }
}
