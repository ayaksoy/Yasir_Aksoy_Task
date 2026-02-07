package com.insider.test.testcases;

import com.insider.test.utils.CommonMethods;
import com.insider.test.utils.ConfigsReader;
import com.insider.test.utils.MySoftAssert;
import org.testng.annotations.Test;

public class QAJobTests extends CommonMethods {
    @Test
    public void verifyQAJobFlow() {
        driver.get(ConfigsReader.getProperty("qa_jobs_url"));
            MySoftAssert mySoftAssert = new MySoftAssert();

        click(qaJobsPage.cookieAccept);
        click(qaJobsPage.allJobsButton);

        openPositionsPage.filterJobs("Istanbul, Turkiye", "Quality Assurance");

        waitForVisibility(openPositionsPage.jobsList);

        openPositionsPage.verifyJobDetails("Quality Assurance",
                "Quality Assurance", "Istanbul, Turkiye", mySoftAssert);
        openPositionsPage.clickOnViewRoleOfFirstJob();
        switchToChildWindow();


        mySoftAssert.assertTrue(driver.getCurrentUrl().contains("lever.co"), "Redirect to Lever failed!");
        mySoftAssert.assertTrue(leverPage.isApplyButtonDisplayed(), "Lever application form is not loaded properly!");


        mySoftAssert.assertAll();

    }
}
