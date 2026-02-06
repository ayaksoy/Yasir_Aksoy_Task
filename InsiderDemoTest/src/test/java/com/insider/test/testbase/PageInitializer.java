package com.insider.test.testbase;


import com.insider.test.pages.HomePage;

public class PageInitializer extends BaseClass {
    public static HomePage homePage;
//
//	public static LoginPageElements loginPage;
//	public static DashboardPageElements dashboardPage;
//	public static AddEmployeePageElements addEmployeePage;
//	public static PersonalDetailsPageElements personalDetails;
//
//	public static void initialize() {
//		loginPage = new LoginPageElements();
//		dashboardPage = new DashboardPageElements();
//		addEmployeePage = new AddEmployeePageElements();
//		personalDetails = new PersonalDetailsPageElements();
//	}
public static void initialize() {
    homePage = new HomePage();
    }
}
