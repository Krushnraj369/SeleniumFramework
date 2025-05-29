package com.jk.selenium.mcb.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OcrHelper {
    private final ITesseract tesseract;

    public OcrHelper(String tessDataPath) {
        tesseract = new Tesseract();
        tesseract.setDatapath(tessDataPath); // जैसे: C:/Program Files/Tesseract-OCR/tessdata
        tesseract.setLanguage("eng");
        // Optional: CAPTCHA के लिए बेहतर रिजल्ट हेतु PSM सेट कर सकते हैं
        tesseract.setPageSegMode(7); // 7 = Treat the image as a single text line
    }

    public String extractText(File imageFile) throws TesseractException {
        return tesseract.doOCR(imageFile).trim();
    }
}
