package com.jk.selenium.mcb.ocr;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;  // ✅ Correct import

public class OpenCVCheck {
    public static void main(String[] args) {
        System.out.println("Starting OpenCV check...");

        String inputPath = "C:\\Users\\krish\\OneDrive\\Documents\\ShareX\\Screenshots\\2025-05\\chrome_G4Xx2HOZPt.png";
        String outputPath = "C:\\Users\\krish\\OneDrive\\Documents\\ShareX\\Screenshots\\2025-05\\processed_output.png";

        // Load image using OpenCV
        Mat image = opencv_imgcodecs.imread(inputPath);
        if (image.empty()) {
            System.out.println("Failed to load image. Check the file path.");
            return;
        }
        System.out.println("Image loaded successfully!");

        // Convert to grayscale
        Mat gray = new Mat();
        opencv_imgproc.cvtColor(image, gray, opencv_imgproc.COLOR_BGR2GRAY);

        // Apply Gaussian blur
        Mat blurred = new Mat();
        opencv_imgproc.GaussianBlur(gray, blurred, new Size(3, 3), 0);  // ✅ Use Size from correct package

        // Save the processed image
        boolean saved = opencv_imgcodecs.imwrite(outputPath, blurred);
        if (saved) {
            System.out.println("Processed image saved successfully at: " + outputPath);
        } else {
            System.out.println("Failed to save processed image.");
        }
    }
}
