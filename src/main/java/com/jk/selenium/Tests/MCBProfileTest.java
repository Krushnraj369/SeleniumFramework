package com.jk.selenium.Tests;

import com.jk.selenium.Pages.LoginPage;
import com.jk.selenium.Pages.MCBProfilePage;
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
