package com.insider.test.UI.testbase;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.insider.test.UI.utils.ConfigsReader;
import com.insider.test.UI.utils.Constants;

public class BaseClass {

	public static WebDriver driver;

	public static ExtentSparkReporter reporter;
	public static ExtentReports report;
	public static ExtentTest test;

    @BeforeSuite(alwaysRun = true)
	public void generateReport() {
		reporter = new ExtentSparkReporter(Constants.REPORT_FILEPATH);
		reporter.config().setDocumentTitle("Insider Test Results Report");
		reporter.config().setReportName("Insider Report");
		reporter.config().setTheme(Theme.DARK);

		report = new ExtentReports();
		report.attachReporter(reporter);
	}

	@AfterSuite(alwaysRun = true)
	public void writeReport() {
		report.flush();
	}

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {
        ConfigsReader.readProperties(Constants.CONFIGURATION_FILEPATH);
        if (browser == null || browser.isEmpty()) {
            browser = ConfigsReader.getProperty("browser");
        }

        driver = null;
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver(getChromeOptions());
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                driver = new ChromeDriver(getChromeOptions());
                break;
        }

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

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
