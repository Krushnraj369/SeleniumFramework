package com.jk.selenium.mcb.ocr;

import com.jk.selenium.Util.ConfigReader;
import java.io.File;

public class OCRTest {
    public static void main(String[] args) {
        String imagePath = ConfigReader.getProperty("ocr.image.path");
        if (imagePath == null || imagePath.isEmpty()) {
            System.err.println("❌ Image path config me nahi mila!");
            return;
        }

        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.err.println("Image file not found at: " + imageFile.getAbsolutePath());
            return;
        }

        String extractedText = OCRUtil.getCaptchaText(imageFile);
        System.out.println("Extracted Text: " + extractedText);
    }
}
