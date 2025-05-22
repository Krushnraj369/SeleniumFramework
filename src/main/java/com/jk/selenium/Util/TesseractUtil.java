package com.mcb.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class TesseractUtil {

    private final Tesseract tesseract;

    public TesseractUtil(String dataPath) {
        tesseract = new Tesseract();
        tesseract.setDatapath(dataPath);
        tesseract.setLanguage("eng");
    }

    public String doOCR(File imageFile) {
        try {
            return tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
            return "OCR Failed: " + e.getMessage();
        }
    }
}
