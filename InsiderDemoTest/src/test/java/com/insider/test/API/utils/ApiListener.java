package com.insider.test.API.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ApiListener implements ITestListener {

    public static ExtentReports extent;
    public static ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/API-Report.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("API Test Report");
        spark.config().setReportName("Insider API Automation Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Ahmet");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getDescription();
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName();
        }
        test = extent.createTest(testName);
        test.info("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed Successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Failed");
        test.fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test Skipped");
        test.skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}