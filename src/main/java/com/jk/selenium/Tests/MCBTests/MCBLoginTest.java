package com.jk.selenium.Tests.MCBTests;

import com.jk.selenium.Pages.MCBPages.MCBLoginPage;
import com.jk.selenium.Pages.MCBPages.MCBOtpPage;
import com.jk.selenium.Pages.MCBPages.MCBDashboardPage;
import com.jk.selenium.Tests.BaseTest;
import com.jk.selenium.Util.ConfigReader;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;

public class MCBLoginTest extends BaseTest {
    private MCBLoginPage loginPage;
    private MCBOtpPage otpPage;
    private MCBDashboardPage dashboardPage;

    private final int MAX_CAPTCHA_RETRIES = 3;

    @BeforeMethod
    public void navigateToLogin() {
        driver.get(ConfigReader.getProperty("mcb.url"));
        loginPage = new MCBLoginPage(driver);
        wait.until(ExpectedConditions.visibilityOf(loginPage.getLoginForm()));
        logger.info("Navigated to login page successfully");
    }

    @Test
    public void testLoginToMCBPortal() {
        logger.info("🚀 Starting MCB Login Test");

        boolean loggedInSuccessfully = false;
        int attempt = 0;

        while (attempt < MAX_CAPTCHA_RETRIES && !loggedInSuccessfully) {
            attempt++;
            logger.info("Login attempt #" + attempt);

            loginPage.enterCorporateId(ConfigReader.getProperty("mcb.corporateId"));
            loginPage.enterLoginId(ConfigReader.getProperty("mcb.loginId"));
            loginPage.enterPassword(ConfigReader.getProperty("mcb.password"));

            String captchaText = loginPage.readCaptchaTextUsingOCR();
            logger.info("Captcha extracted: " + captchaText);

            if (captchaText.isEmpty()) {
                logger.warn("Captcha extraction failed on attempt " + attempt);
                if (attempt == MAX_CAPTCHA_RETRIES) {
                    Assert.fail("Captcha extraction failed after max retries.");
                }
                continue;
            }

            loginPage.enterCaptcha(captchaText);
            loginPage.clickLogin();

            otpPage = new MCBOtpPage(driver);
            try {
                wait.until(ExpectedConditions.visibilityOfAllElements(otpPage.getOtpFields()));
                logger.info("OTP input fields visible, login success confirmed.");
                loggedInSuccessfully = true;
            } catch (TimeoutException e) {
                logger.warn("OTP fields not found, login probably failed due to wrong captcha. Retrying...");
                if (attempt == MAX_CAPTCHA_RETRIES) {
                    Assert.fail("Login failed after max captcha retries.");
                } else {
                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.visibilityOf(loginPage.getLoginForm()));
                }
            }
        }

        Assert.assertTrue(loggedInSuccessfully, "Login unsuccessful after retries.");

        String otp = ConfigReader.getProperty("mcb.otp");
        Assert.assertNotNull(otp, "OTP value is missing in config");
        Assert.assertFalse(otp.trim().isEmpty(), "OTP value in config is empty");

        otpPage.enterOtpDigits(otp);
        otpPage.clickLoginButton();

        dashboardPage = new MCBDashboardPage(driver);
        boolean isDashboardLoaded = dashboardPage.waitForDashboardToLoad();

        if (!isDashboardLoaded) {
            String screenshotPath = takeScreenshot("dashboard_load_failure");
            logger.error("Dashboard did not load after OTP submission. Screenshot saved at: " + screenshotPath);
        }
        Assert.assertTrue(isDashboardLoaded, "Dashboard did not load after login and OTP submission.");
        logger.info("🎉 Login successful and dashboard loaded.");
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