package com.jk.selenium.Tests.IBMBTests;

import com.jk.selenium.Pages.IBMBPages.IBMBBeneficiaryManagementPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class IBMBBeneficiaryManagementTest {

    WebDriver driver;
    IBMBBeneficiaryManagementPage beneficiaryPage;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe"); // Correct path lagao yahan
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://yourdashboardurl.com");  // Aapka dashboard URL yahan dalein

        // Agar login karna hai to yahan login ka code ya login page class call karo
        // Example:
        // LoginPage loginPage = new LoginPage(driver);
        // loginPage.login("username", "password");

        beneficiaryPage = new IBMBBeneficiaryManagementPage(driver);
    }

    @Test
    public void testClickManageBeneficiary() {
        beneficiaryPage.clickManageBeneficiary();

        // Add assertions here to verify you are on the beneficiary page
        // For example:
        // String expectedUrl = "https://yourdashboardurl.com/beneficiary";
        // assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
