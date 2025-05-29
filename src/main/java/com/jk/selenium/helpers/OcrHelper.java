package com.jk.selenium.helpers;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class OcrHelper {

    // Tesseract डेटा पथ (एक बार सेट करें, बार-बार नहीं)
    private static final String TESSDATA_PATH = Paths.get(System.getProperty("user.dir"), "tessdata").toString();

    public static String readCaptchaFromFile(File imageFile) {
        if (imageFile == null || !imageFile.exists()) {
            System.err.println("Image file does not exist: " + (imageFile != null ? imageFile.getPath() : "null"));
            return "";
        }

        try {
            // 1. इमेज लोड करें
            BufferedImage img = ImageIO.read(imageFile);
            if (img == null) {
                System.err.println("Unsupported image format for file: " + imageFile.getPath());
                return "";
            }

            // 2. Tesseract इंस्टेंस कॉन्फिगर करें
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(TESSDATA_PATH);

            // 3. CAPTCHA के लिए ऑप्टिमाइज्ड सेटिंग्स
            tesseract.setLanguage("eng");
            tesseract.setPageSegMode(7);  // सिंगल लाइन मोड (CAPTCHA के लिए बेस्ट)
            tesseract.setOcrEngineMode(3); // डिफॉल्ट OCR इंजन
            tesseract.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
            tesseract.setTessVariable("user_defined_dpi", "300");

            // 4. OCR परफॉर्म करें और रिजल्ट क्लीन करें
            String result = tesseract.doOCR(img);
            return result.replaceAll("[^a-zA-Z0-9]", "").trim();

        } catch (TesseractException e) {
            System.err.println("Tesseract OCR exception for file: " + imageFile.getPath());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General exception during OCR processing for file: " + imageFile.getPath());
            e.printStackTrace();
        }
        return "";
    }
}
