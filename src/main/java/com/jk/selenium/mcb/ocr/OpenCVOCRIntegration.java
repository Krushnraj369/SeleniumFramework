package com.jk.selenium.mcb.ocr;

import com.jk.selenium.Util.ConfigReader;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;

public class OpenCVOCRIntegration {

    public static String doOCRWithOpenCV() {
        try {
            // Read paths from config
            String inputImagePath = ConfigReader.getProperty("ocr.image.path");
            String tessDataPath = ConfigReader.getProperty("tesseract.datapath");

            // Load image
            Mat src = opencv_imgcodecs.imread(inputImagePath);
            if (src.empty()) {
                System.err.println("Failed to load image: " + inputImagePath);
                return "";
            }

            // Preprocessing: grayscale, blur, adaptive threshold
            Mat gray = new Mat();
            opencv_imgproc.cvtColor(src, gray, opencv_imgproc.COLOR_BGR2GRAY);

            Mat blurred = new Mat();
            opencv_imgproc.GaussianBlur(gray, blurred, new org.bytedeco.opencv.opencv_core.Size(3, 3), 0);

            Mat thresh = new Mat();
            opencv_imgproc.adaptiveThreshold(
                    blurred, thresh, 255,
                    opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                    opencv_imgproc.THRESH_BINARY_INV, 11, 2);

            // Optionally save processed image
            String processedPath = "processed_captcha.png";
            opencv_imgcodecs.imwrite(processedPath, thresh);
            System.out.println("Processed captcha saved at: " + processedPath);

            // Convert Mat to BufferedImage
            BufferedImage bufferedImage = matToBufferedImage(thresh);

            // Setup Tesseract OCR
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tessDataPath);
            tesseract.setLanguage("eng");
            tesseract.setPageSegMode(7);
            tesseract.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");

            // OCR execution
            String result = tesseract.doOCR(bufferedImage);
            result = result.replaceAll("[^A-Za-z0-9]", "").trim();

            System.out.println("OCR Result: " + result);
            return result;

        } catch (TesseractException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static BufferedImage matToBufferedImage(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int channels = mat.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        mat.data().get(sourcePixels);

        BufferedImage image;
        if (channels > 1) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        }

        final byte[] targetPixels = ((java.awt.image.DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
        return image;
    }

    public static void main(String[] args) {
        String extractedText = doOCRWithOpenCV();
        System.out.println("Extracted Captcha Text: " + extractedText);
    }
}
