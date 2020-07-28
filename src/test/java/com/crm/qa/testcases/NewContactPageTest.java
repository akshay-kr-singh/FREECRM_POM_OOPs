package com.crm.qa.testcases;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.FixedLeftPanel;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LaunchPage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.pages.NewContactPage;
import com.crm.qa.util.DataDrivenUtil;

public class NewContactPageTest extends BaseTest {
	private LoginPage loginPage;
	private HomePage homePage;
	private FixedLeftPanel fixedLeftPanel;
	private ContactsPage contactsPage;
	private NewContactPage newContactPage;
	

	@Test(priority = 1)
	public void newContactPageUrlTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		fixedLeftPanel = homePage.getPageInstance(FixedLeftPanel.class);
		contactsPage = fixedLeftPanel.clickOnContactsPageLink();
		newContactPage = contactsPage.clickOnNewBtn();
		String newContactPageUrl = newContactPage.getNewContactPageURL();
		Assert.assertEquals(newContactPageUrl, "https://ui.freecrm.com/contacts/new", "new contact page url mismatch");
	}

	@Test(priority = 2)
	public void newContactPageTitleTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		fixedLeftPanel = homePage.getPageInstance(FixedLeftPanel.class);
		contactsPage = fixedLeftPanel.clickOnContactsPageLink();
		newContactPage = contactsPage.clickOnNewBtn();
		String newContactPageTitle = newContactPage.getCurrentPageTitle();
		Assert.assertEquals(newContactPageTitle, "Cogmento CRM", "new contact page title mismatch");
	}

	@Test(priority = 3)
	public void newContactPageLabelTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		fixedLeftPanel = homePage.getPageInstance(FixedLeftPanel.class);
		contactsPage = fixedLeftPanel.clickOnContactsPageLink();
		newContactPage = contactsPage.clickOnNewBtn();
		Boolean flag = newContactPage.getNewContactPageLabel().isDisplayed();
		Assert.assertTrue(flag);
		String text = newContactPage.getNewContactPageLabel().getText();
		Assert.assertEquals(text, "Create New Contact", "new contact page label mismatch");
	}

	@Test(priority = 4, dataProvider = "getData")
	private void createNewContactTest(String fn, String mn, String ln) {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		fixedLeftPanel = homePage.getPageInstance(FixedLeftPanel.class);
		contactsPage = fixedLeftPanel.clickOnContactsPageLink();
		newContactPage = contactsPage.clickOnNewBtn();
		newContactPage.createNewContact(fn, mn, ln);
		newContactPage.waitForElementVisibility(By.xpath("//div[contains(text(),'Identifier')]"));
	}

	@DataProvider
	private Iterator<Object[]> getData() {
		List<Object[]> newContactsInfo = DataDrivenUtil.getNewContacts();
		return newContactsInfo.iterator();
	}

}
