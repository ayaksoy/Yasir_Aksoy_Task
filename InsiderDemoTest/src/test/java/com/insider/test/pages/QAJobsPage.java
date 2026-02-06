package com.insider.test.pages;

import com.insider.test.testbase.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QAJobsPage {

    @FindBy(linkText = "See all QA jobs")
    public WebElement allJobsButton;

    @FindBy(id = "wt-cli-accept-all-btn")
    public WebElement cookieAccept;

    public QAJobsPage() {
        PageFactory.initElements(BaseClass.driver, this);
    }

}
