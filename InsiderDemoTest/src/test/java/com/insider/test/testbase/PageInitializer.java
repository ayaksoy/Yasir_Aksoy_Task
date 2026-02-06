package com.insider.test.testbase;


import com.insider.test.pages.HomePage;
import com.insider.test.pages.QAJobsPage;

public class PageInitializer extends BaseClass {
    public static HomePage homePage;
    public static QAJobsPage qaJobsPage;

    public static void initialize() {
        homePage = new HomePage();
        qaJobsPage = new QAJobsPage();
    }
}
