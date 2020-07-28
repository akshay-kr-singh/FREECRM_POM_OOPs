package com.crm.qa.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestNGRetryAnalyzerListener implements ITestListener, IRetryAnalyzer, IAnnotationTransformer {
	private int counter = 0;
	private int retryLimit = 2;
	private ExtentReports extent = TestUtil.getExtentReportsObject();
	private ExtentTest test;
	private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	private TestUtil util;
	private ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

	// method from IRetryAnalyzer interface
	public boolean retry(ITestResult result) {
		if (counter < retryLimit) {
			counter++;
			return true;
		}
		return false;
	}

	// method from IAnnotationTransformer interface
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// class in which retry() method (from IRetryAnalyzer interface) is implemented
		// is passed as the argument
		annotation.setRetryAnalyzer(TestNGRetryAnalyzerListener.class);
	}

	public synchronized void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		test = extent.createTest(testName);
		extentTest.set(test);
		try {
			drivers.set((WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
		util = new TestUtil(drivers.get());
		try {
			extentTest.get().addScreenCaptureFromPath(util.getScreenshotPath(result.getMethod().getMethodName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());
		util = new TestUtil(drivers.get());
		try {
			extentTest.get().addScreenCaptureFromPath(util.getScreenshotPath(result.getMethod().getMethodName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void onTestSkipped(ITestResult result) {
		extentTest.get().log(Status.SKIP, "Test Skipped");
	}

	public synchronized void onFinish(ITestContext context) {
		extent.flush();

	}

}
