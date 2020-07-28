package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LaunchPage extends BasePage {

	public LaunchPage(WebDriver driver) {
		super(driver);
	}

	// page locators:
	private By companyLogo = By.xpath("//div[@class='rd-navbar-wrap'] //div[@class='rd-navbar-brand']");
	private By companyTelephone = By.xpath("//div[@class='top-panel clearfix'] //div[@class='telephone']");
	private By signUpPageLink = By.xpath("//a[text()='Sign Up']");
	private By loginPageLink = By.xpath("//span[text()='Log In']/parent::a");

	// page actions:
	public String getLaunchPageTitle() {
		return getCurrentPageTitle();
	}

	public String getLaunchPageURL() {
		return getCurrentPageURL();
	}

	public WebElement getCompanyLogo() {
		return getObject(companyLogo);
	}

	public WebElement getCompanyTelephone() {
		return getObject(companyTelephone);
	}

	public SignUpPage clickOnSignUpPageLink() {
		performClick(signUpPageLink);
		return getPageInstance(SignUpPage.class);
	}

	public LoginPage clickOnLoginPageLink() {
		performClick(loginPageLink);
		return getPageInstance(LoginPage.class);
	}

}
