package com.insider.test.UI.testcases;

import com.insider.test.UI.utils.CommonMethods;
import com.insider.test.UI.utils.ConfigsReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class QAJobTests extends CommonMethods {
    @Test
    public void verifyQAJobFlow() {
        driver.get(ConfigsReader.getProperty("qa_jobs_url"));
            SoftAssert softAssert = new SoftAssert();

        click(qaJobsPage.cookieAccept);
        click(qaJobsPage.allJobsButton);

        openPositionsPage.filterJobs("Istanbul, Turkiye", "Quality Assurance");

        waitForVisibility(openPositionsPage.jobsList);

        openPositionsPage.verifyJobDetails("Quality Assurance",
                "Quality Assurance", "Istanbul, Turkiye", softAssert);
        softAssert.assertAll();
        openPositionsPage.clickOnViewRoleOfFirstJob();
        switchToChildWindow();


        Assert.assertTrue(driver.getCurrentUrl().contains("lever.co"), "Redirect to Lever failed!");
        Assert.assertTrue(leverPage.isApplyButtonDisplayed(), "Lever application form is not loaded properly!");



    }
}
