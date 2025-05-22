package com.jk.selenium.mcb.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;

public class OCRUtil {
    public static String getCaptchaText(File imageFile) {
        Tesseract tesseract = new Tesseract();




        try {
            return tesseract.doOCR(imageFile).trim();
        } catch (TesseractException e) {
            e.printStackTrace();
            return "";
        }
    }
}
