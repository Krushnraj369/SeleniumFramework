package com.jk.selenium.mcb.ocr;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class OpenCVOCRPreprocess {

    public static BufferedImage preprocessCaptchaWithOpenCV(String imagePath) throws Exception {
        // Load image with OpenCV
        Mat src = opencv_imgcodecs.imread(imagePath);
        if (src.empty()) {
            throw new Exception("❌ Image not found or empty at path: " + imagePath);
        }

        // Convert to grayscale
        Mat gray = new Mat();
        opencv_imgproc.cvtColor(src, gray, opencv_imgproc.COLOR_BGR2GRAY);

        // Apply median blur to reduce noise (try GaussianBlur if needed)
        Mat blurred = new Mat();
        opencv_imgproc.medianBlur(gray, blurred, 3);

        // Adaptive thresholding for binarization (inverted binary for Tesseract)
        Mat binary = new Mat();
        opencv_imgproc.adaptiveThreshold(
                blurred,
                binary,
                255,
                opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                opencv_imgproc.THRESH_BINARY_INV,
                11,
                2
        );

        // Morphological closing to reduce noise and join characters
        Mat kernel = opencv_imgproc.getStructuringElement(opencv_imgproc.MORPH_RECT, new org.bytedeco.opencv.opencv_core.Size(2, 2));
        opencv_imgproc.morphologyEx(binary, binary, opencv_imgproc.MORPH_CLOSE, kernel);

        // Optional: Save processed image for debug
        /*
        String debugPath = System.getProperty("user.dir") + "/debug_captcha_preprocessed.png";
        opencv_imgcodecs.imwrite(debugPath, binary);
        System.out.println("✅ Saved preprocessed captcha image at: " + debugPath);
        */

        // Convert to BufferedImage for Tesseract OCR
        return matToBufferedImage(binary);
    }

    private static BufferedImage matToBufferedImage(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int channels = mat.channels();

        BufferedImage img;
        byte[] data = new byte[width * height * channels];
        mat.data().get(data);

        if (channels > 1) {
            // Color image (not usual for captchas, but just in case)
            img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } else {
            // Grayscale image
            img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        }

        final byte[] targetPixels = ((java.awt.image.DataBufferByte) img.getRaster().getDataBuffer()).getData();
        System.arraycopy(data, 0, targetPixels, 0, data.length);

        return img;
    }
}
