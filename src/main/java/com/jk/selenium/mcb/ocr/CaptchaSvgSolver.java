package com.jk.selenium.mcb.ocr;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CaptchaSvgSolver {

    private final WebDriver driver;

    public CaptchaSvgSolver(WebDriver driver) {
        this.driver = driver;
    }

    public String readCaptchaFromSvg() {
        // SVG के अंदर के सभी <text> एलिमेंट्स को खोजो
        List<WebElement> textElements = driver.findElements(By.xpath("//svg//*[local-name()='text']"));

        StringBuilder captchaText = new StringBuilder();

        for (WebElement el : textElements) {
            captchaText.append(el.getText());
        }

        return captchaText.toString().trim();
    }
}
