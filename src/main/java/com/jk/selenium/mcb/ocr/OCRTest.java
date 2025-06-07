package com.jk.selenium.mcb.ocr;

import com.jk.selenium.Util.ConfigReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class OCRTest {

    public static void main(String[] args) {
        // Get the image path from config.properties
        String imagePath = ConfigReader.getProperty("ocr.image.path");

        if (imagePath == null || imagePath.isEmpty()) {
            System.err.println("❌ ocr.image.path is missing in config.properties!");
            return;
        }

        try {
            File imgFile = new File(imagePath);
            if (!imgFile.exists()) {
                System.err.println("❌ Image file not found: " + imagePath);
                return;
            }

            // Read the image
            BufferedImage image = ImageIO.read(imgFile);

            if (image == null) {
                System.err.println("❌ Failed to read image. Please check the file format.");
                return;
            }

            // Perform OCR using OCRUtil
            String extractedText = OCRUtil.getCaptchaTextFromBufferedImage(image);

            System.out.println("✅ Extracted Text from Image: " + extractedText);

        } catch (Exception e) {
            System.err.println("❌ Exception occurred while processing the image:");
            e.printStackTrace();
        }
    }
}