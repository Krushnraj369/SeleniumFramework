package com.jk.selenium.mcb.ocr;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class ImagePreprocessor {

    static {
        // Load OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Preprocess image for OCR using OpenCV.
     * Steps:
     * 1) Grayscale
     * 2) Resize (scale up)
     * 3) Gaussian blur (denoise)
     * 4) Adaptive Threshold (binarization with white background and black text)
     * 5) Morphological operations (dilate/erode)
     * 6) Sharpening filter
     *
     * @param inputMat OpenCV Mat input image (BGR or RGB)
     * @return Processed Mat (grayscale, binary) ready for OCR
     */
    public static Mat preprocessImage(Mat inputMat) {
        Mat gray = new Mat();
        Mat resized = new Mat();
        Mat blurred = new Mat();
        Mat thresh = new Mat();
        Mat morph = new Mat();
        Mat sharpened = new Mat();

        // 1. Convert to grayscale
        Imgproc.cvtColor(inputMat, gray, Imgproc.COLOR_BGR2GRAY);

        // 2. Resize (scale up by 2x for better OCR)
        Imgproc.resize(gray, resized, new Size(gray.width() * 2, gray.height() * 2), 0, 0, Imgproc.INTER_LINEAR);

        // 3. Gaussian blur to reduce noise
        Imgproc.GaussianBlur(resized, blurred, new Size(3, 3), 0);

        // 4. Adaptive thresholding (binary image)
        // THRESH_BINARY: white background + black text (Tesseract ke liye ideal)
        Imgproc.adaptiveThreshold(
                blurred,
                thresh,
                255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY,
                11,
                2
        );

        // Agar aapko image invert karna ho (black background white text se white background black text banane ke liye), uncomment karein:
        // Core.bitwise_not(thresh, thresh);

        // 5. Morphological operations to clean the text
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3)); // 3x3 kernel for better text cleaning
        Imgproc.morphologyEx(thresh, morph, Imgproc.MORPH_CLOSE, kernel);

        // 6. Sharpening with kernel
        Mat kernelSharpen = new Mat(3, 3, CvType.CV_32F) {
            {
                put(0, 0, 0);   put(0, 1, -1);  put(0, 2, 0);
                put(1, 0, -1);  put(1, 1, 5);   put(1, 2, -1);
                put(2, 0, 0);   put(2, 1, -1);  put(2, 2, 0);
            }
        };
        Imgproc.filter2D(morph, sharpened, -1, kernelSharpen);

        // Cleanup mats to free memory (optional)
        gray.release();
        resized.release();
        blurred.release();
        thresh.release();
        morph.release();

        return sharpened;
    }
}
