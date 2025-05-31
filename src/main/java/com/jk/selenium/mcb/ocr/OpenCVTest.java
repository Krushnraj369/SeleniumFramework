package com.jk.selenium.mcb.ocr;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

public class OpenCVTest {

    public static void main(String[] args) {
        System.out.println("Starting OpenCV processing...");

        String inputPath = "C:\\Users\\krish\\OneDrive\\Documents\\ShareX\\Screenshots\\2025-05\\chrome_G4Xx2HOZPt.png";
        String outputPath = "C:\\Users\\krish\\OneDrive\\Documents\\ShareX\\Screenshots\\2025-05\\processed_output.png";

        // Load image
        Mat image = opencv_imgcodecs.imread(inputPath);
        if (image.empty()) {
            System.out.println("Failed to load image. Check the file path.");
            return;
        }
        System.out.println("Image loaded successfully!");

        // Grayscale conversion
        Mat gray = new Mat();
        opencv_imgproc.cvtColor(image, gray, opencv_imgproc.COLOR_BGR2GRAY);

        // Median blur to reduce noise
        Mat blurred = new Mat();
        opencv_imgproc.medianBlur(gray, blurred, 3);

        // Adaptive Thresholding
        Mat thresholded = new Mat();
        opencv_imgproc.adaptiveThreshold(
                blurred,
                thresholded,
                255,
                opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                opencv_imgproc.THRESH_BINARY,
                11,
                2
        );

        // Save the processed image
        boolean saved = opencv_imgcodecs.imwrite(outputPath, thresholded);
        if (saved) {
            System.out.println("Processed image saved successfully at: " + outputPath);
        } else {
            System.out.println("Failed to save processed image.");
        }
    }
}
