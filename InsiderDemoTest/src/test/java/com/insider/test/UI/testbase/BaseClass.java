package com.insider.test.UI.testbase;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.insider.test.UI.utils.ConfigsReader;
import com.insider.test.UI.utils.Constants;

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
	public void setUp() {
		ConfigsReader.readProperties(Constants.CONFIGURATION_FILEPATH);
		String browser = ConfigsReader.getProperty("browser");

		driver = null;
		switch (browser.toLowerCase()) {
		case "chrome": {
            driver = new ChromeDriver(getChromeOptions());
			break;
		}
		case "firefox": {
			driver = new FirefoxDriver(getFirefoxOptions());
			break;
		}
		default:
			break;
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT_TIME));
		PageInitializer.initialize();
	}

    public ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--ignore-certificate-errors");
        options.setAcceptInsecureCerts(true);
        return options;
    }

    public FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("geo.enabled", false);
        options.addPreference("network.manage-offline-status", false);
        options.setAcceptInsecureCerts(true);
        return options;
    }

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
