package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FixedLeftPanel extends BasePage {

	public FixedLeftPanel(WebDriver driver) {
		super(driver);
	}

	// page locators:
	private By contactsPageLink = By.xpath("//span[contains(text(),'Contacts')]/parent::a");
//	private By companiesPageLink = By.xpath("//span[contains(text(),'Companies')]/ancestor::a");
//	private By dealsPageLink = By.xpath("//span[contains(text(),'Deals')]/parent::a");

	// page actions:
	public ContactsPage clickOnContactsPageLink() {
		performClick(contactsPageLink);
		return getPageInstance(ContactsPage.class);
	}

}
