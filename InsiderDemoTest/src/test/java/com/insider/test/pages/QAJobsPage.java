package com.insider.test.pages;

import com.insider.test.testbase.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QAJobsPage {

    @FindBy(css = "div.header-top div.container")
    public WebElement allJobsButton;

    public QAJobsPage() {
        PageFactory.initElements(BaseClass.driver, this);
    }

}
