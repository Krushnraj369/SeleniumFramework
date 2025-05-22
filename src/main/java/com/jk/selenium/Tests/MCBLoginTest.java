package com.jk.selenium.Tests;

import com.jk.selenium.Pages.MCBLoginPage;
import com.jk.selenium.Util.ConfigReader;
import com.mcb.ocr.TesseractUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;

public class MCBLoginTest {
    private WebDriver driver;
    private MCBLoginPage loginPage;
    private WebDriverWait wait;

    // Post-login element id or locator - configure according to your app
    private final By postLoginElement = By.id("somePostLoginElement");

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void navigateToLogin() {
        driver.get(ConfigReader.getProperty("mcb.url"));
        loginPage = new MCBLoginPage(driver);
    }

    @Test
    public void testLoginToMCBPortal() {
        System.out.println("🚀 Starting login test...");

        // Enter login details
        loginPage.enterCorporateId(ConfigReader.getProperty("mcb.corporateId"));
        loginPage.enterLoginId(ConfigReader.getProperty("mcb.loginId"));
        loginPage.enterPassword(ConfigReader.getProperty("mcb.password"));

        // Handle captcha with OCR
        try {
            WebElement captchaImg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Captcha verification']")));

            // Screenshot of full page
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshot);

            Point point = captchaImg.getLocation();
            int width = captchaImg.getSize().getWidth();
            int height = captchaImg.getSize().getHeight();

            // Crop the captcha image
            BufferedImage captchaCropped = fullImg.getSubimage(point.getX(), point.getY(), width, height);

            File captchaFile = new File("src/test/resources/captcha.png");
            ImageIO.write(captchaCropped, "png", captchaFile);

            String tessDataPath = System.getProperty("user.dir") + "/target/tessdata";
            TesseractUtil ocr = new TesseractUtil(tessDataPath);

            String captchaText = ocr.doOCR(captchaFile).trim().replaceAll("[^a-zA-Z0-9]", "");
            System.out.println("🔍 OCR Captcha Text: " + captchaText);

            // Validate captchaText length and characters before entering
            Assert.assertFalse(captchaText.isEmpty(), "Captcha OCR text is empty!");

            loginPage.enterCaptcha(captchaText);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Failed to process captcha using OCR: " + e.getMessage());
        }

        // Submit the login form
        loginPage.clickLogin();

        // Validate successful login by checking the post-login element
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(postLoginElement));
            System.out.println("✅ Login successful, post-login element found.");
        } catch (TimeoutException e) {
            System.out.println("❌ Login may have failed, post-login element NOT found.");
            Assert.fail("Login failed or post-login page did not load correctly.");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed.");
        }
    }

    public static class AdminPanelLoginTest {
        @Test
        public void openAdminPanel() {
            System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver-win64\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Correct way to load URL from config
            String adminUrl = ConfigReader.getProperty("admin.url");
            driver.get(adminUrl);

            // TODO: Add login steps using Page Object Model

            driver.quit();
        }
    }
}
