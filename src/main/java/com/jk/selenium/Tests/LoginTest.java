package com.jk.selenium.Tests;

import com.jk.selenium.Pages.DashboardPage;
import com.jk.selenium.Pages.LoginPage;
import com.jk.selenium.Pages.OtpPage;
import com.jk.selenium.Util.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private OtpPage otpPage;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", ConfigReader.getProperty("chromedriver.path"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        String adminUrl = ConfigReader.getProperty("admin.url");
        driver.get(adminUrl);

        loginPage = new LoginPage(driver);
        otpPage = new OtpPage(driver);
    }

    @Test
    public void testLoginWithOtpAndDashboardLoad() throws InterruptedException {
        // Step 1: Enter username
        wait.until(ExpectedConditions.visibilityOf(loginPage.getUsernameField()));
        loginPage.enterUsername(ConfigReader.getProperty("admin.username"));
        System.out.println("✅ Entered username.");

        // Step 2: Enter password
        wait.until(ExpectedConditions.visibilityOf(loginPage.getPasswordField()));
        loginPage.enterPassword(ConfigReader.getProperty("admin.password"));
        System.out.println("✅ Entered password.");

        // Step 3: Read Captcha using OCR and enter it
        wait.until(ExpectedConditions.visibilityOf(loginPage.getCaptchaImage()));
        String captchaText = loginPage.readCaptchaUsingOCR();
        if (captchaText.isEmpty()) {
            throw new RuntimeException("❌ Captcha text extraction failed!");
        }
        loginPage.enterCaptcha(captchaText);  // Captcha enter method added in LoginPage
        System.out.println("✅ Entered captcha: " + captchaText);

        // Step 4: Click Login button
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.getLoginButton()));
        loginPage.clickLoginButton();
        System.out.println("✅ Clicked login button.");

        // Step 5: Wait for OTP fields
        wait.until(ExpectedConditions.visibilityOfAllElements(otpPage.getOtpFields()));
        System.out.println("✅ OTP fields are visible.");

        // Step 6: Enter OTP digits (from config)
        enterOtpDigits(ConfigReader.getProperty("admin.otp"));
        System.out.println("✅ OTP entered.");

        // Step 7: Click Verify button
        By verifyBtn = By.xpath("//button[contains(@class, 'gradient-btn') and contains(normalize-space(.), 'Verify')]");
        wait.until(ExpectedConditions.elementToBeClickable(verifyBtn)).click();
        System.out.println("✅ Clicked Verify button.");

        // Step 8: Wait and verify dashboard load
        DashboardPage dashboardPage = new DashboardPage(driver);
        if (dashboardPage.waitForDashboardToLoad()) {
            System.out.println("✅ Dashboard loaded successfully.");

            // Step 9: Interact with dashboard search
            try {
                dashboardPage.enterSearchText("parameter Confirmation");
                System.out.println("✅ Search text entered successfully in dashboard.");
                Thread.sleep(5000); // Optional pause
            } catch (Exception e) {
                System.err.println("❌ Failed to enter search text: " + e.getMessage());
                throw e;
            }
        } else {
            System.err.println("❌ Dashboard failed to load.");
            System.err.println("Current URL: " + driver.getCurrentUrl());
            String pageSourceSnippet = Objects.requireNonNull(driver.getPageSource()).length() > 500
                    ? driver.getPageSource().substring(0, 500)
                    : driver.getPageSource();
            System.err.println("Page source snippet: " + pageSourceSnippet);
            throw new RuntimeException("Dashboard verification failed.");
        }
    }

    private void enterOtpDigits(String otpCode) throws InterruptedException {
        List<WebElement> otpFields = otpPage.getOtpFields();

        if (otpFields.size() == otpCode.length()) {
            for (int i = 0; i < otpCode.length(); i++) {
                WebElement field = otpFields.get(i);
                field.clear();
                field.sendKeys(String.valueOf(otpCode.charAt(i)));
                Thread.sleep(100);
            }
        } else if (otpFields.size() == 1) {
            WebElement otpField = otpFields.get(0);
            otpField.clear();
            otpField.sendKeys(otpCode);
        } else {
            throw new RuntimeException("Unexpected number of OTP input fields: " + otpFields.size());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }
}
