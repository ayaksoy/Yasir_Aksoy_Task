package com.insider.test.UI.testbase;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.insider.test.UI.utils.CommonMethods;

public class Listener implements ITestListener {

//	public void onStart(ITestContext context) {
//		System.out.println("Functionality test started!");
//	}
//
//	public void onFinish(ITestContext context) {
//		System.out.println("Functionality test finished!");
//	}

//	public void onTestStart(ITestResult result) {
//		System.out.println(result.getName() + " test is starting!");
//
//		// create a test report just before the @Test starts
//		BaseClass.test = BaseClass.report.createTest(result.getName());
//	}
//
//	public void onTestSuccess(ITestResult result) {
//		System.out.println(result.getName() + " just passed!");
//
//		// print the test pass on report
//		BaseClass.test.pass("Test passed: " + result.getName());
//
//		// take screenshot and add it to the report
//		String screenShotPath = CommonMethods.takeScreenshot(result.getName() + ".png");
//		BaseClass.test.addScreenCaptureFromPath(screenShotPath);
//	}

	public void onTestFailure(ITestResult result) {
		System.out.println(result.getName() + " just failed!!!");

		// print the test fail on report
		BaseClass.test.fail("Test failed: " + result.getName());

		// take screenshot and add it to the report
		String screenShotPath = CommonMethods.takeScreenshot(result.getName() + ".png");
		BaseClass.test.addScreenCaptureFromPath(screenShotPath);
	}
}
