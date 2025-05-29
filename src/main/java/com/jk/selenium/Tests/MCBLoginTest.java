package com.jk.selenium.Tests;

import com.jk.selenium.Pages.MCBLoginPage;
import com.jk.selenium.Util.ConfigReader;
import com.jk.selenium.mcb.ocr.OCRUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class MCBLoginTest {
    private static final Logger logger = LogManager.getLogger(MCBLoginTest.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private MCBLoginPage loginPage;

    private final By postLoginElement = By.xpath("//span[contains(text(), 'Dashboard')]");

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", ConfigReader.getProperty("chromedriver.path"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        logger.info("Browser initialized successfully");
    }

    @BeforeMethod
    public void navigateToLogin() {
        driver.get(ConfigReader.getProperty("mcb.url"));
        loginPage = new MCBLoginPage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getLoginFormLocator()));
        logger.info("Navigated to login page successfully");
    }

    @Test
    public void testLoginToMCBPortal() {
        logger.info("🚀 Starting MCB Login Test");

        // Enter Credentials
        loginPage.enterCorporateId(ConfigReader.getProperty("mcb.corporateId"));
        loginPage.enterLoginId(ConfigReader.getProperty("mcb.loginId"));
        loginPage.enterPassword(ConfigReader.getProperty("mcb.password"));
        logger.info("Credentials entered successfully");

        // Extract Captcha and Enter
        String captchaText = loginPage.readCaptchaTextUsingOCR();
        Assert.assertFalse(captchaText.isEmpty(), "Captcha extraction failed - No captcha found");

        loginPage.enterCaptcha(captchaText);
        logger.info("Captcha entered successfully");

        // Click Login and verify
        loginPage.clickLogin();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(postLoginElement));
            logger.info("✅ Login successful. Dashboard element found.");
        } catch (TimeoutException e) {
            logger.error("❌ Login failed or dashboard not loaded", e);
            String screenshotPath = takeScreenshot("login_failure");
            if (!screenshotPath.isEmpty()) {
                logger.info("Screenshot saved at: {}", screenshotPath);
            }
            Assert.fail("Login did not succeed or Dashboard not found.");
        }
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

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("🧹 Browser closed after test.");
        }
    }
}
