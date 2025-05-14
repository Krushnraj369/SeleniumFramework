package com.jk.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;

public class LoginTest {

    @Test
    public void loginTest() {
        WebDriverManager.chromedriver().setup(); // Automatically downloads and sets driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("https://practicetestautomation.com/practice-test-login/");

            WebElement usernameField = driver.findElement(By.id("username"));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("submit"));

            usernameField.sendKeys("student");
            passwordField.sendKeys("Password123");
            loginButton.click();

            WebElement logoutButton = driver.findElement(By.xpath("//a[text()='Log out']"));
            if (logoutButton.isDisplayed()) {
                System.out.println("✅ Login Test Passed.");
            }
        } catch (Exception e) {
            System.out.println("❌ Login Test Failed. Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
