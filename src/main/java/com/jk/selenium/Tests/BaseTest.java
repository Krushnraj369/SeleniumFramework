package com.jk.selenium.Tests;

import com.jk.selenium.Util.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUpSuite() {
        System.setProperty("webdriver.chrome.driver", ConfigReader.getProperty("chromedriver.path"));
    }

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        logger.info("Browser initialized successfully");
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        logger.info("Waiting 10 seconds before closing browser for inspection...");
        Thread.sleep(10000);

        if (driver != null) {
            driver.quit();
            logger.info("🧹 Browser closed after test.");
        }
    }
}
