package com.insider.test.UI.pages;

import com.insider.test.UI.utils.CommonMethods;
import com.insider.test.UI.utils.MySoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OpenPositionsPage extends CommonMethods {

    private final By positionTitle = By.className("position-title");
    private final By positionDepartment = By.className("position-department");
    private final By positionLocation = By.className("position-location");
    private final By viewRoleButton = By.linkText("View Role");

    @FindBy(id = "filter-by-location")
    public WebElement locationSelect;

    @FindBy(id = "filter-by-department")
    public WebElement departmentSelect;

    @FindBy(id = "jobs-list")
    public WebElement jobsList;

    @FindBy(className = "position-list-item")
    public List<WebElement> allJobs;

    public void filterJobs(String location, String department) {
        selectDropdown(locationSelect,location);
        wait(5);
        selectDropdown(departmentSelect,department);
    }

    public void verifyJobDetails(String expectedTitle, String expectedDept, String expectedLoc, MySoftAssert softAssert) {
        for (WebElement job : allJobs) {
            String actualTitle = job.findElement(positionTitle).getText();
            String actualDept = job.findElement(positionDepartment).getText();
            String actualLoc = job.findElement(positionLocation).getText();

            softAssert.assertTrue(actualTitle.contains(expectedTitle),
                    "Position mismatch! Expected: " + expectedTitle + ", but found: " + actualTitle);
            softAssert.assertTrue(actualDept.contains(expectedDept),
                    "Department mismatch! Expected: " + expectedDept + ", but found: " + actualDept);
            softAssert.assertTrue(actualLoc.contains(expectedLoc),
                    "Location mismatch! Expected: " + expectedLoc + ", but found: " + actualLoc);
        }
    }

    public void clickOnViewRoleOfFirstJob() {
        scrollToElement(allJobs.get(0));
        waitForClickability(allJobs.get(0).findElement(viewRoleButton));

        jsClick(allJobs.get(0).findElement(viewRoleButton));
    }


    public OpenPositionsPage() {
        PageFactory.initElements(driver, this);
    }
}
