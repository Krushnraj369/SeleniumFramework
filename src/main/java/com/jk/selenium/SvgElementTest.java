package com.jk.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class SvgElementTest {
    public static void main(String[] args) {
        // Step 1: Set system property if required (Optional with Selenium 4+ and ChromeDriverManager)
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // Step 2: Launch Chrome browser
        WebDriver driver = new ChromeDriver();

        try {
            // Step 3: Load the test-report.html (Change path according to your file location)
            String filePath = "file:///C:/Users/YourName/Path/to/test-report.html";
            driver.get(filePath);

            // Step 4: Maximize browser & set implicit wait
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 5: Locate SVG Circle element using XPath
            WebElement svgCircle = driver.findElement(By.xpath("//*[local-name()='svg']/*[local-name()='circle']"));

            // Step 6: Print SVG attribute values
            System.out.println("CX: " + svgCircle.getAttribute("cx"));
            System.out.println("CY: " + svgCircle.getAttribute("cy"));
            System.out.println("Radius: " + svgCircle.getAttribute("r"));
            System.out.println("Stroke: " + svgCircle.getAttribute("stroke"));
            System.out.println("Fill: " + svgCircle.getAttribute("fill"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 7: Close browser
            driver.quit();
        }
    }
}
