/**package com.jk.selenium.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**public class ReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReports() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            extent.attachReporter(spark);
        }
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void logInfo(String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    public static void logPass(String message) {
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    public static void logFail(String message) {
        if (test != null) {
            test.log(Status.FAIL, message);
        }
    }
}
*/