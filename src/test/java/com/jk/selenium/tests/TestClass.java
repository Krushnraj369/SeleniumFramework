package com.jk.selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestClass {
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Automatically downloads & sets path
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        driver.quit(); // Important to close the browser
    }

    public static void main(String[] args) {
        new TestClass().setUp(); // Run setup
    }
}
