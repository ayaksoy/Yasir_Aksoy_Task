package com.insider.test.testcases;

import com.insider.test.utils.CommonMethods;
import com.insider.test.utils.ConfigsReader;
import com.insider.test.utils.MySoftAssert;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTests extends CommonMethods {
    @Test
    public void verifyHomePageBlocks() {
        driver.get(ConfigsReader.getProperty("homepage_url"));
        Assert.assertTrue(driver.getTitle().contains("Insider"), "Page title wrong!");


        MySoftAssert softAssert = new MySoftAssert();
        scrollToElement(homePage.navigationBar);
        waitForVisibility(homePage.navigationBar);
        softAssert.assertTrue(homePage.navigationBar.isDisplayed(), "Navbar not loaded!");

        scrollToElement(homePage.heroSection);
        waitForVisibility(homePage.heroSection);
        softAssert.assertTrue(homePage.heroSection.isDisplayed(), "Hero section not loaded!");

        scrollToElement(homePage.trustedBySection);
        waitForVisibility(homePage.trustedBySection);
        softAssert.assertTrue(homePage.trustedBySection.isDisplayed(), "TrustedBy section not loaded!");

        scrollToElement(homePage.coreDifferentiators);
        waitForVisibility(homePage.coreDifferentiators);
        softAssert.assertTrue(homePage.coreDifferentiators.isDisplayed(), "coreDifferentiators section not loaded!");

        scrollToElement(homePage.capabilitiesSection);
        waitForVisibility(homePage.capabilitiesSection);
        softAssert.assertTrue(homePage.capabilitiesSection.isDisplayed(), "capabilities section not loaded!");

        scrollToElement(homePage.footer);
        waitForVisibility(homePage.footer);
        softAssert.assertTrue(homePage.footer.isDisplayed(), "Footer Section not loaded!");



        softAssert.assertAll();
    }
}
