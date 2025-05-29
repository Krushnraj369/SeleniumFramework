package com.jk.selenium.mcb.ocr;

import com.jk.selenium.Util.ConfigReader;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

public class OCRUtil {

    public static String getCaptchaText(File imageFile) {
        String tessDataPath = ConfigReader.getProperty("tesseract.datapath");
        if (tessDataPath == null || tessDataPath.isEmpty()) {
            System.err.println("❌ Tessdata path config me nahi mila!");
            return "";
        }

        try {
            BufferedImage originalImage = ImageIO.read(imageFile);

            // Preprocessing steps
            BufferedImage gray = toGrayscale(originalImage);
            BufferedImage contrastImg = increaseContrast(gray);
            BufferedImage binary = toBinary(contrastImg);

            // Debug: Save preprocessed image
            String debugPath = System.getProperty("user.dir") + "/debug_captcha_preprocessed.png";
            ImageIO.write(binary, "png", new File(debugPath));
            System.out.println("Preprocessed captcha image saved to: " + debugPath);

            // Setup Tesseract
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tessDataPath);
            tesseract.setLanguage("eng");
            tesseract.setPageSegMode(8);  // single word mode (try 6 or 7 if needed)
            tesseract.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");

            // OCR
            String result = tesseract.doOCR(binary);
            result = result.replaceAll("[^A-Za-z0-9]", "").trim();
            System.out.println("OCR Result: " + result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Convert to grayscale
    private static BufferedImage toGrayscale(BufferedImage input) {
        BufferedImage gray = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = gray.createGraphics();
        g.drawImage(input, 0, 0, null);
        g.dispose();
        return gray;
    }

    // Increase contrast
    private static BufferedImage increaseContrast(BufferedImage img) {
        RescaleOp rescale = new RescaleOp(1.5f, 0, null);  // 1.5 = contrast factor
        BufferedImage contrasted = rescale.filter(img, null);
        return contrasted;
    }

    // Convert to binary using simple thresholding
    private static BufferedImage toBinary(BufferedImage img) {
        BufferedImage binary = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = binary.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return binary;
    }
}
