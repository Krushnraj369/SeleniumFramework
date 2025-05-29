package com.jk.selenium.mcb.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class CaptchaOCR {

    private final ITesseract tesseract;

    // Constructor में datapath set करें ताकि flexibility मिले
    public CaptchaOCR(String tessDataPath) {
        tesseract = new Tesseract();
        tesseract.setDatapath(tessDataPath);  // tessdata folder का path
        tesseract.setLanguage("eng");
        // अगर चाहें तो whitelist सेट कर सकते हैं:
        tesseract.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
    }

    public String readCaptchaText(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            String text = tesseract.doOCR(img);
            // केवल alpha-numeric characters ही रखें, बाकी हटाएं
            return text.trim().replaceAll("[^a-zA-Z0-9]", "");
        } catch (Exception e) {
            System.err.println("OCR failed: " + e.getMessage());
            return "";
        }
    }
}
