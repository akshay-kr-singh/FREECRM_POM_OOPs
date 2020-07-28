package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.crm.qa.pages.LaunchPage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.pages.SignUpPage;

public class LaunchPageTest extends BaseTest {
	
	private LoginPage loginPage;

	@Test(priority = 1)
	public void launchPageUrlTest() {
		String url = page.getPageInstance(LaunchPage.class).getLaunchPageURL();
		Assert.assertEquals(url, "https://freecrm.co.in/", "launch page url mismatch");
	}

	@Test(priority = 2)
	public void launchPageTitleTest() {
		String title = page.getPageInstance(LaunchPage.class).getLaunchPageTitle();
		Assert.assertEquals(title, "Free CRM #1 cloud software for any business large or small", "launch page title mismatch");
	}

	@Test(priority = 3)
	public void companyLogoDisplayTest() {
		Boolean flag = page.getPageInstance(LaunchPage.class).getCompanyLogo().isDisplayed();
		Assert.assertTrue(flag);
	}

	@Test(priority = 4)
	public void companyTelephoneDisplayTest() {
		Boolean flag = page.getPageInstance(LaunchPage.class).getCompanyTelephone().isDisplayed();
		Assert.assertTrue(flag);
	}

	@Test(priority = 5)
	public void signUpPageLinkTest() {
		SignUpPage signUpPage = page.getPageInstance(LaunchPage.class).clickOnSignUpPageLink();
		String signUpPageUrl = signUpPage.getCurrentPageURL();
		Assert.assertEquals(signUpPageUrl, "https://register.freecrm.com/register/");
	}

	@Test(priority = 6)
	public void loginPageLinkTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		String loginPageUrl = loginPage.getLoginPageURL();		
		Assert.assertEquals(loginPageUrl, "https://ui.freecrm.com/");
		String loginPageTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(loginPageTitle, "Cogmento CRM");		
	}

}
