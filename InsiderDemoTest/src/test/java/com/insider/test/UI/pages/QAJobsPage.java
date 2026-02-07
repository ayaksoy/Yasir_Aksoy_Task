package com.insider.test.UI.pages;

import com.insider.test.UI.testbase.BaseClass;
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
