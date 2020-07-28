package com.crm.qa.testcases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.FixedLeftPanel;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LaunchPage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.DataDrivenUtil;

public class ContactsPageTest extends BaseTest {
	private LoginPage loginPage;
	private HomePage homePage;
	private FixedLeftPanel fixedLeftPanel;
	private ContactsPage contactsPage;
	
	@Test(priority=1)
	public void contactsPageUrlTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		fixedLeftPanel = homePage.getPageInstance(FixedLeftPanel.class);
		contactsPage = fixedLeftPanel.clickOnContactsPageLink();
		String contactsPageUrl = contactsPage.getContactsPageURL();
		Assert.assertEquals(contactsPageUrl, "https://ui.freecrm.com/contacts","contacts page url mismatch");
	}
	
	@Test(priority=2)
	public void contactsPageTitleTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		fixedLeftPanel = homePage.getPageInstance(FixedLeftPanel.class);
		contactsPage = fixedLeftPanel.clickOnContactsPageLink();
		String contactsPageTitle = contactsPage.getContactsPageTitle();
		Assert.assertEquals(contactsPageTitle, "Cogmento CRM", "contacts page title mismatch");
	}

	@Test(priority=3)
	public void contactsPageLabelTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		fixedLeftPanel = homePage.getPageInstance(FixedLeftPanel.class);
		contactsPage = fixedLeftPanel.clickOnContactsPageLink();
		Boolean flag = contactsPage.getContactsPageLabel().isDisplayed();
		Assert.assertTrue(flag);
		String text = contactsPage.getContactsPageLabel().getText();
		Assert.assertEquals(text, "Contacts", "contacts page label mismatch");
	}
	
	@Test(priority=4)
	public void selectDesiredContactTest() {
		loginPage = page.getPageInstance(LaunchPage.class).clickOnLoginPageLink();
		homePage = loginPage.customerLogin();
		fixedLeftPanel = homePage.getPageInstance(FixedLeftPanel.class);
		contactsPage = fixedLeftPanel.clickOnContactsPageLink();
		
		List<String> f_names = new ArrayList<String>();
		List<Object[]> contactInfo = DataDrivenUtil.getNewContacts();
		Iterator<Object[]> contactInfoItr = contactInfo.iterator();
		while(contactInfoItr.hasNext()) {
			Object[] name = contactInfoItr.next();
			System.out.println((String)name[0]);
			f_names.add((String)name[0]);			
		}
		
		System.out.println(f_names .toString());
		
		int counter = contactsPage.selectDesiredContact(f_names);
		Assert.assertTrue(counter == f_names.size());
	}

}
