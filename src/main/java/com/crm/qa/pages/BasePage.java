package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePage extends Page {

	public BasePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String getCurrentPageTitle() {
		return drivers.get().getTitle();
	}

	@Override
	public String getCurrentPageURL() {
		return drivers.get().getCurrentUrl();
	}

	// explicitly wait for element to be visible
	@Override
	public void waitForElementVisibility(By locator) {
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	// explicitly wait for element to be invisible
	@Override
	public void waitForElementInvisibility(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	// to perform click
	@Override
	public void performClick(By locator) {
		waitForElementVisibility(locator);
		drivers.get().findElement(locator).click();
	}

	// to send keys
	@Override
	public void populateField(By locator, String text) {
		waitForElementVisibility(locator);
		drivers.get().findElement(locator).sendKeys(text);
	}

	// to extract text
	@Override
	public String extractText(By locator) {
		waitForElementVisibility(locator);
		return drivers.get().findElement(locator).getText();
	}

	// to return WebElement
	@Override
	public WebElement getObject(By locator) {
		waitForElementVisibility(locator);
		return drivers.get().findElement(locator);
	}

	@Override
	public void refreshBrowser() {
		drivers.get().navigate().refresh();		
	}

}
