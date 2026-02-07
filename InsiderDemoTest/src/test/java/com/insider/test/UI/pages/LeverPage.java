package com.insider.test.UI.pages;
import com.insider.test.UI.utils.CommonMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeverPage extends CommonMethods {
    @FindBy(xpath = "//a[text()='Apply for this job']")
    public WebElement applyButton;

    public LeverPage() {
        PageFactory.initElements(driver, this);
    }

    public boolean isApplyButtonDisplayed() {
        return applyButton.isDisplayed();
    }
}