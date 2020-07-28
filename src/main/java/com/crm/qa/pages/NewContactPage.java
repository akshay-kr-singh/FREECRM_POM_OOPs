package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewContactPage extends BasePage {

	public NewContactPage(WebDriver driver) {
		super(driver);
	}

	// page locators:
	private By pageLabel = By.cssSelector("div.ui.header.item.mb5.light-black");
	private By firstName = By.xpath("//input[@name='first_name']");
	private By middleName = By.xpath("//input[@name='middle_name']");
	private By lastName = By.xpath("//input[@name='last_name']");
//	private By company = By.xpath("//div[@name='company']");
	private By saveBtn = By.cssSelector("button.ui.linkedin.button");
//	private By cancelBtn = By.xpath("//button[@class='ui button']");

	// page actions
	public String getNewContactPageTitle() {
		return getCurrentPageTitle();
	}

	public String getNewContactPageURL() {
		return getCurrentPageURL();
	}

	public WebElement getNewContactPageLabel() {
		return getObject(pageLabel);
	}

	public void createNewContact(String fn, String mn, String ln) {
			populateField(firstName, fn);
			populateField(middleName, mn);
			populateField(lastName, ln);
			performClick(saveBtn);	
	}
}
