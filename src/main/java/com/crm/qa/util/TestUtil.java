package com.crm.qa.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.crm.qa.pages.BasePage;

public class TestUtil extends BasePage {
	
	public TestUtil(WebDriver driver) {
		super(driver);
	}
	
	public String getScreenshotPath(String testName) throws IOException {
		TakesScreenshot ts = ((TakesScreenshot)drivers.get());
		File src = ts.getScreenshotAs(OutputType.FILE);
		String pathToScreenshot = System.getProperty("user.dir") + "\\screenshot\\" + testName + ".png";
		FileUtils.copyFile(src, new File(pathToScreenshot));
		return pathToScreenshot;		
	}
	
	
	public static ExtentReports getExtentReportsObject() {
		String pathToExtentReport = System.getProperty("user.dir") + "\\reports\\freecrm_extent.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(pathToExtentReport);
		reporter.config().setDocumentTitle("FREECRM Test Result");
		reporter.config().setReportName("Daily Sanity Test Report");
		
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Akshay");
		
		return extent;
	}
	
	public void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) drivers.get()).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	
}
