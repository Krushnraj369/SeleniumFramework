package com.jk.selenium.Tests.IBMBTests;

import com.jk.selenium.Pages.IBMBPages.IBMBDashboardPage;
import com.jk.selenium.Pages.IBMBPages.IBMBLoginPage;
import com.jk.selenium.Tests.BaseTest;
import com.jk.selenium.Util.ConfigReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class IBMBLoginTest extends BaseTest {
    private IBMBLoginPage loginPage;
    private IBMBDashboardPage dashboardPage;

    private final int MAX_CAPTCHA_RETRIES = 3;

    @BeforeMethod
    public void navigateToLogin() {
        driver.get(ConfigReader.getProperty("ibmb.url"));
        loginPage = new IBMBLoginPage(driver);

        logger.info("Navigated to IBMB login page successfully");
    }

    @Test
    public void testLoginToIBMBPortal() {
        logger.info("🚀 Starting IBMB Login Test");

        boolean loggedInSuccessfully = false;
        int attempt = 0;

        while (attempt < MAX_CAPTCHA_RETRIES && !loggedInSuccessfully) {
            attempt++;
            logger.info("Login attempt #" + attempt);

            loginPage.enterLoginId(ConfigReader.getProperty("ibmb.loginId"));
            loginPage.enterPassword(ConfigReader.getProperty("ibmb.password"));

            String captchaText = loginPage.readCaptchaTextUsingOCR();
            logger.info("Extracted CAPTCHA via OCR: " + captchaText);

            if (captchaText.isEmpty()) {
                logger.warn("Captcha extraction failed on attempt " + attempt);
                if (attempt == MAX_CAPTCHA_RETRIES) {
                    Assert.fail("Captcha extraction failed after max retries.");
                }
                continue;
            }

            loginPage.enterCaptcha(captchaText);
            loginPage.clickLogin();

            try {
                // Wait for dashboard or some post-login element visible after login success
                dashboardPage = new IBMBDashboardPage(driver);
                boolean dashboardLoaded = dashboardPage.waitForDashboardToLoad();

                if (dashboardLoaded) {
                    logger.info("Dashboard loaded successfully after login");
                    loggedInSuccessfully = true;

                    // Click beneficiary link after successful login
                    loginPage.clickBeneficiaryLink();

                    // Verify beneficiary page loaded by checking URL or some unique element
                    String currentUrl = driver.getCurrentUrl();
                    logger.info("After clicking Beneficiary Link, URL: " + currentUrl);
                    Assert.assertTrue(currentUrl.contains("/beneficiary"), "Beneficiary page should be loaded");
                } else {
                    logger.warn("Dashboard did not load, login might have failed");
                    if (attempt == MAX_CAPTCHA_RETRIES) {
                        Assert.fail("Login failed after max captcha retries.");
                    } else {
                        driver.navigate().refresh();

                    }
                }

            } catch (TimeoutException e) {
                logger.warn("Dashboard not found, login probably failed due to wrong captcha. Retrying...");
                if (attempt == MAX_CAPTCHA_RETRIES) {
                    Assert.fail("Login failed after max captcha retries.");
                } else {
                    driver.navigate().refresh();
                  
                }
            }
        }

        Assert.assertTrue(loggedInSuccessfully, "Login unsuccessful after retries.");
        logger.info("🎉 IBMB Login successful and dashboard loaded.");
    }

    private String takeScreenshot(String name) {
        try {
            File screenshotDir = new File(System.getProperty("user.dir") + "/screenshots/");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = screenshotDir + "/" + name + "_" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(source, new File(dest));
            return dest;
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
            return "";
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = takeScreenshot(result.getMethod().getMethodName() + "_failure");
            if (!screenshotPath.isEmpty()) {
                logger.error("Test failed. Screenshot saved at: {}", screenshotPath);
            }
        }
    }
}
