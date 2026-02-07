package com.insider.test.UI.testbase;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.insider.test.UI.utils.CommonMethods;

public class Listener implements ITestListener {


	public void onTestStart(ITestResult result) {
		System.out.println(result.getName() + " test is starting!");
		BaseClass.test = BaseClass.report.createTest(result.getName());
	}

    @Override
    public void onTestFailure(ITestResult result) {
        BaseClass.test.fail("Test fail: " + result.getName());
        BaseClass.test.fail(result.getThrowable());

        String base64Screenshot = CommonMethods.takeScreenshot(result.getName());
        BaseClass.test.addScreenCaptureFromBase64String(base64Screenshot);
    }
}
