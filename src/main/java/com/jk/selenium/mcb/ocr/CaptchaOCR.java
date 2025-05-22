package com.jk.selenium.mcb.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class CaptchaOCR {

    public static String readCaptchaText(String imagePath) {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata"); // path to tessdata folder

        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            String text = tesseract.doOCR(img);
            return text.trim().replaceAll("[^a-zA-Z0-9]", ""); // cleanup
        } catch (Exception e) {
            System.err.println("OCR failed: " + e.getMessage());
            return "";
        }
    }
}
