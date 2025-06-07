package com.jk.selenium.Tests.MCBTests;

import com.jk.selenium.Pages.AdminPanelPages.LoginPage;
import com.jk.selenium.Pages.MCBPages.MCBProfilePage;
import com.jk.selenium.Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MCBProfileTest extends BaseTest {

    @Test
    public void testProfileClickAndVerify() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginToMCB();

        MCBProfilePage profilePage = new MCBProfilePage(driver);
        profilePage.clickProfileDropdown();

        String name = profilePage.getProfileName();
        System.out.println("Profile name: " + name);
        Assert.assertTrue(name.contains("OHNMAR PHYO"), "Profile name verification failed!");

        Assert.assertTrue(profilePage.isLogoutButtonVisible(), "Logout button not visible!");
    }
}