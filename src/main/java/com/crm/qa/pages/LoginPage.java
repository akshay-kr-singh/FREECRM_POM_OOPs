package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.crm.qa.testdata.FreeCrmTestData;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// page locators:
	private By email = By.xpath("//input[@name='email']");
	private By password = By.xpath("//input[@name='password']");
	private By loginBtn = By.xpath("//div[text()='Login']");
	private By resetPasswordPageLink = By.xpath("//a[text()='Forgot your password?']");

	// page actions:
	public String getLoginPageTitle() {
		return getCurrentPageTitle();
	}

	public String getLoginPageURL() {
		return getCurrentPageURL();
	}

	public ResetPasswordPage clickOnForgotYourPasswordLink() {
		performClick(resetPasswordPageLink);
		return getPageInstance(ResetPasswordPage.class);
	}

	public HomePage customerLogin() {
		String loginInfo = FreeCrmTestData.getUserLoginInfo().get("customer");
		String[] credentials = loginInfo.split("_");

		populateField(email, credentials[0]);
		populateField(password, credentials[1]);
		performClick(loginBtn);
		return getPageInstance(HomePage.class);
	}

}
