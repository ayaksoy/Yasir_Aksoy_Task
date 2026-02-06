package com.insider.test.utils;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

public class MySoftAssert extends SoftAssert {

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        String methodName = assertCommand.getMessage();

        CommonMethods.takeScreenshot("SoftAssert_Failure.png");
    }
}