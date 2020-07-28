package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {

	public ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
	public WebDriverWait wait;

	public Page(WebDriver driver) {
		this.drivers.set(driver);
		wait = new WebDriverWait(drivers.get(), 40);
	}

	public abstract String getCurrentPageTitle();

	public abstract String getCurrentPageURL();
	
	public abstract void waitForElementVisibility(By locator);
	
	public abstract void waitForElementInvisibility(By locator);
	
	public abstract void performClick(By locator);
	
	public abstract void populateField(By locator, String text);
	
	public abstract String extractText(By locator);
	
	public abstract WebElement getObject(By locator);
	
	public abstract void refreshBrowser();

	public <TPage extends BasePage> TPage getPageInstance(Class<TPage> pageClass) {
		try {
			return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(this.drivers.get());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
