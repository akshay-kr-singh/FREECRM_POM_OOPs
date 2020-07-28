package com.crm.qa.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsExecutorUtil {
	
	public static void flash(WebElement element, WebDriver driver) { //highlight the element
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 2; i++) {
			changeColor("rgb(0,200,0)", element, driver);
			changeColor(bgcolor, element, driver);
		}
	}
	
	public static void changeColor(String color, WebElement element, WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '"+color+"' ", element);		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void drawBorder(WebElement element, WebDriver driver) { //draw a border
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("arguments[0].style.border = '3px solid red'", element);		
	}
	
	public static void generateAlert(WebDriver driver, String message) { //generate alert
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("alert(' "+message+" ')");		
	}
	
	public static void clickElementByJS(WebElement element, WebDriver driver) { //click on any element by using JS executor
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("arguments[0].click();", element);		
	}
	
	public static void refreshBrowserByJS(WebDriver driver) { //refresh the browser using JS executor
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("history.go(0)");
	}
	
	public static String getTitleByJS(WebDriver driver) { //get the page title using JS executor
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		String title = js.executeScript("return document.title;").toString();
		return title;
	}
	
	public static String getPageInnerText(WebDriver driver) { //get the page text (not possible using Selenium method) 
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		String pageText = js.executeScript("return document.documentElement.innerText;").toString();
		return pageText;
	}
	
	public static void scrollPageDown(WebDriver driver) { //scroll page down
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	
	public static void scrollIntoView(WebElement element, WebDriver driver) { //scroll page down till particular element is visible
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}


}
