package com.jk.selenium.Tests.MCBTests;

import com.jk.selenium.Pages.MCBPages.BeneficiaryManagementPage;
import com.jk.selenium.Util.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeneficiaryManagementTest {

    private static final Logger logger = LogManager.getLogger(BeneficiaryManagementTest.class);
    private WebDriver driver;
    private BeneficiaryManagementPage beneficiaryPage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", ConfigReader.getProperty("chromedriver.path"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        beneficiaryPage = new BeneficiaryManagementPage(driver);
        logger.info("Browser initialized for Beneficiary Management tests.");

        // 🛑 Add login steps here if required
        // Example:
        // driver.get(ConfigReader.getProperty("mcb.url"));
        // loginPage.login(...);
    }

    @Test
    public void testAddOtherBankBeneficiary() {
        logger.info("🚀 Starting test to add Other Bank Beneficiary");

        beneficiaryPage.openBeneficiaryMenu();
        beneficiaryPage.clickOtherBank();

        String accountName = ConfigReader.getProperty("beneficiary.accountName");
        String accountNumber = ConfigReader.getProperty("beneficiary.accountNumber");
        String mobileNumber = ConfigReader.getProperty("beneficiary.mobileNumber");
        String bankName = ConfigReader.getProperty("beneficiary.bankName");

        beneficiaryPage.enterAccountName(accountName);
        beneficiaryPage.enterAccountNumber(accountNumber);
        beneficiaryPage.enterMobileNumber(mobileNumber);
        beneficiaryPage.selectBank(bankName);

        logger.info("✅ Beneficiary details filled successfully with config values.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("🧹 Browser closed after Beneficiary Management tests.");
        }
    }
}