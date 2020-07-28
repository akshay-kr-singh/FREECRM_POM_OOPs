package com.crm.qa.testcases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LaunchPage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.testdata.FreeCrmTestData;

public class LoginPageTest extends BaseTest {
	private LoginPage loginPage;
	private HomePage homePage;
	
	@Test(priority = 1)
	public void loginPageUrlTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		String loginPageUrl = loginPage.getLoginPageURL();
		Assert.assertEquals(loginPageUrl, "https://ui.freecrm.com/", "login page url mismatch");
	}
	
	@Test(priority = 2)
	public void loginPageTitleTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		String loginPageTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(loginPageTitle, "Cogmento CRM", "login page title mismatch");
	}

	@Test(priority = 3)
	public void customerLoginTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		WebElement userName = homePage.getUserNameDisplay();
		Assert.assertTrue(userName.isDisplayed());
		String customer_user_name = FreeCrmTestData.getUserLoginInfo().get("customer_user_name");
		Assert.assertEquals(userName.getText(), customer_user_name);
	}
	
	@Test(priority = 4)
	public void validateResetPasswordPageLinkTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		loginPage.clickOnForgotYourPasswordLink();
	}

}
