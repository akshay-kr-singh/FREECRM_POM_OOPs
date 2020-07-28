package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	// page locators:
	private By userDisplay = By.xpath("//span[@class='user-display']");

	// page actions:
	public String getHomePageTitle() {
		return getCurrentPageTitle();
	}

	public String getHomePageURL() {
		return getCurrentPageURL();
	}

	public WebElement getUserNameDisplay() {
		return getObject(userDisplay);
	}

}
