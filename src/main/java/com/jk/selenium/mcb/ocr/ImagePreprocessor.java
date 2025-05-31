package com.jk.selenium.mcb.ocr;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImagePreprocessor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static String preprocessAndSave(String inputFilePath, String outputFilePath) {
        Mat image = Imgcodecs.imread(inputFilePath);

        if (image.empty()) {
            System.err.println("Failed to load image for preprocessing: " + inputFilePath);
            return null;
        }

        // Convert to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        // Apply Gaussian blur
        Imgproc.GaussianBlur(gray, gray, new Size(3, 3), 0);

        // Adaptive threshold to get binary image
        Mat thresh = new Mat();
        Imgproc.adaptiveThreshold(gray, thresh, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY_INV, 11, 2);

        // Save preprocessed image
        Imgcodecs.imwrite(outputFilePath, thresh);

        return outputFilePath;
    }
}
