package com.insider.test.UI.testcases;

import com.insider.test.UI.utils.CommonMethods;
import com.insider.test.UI.utils.ConfigsReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class QAJobTests extends CommonMethods {
    @Test
    public void verifyQAJobFlow() {
        driver.get(ConfigsReader.getProperty("qa_jobs_url"));
            SoftAssert softAssert = new SoftAssert();

        click(qaJobsPage.cookieAccept);
        click(qaJobsPage.allJobsButton);

        openPositionsPage.filterJobs("Istanbul, Turkiye", "Quality Assurance");

        //waits until all the jobs locations are Istanbul, Turkiye
        openPositionsPage.checkLocationPresence("Istanbul, Turkiye");

        //it fails cuz one of the jobs title isnt Quality Assurance it is Quality Engineering
        // you can change expectedTitle to Quality to pass the test
        openPositionsPage.verifyJobDetails("Quality Assurance",
                "Quality Assurance", "Istanbul, Turkiye", softAssert);
        softAssert.assertAll();
        openPositionsPage.clickOnViewRoleOfFirstJob();
        switchToChildWindow();

        waitForVisibility(leverPage.applyButton);
        Assert.assertTrue(driver.getCurrentUrl().contains("lever.co"), "Redirect to Lever failed!");
        Assert.assertTrue(leverPage.isApplyButtonDisplayed(), "Lever application form is not loaded properly!");



    }
}
