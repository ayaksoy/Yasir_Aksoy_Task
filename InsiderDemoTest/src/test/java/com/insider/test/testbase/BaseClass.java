package com.insider.test.testbase;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.insider.test.utils.ConfigsReader;
import com.insider.test.utils.Constants;

public class BaseClass {

	public static WebDriver driver;

	// extent report objects
	public static ExtentSparkReporter reporter;
	public static ExtentReports report;
	public static ExtentTest test;

	@BeforeTest(alwaysRun = true)
	public void generateReport() {
		reporter = new ExtentSparkReporter(Constants.REPORT_FILEPATH);
		reporter.config().setDocumentTitle("HRM Test Results Report");
		reporter.config().setReportName("HRM Report");
		reporter.config().setTheme(Theme.DARK);

		report = new ExtentReports();
		report.attachReporter(reporter);
	}

	@AfterTest(alwaysRun = true)
	public void writeReport() {
		report.flush();
	}

	@BeforeMethod(alwaysRun = true)
	public static void setUp() {
		ConfigsReader.readProperties(Constants.CONFIGURATION_FILEPATH);
		String browser = ConfigsReader.getProperty("browser");

		driver = null;
		switch (browser.toLowerCase()) {
		case "chrome": {
			driver = new ChromeDriver();
			break;
		}
		case "firefox": {
			driver = new FirefoxDriver();
			break;
		}
		default:
			break;
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT_TIME));
		String url = ConfigsReader.getProperty("url");
		driver.get(url);

		PageInitializer.initialize();
	}

	@AfterMethod(alwaysRun = true)
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
