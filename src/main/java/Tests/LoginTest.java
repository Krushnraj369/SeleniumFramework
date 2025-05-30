package Tests;

import Pages.LoginPage;
import Pages.OtpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private OtpPage otpPage;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://netbankinguat.aiplservices.com:31001/netbanking/login");

        loginPage = new LoginPage(driver);
        otpPage = new OtpPage(driver);
    }

    @Test
    public void testLoginWithOtp() {
        // Step 1: Enter Login Credentials
        wait.until(ExpectedConditions.visibilityOf(loginPage.getUsernameField()));
        loginPage.enterUsername("1");

        wait.until(ExpectedConditions.visibilityOf(loginPage.getPasswordField()));
        loginPage.enterPassword("Qaws@1212");

        wait.until(ExpectedConditions.elementToBeClickable(loginPage.getLoginButton()));
        loginPage.clickLoginButton();

        // Step 2: Enter OTP
        wait.until(ExpectedConditions.visibilityOfAllElements(otpPage.getOtpFields()));
        otpPage.enterOtp("123456"); // Replace with actual OTP

        wait.until(ExpectedConditions.elementToBeClickable(otpPage.getVerifyButton()));
        otpPage.clickVerifyButton();

        // Step 3: Wait for Dashboard to Load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='IBMB']")));
        System.out.println("✅ Login Successful. Dashboard loaded.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
