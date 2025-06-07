package com.jk.selenium.Tests.MCBTests;

import com.jk.selenium.Tests.BaseTest;
import com.jk.selenium.Pages.MCBPages.MCBProfilePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MCBDashboardTest extends BaseTest {

    @Test
    @DisplayName("Verify Profile Dropdown Click")
    public void testProfileDropdown() throws InterruptedException {
        MCBProfilePage MCBprofilePage = new MCBProfilePage(driver);
        MCBprofilePage.clickProfileIcon();
        Thread.sleep(2000); // Add better wait like WebDriverWait later
        MCBprofilePage.clickProfileDropdown();
        Thread.sleep(2000);
    }
}