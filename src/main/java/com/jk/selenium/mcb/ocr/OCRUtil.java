package com.jk.selenium.mcb.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;

public class OCRUtil {
    private static Tesseract tesseractInstance;

    static {
        tesseractInstance = new Tesseract();
        // Aapka correct tessdata path jo aapne diya hai:
        tesseractInstance.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        tesseractInstance.setLanguage("eng");
        // Whitelist: Only alphanumeric chars, better accuracy for captcha
        tesseractInstance.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        // Page segmentation mode 7: Treat image as a single text line (captcha mostly single line)
        tesseractInstance.setPageSegMode(7);
    }

    // OCR from image file
    public static String getCaptchaText(java.io.File imageFile) {
        try {
            String result = tesseractInstance.doOCR(imageFile);
            if (result != null) {
                return result.replaceAll("[^A-Za-z0-9]", "").trim();
            } else {
                return "";
            }
        } catch (TesseractException e) {
            System.err.println("Error during OCR processing: " + e.getMessage());
            return "";
        }
    }

    // OCR from BufferedImage (preferred for captcha from Selenium screenshot)
    public static String getCaptchaTextFromBufferedImage(BufferedImage image) {
        try {
            String result = tesseractInstance.doOCR(image);
            if (result != null) {
                return result.replaceAll("[^A-Za-z0-9]", "").trim();
            } else {
                return "";
            }
        } catch (TesseractException e) {
            System.err.println("Error during OCR processing: " + e.getMessage());
            return "";
        }
    }
}