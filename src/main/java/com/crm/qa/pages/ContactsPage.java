package com.crm.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactsPage extends BasePage {

	public ContactsPage(WebDriver driver) {
		super(driver);
	}

	// page locators:
	private By pageLabel = By.cssSelector("div.ui.header.item.mb5.light-black");
	private By newContactBtn = By.xpath("//a[contains(@href,'contacts/new')]");

	// page actions:
	public String getContactsPageTitle() {
		return getCurrentPageTitle();
	}

	public String getContactsPageURL() {
		return getCurrentPageURL();
	}

	public WebElement getContactsPageLabel() {
		return getObject(pageLabel);
	}

	public NewContactPage clickOnNewBtn() {
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		performClick(newContactBtn);
		return getPageInstance(NewContactPage.class);
	}

	public int selectDesiredContact(List<String> names) {
		// Handling dynamic web table
		// Method 1
		String beforeXpath = "//table[contains(@class,'ui celled sortable striped table custom-grid table-scroll')]/tbody/tr[";
		String afterXpath = "]/td[2]";

		int counter = 0;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int rowCount = drivers.get().findElements(By.xpath(
				"//table[contains(@class,'ui celled sortable striped table custom-grid table-scroll')]/tbody/tr"))
				.size();
		System.out.println("Row Count: " + rowCount);

		for (int i = 1; i <= rowCount; i++) {
			WebElement element = drivers.get().findElement(By.xpath(beforeXpath + i + afterXpath));
			String name = element.getText();
			String[] nameArray = name.split(" ");

			System.out.println(nameArray[0]);

			if (names.contains(nameArray[0])) {
				element.click();
				counter++;
				if (counter >= names.size()) {
					break;
				}
			}
		}

		System.out.println(counter);
		return counter;
	}

}
