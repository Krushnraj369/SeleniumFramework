package com.jk.selenium.mcb.ocr;

import java.awt.image.BufferedImage;

public class Tesseract {
    public void setDatapath(String s) {
        // फिलहाल कोई implementation नहीं
    }

    public String doOCR(BufferedImage imageFile) {
        // असली OCR यहां implement करें
        // फिलहाल खाली स्ट्रिंग वापस कर रहा हूं ताकि compile error न आए
        return "";
    }

    public void setLanguage(String eng) {
    }

    public void setPageSegMode(int i) {
    }

    public void setTessVariable(String tesseditCharWhitelist, String abcdefghijklmnopqrstuvwxyZabcdefghijklmnopqrstuvwxyz0123456789) {
    }
}
