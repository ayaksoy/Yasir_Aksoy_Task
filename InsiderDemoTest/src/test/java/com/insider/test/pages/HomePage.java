package com.insider.test.pages;

import com.insider.test.testbase.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(css = ".header-logo  svg")
    public WebElement headerLogo;

    @FindBy(id = "navigation")
    public WebElement navigationBar;

    @FindBy(xpath = "//h1[contains(text(),'Be unstoppable')]")
    public WebElement heroSection;

    @FindBy(xpath = "//h2[contains(text(),'TRUSTED BY 2,000+ CUSTOMERS')]")
    public WebElement trustedBySection;

    @FindBy(className = "homepage-core-differentiators")
    public WebElement coreDifferentiators;

    @FindBy(className = "homepage-capabilities")
    public WebElement capabilitiesSection;

    @FindBy(tagName = "footer")
    public WebElement footer;

    public HomePage() {
        PageFactory.initElements(BaseClass.driver, this);
    }
}