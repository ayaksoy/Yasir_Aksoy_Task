package com.insider.test.UI.testbase;


import com.insider.test.UI.pages.HomePage;
import com.insider.test.UI.pages.LeverPage;
import com.insider.test.UI.pages.OpenPositionsPage;
import com.insider.test.UI.pages.QAJobsPage;

public class PageInitializer extends BaseClass {
    public static HomePage homePage;
    public static QAJobsPage qaJobsPage;
    public static OpenPositionsPage openPositionsPage;
    public static LeverPage leverPage;

    public static void initialize() {
        homePage = new HomePage();
        qaJobsPage = new QAJobsPage();
        openPositionsPage = new OpenPositionsPage();
        leverPage = new LeverPage();
    }
}
