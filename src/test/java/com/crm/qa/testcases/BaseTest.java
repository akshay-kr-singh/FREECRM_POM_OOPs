package com.crm.qa.testcases;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.crm.qa.pages.BasePage;
import com.crm.qa.pages.Page;
import com.crm.qa.util.WebEventListener;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
//	private EventFiringWebDriver e_driver;
//	private WebEventListener eventListener;	
	public Properties prop;
	public Page page;
	ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

	public BaseTest() {
		prop = new Properties();
		String pathToPropertiesFile = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\crm\\qa\\config\\config.properties";
		try {
			FileInputStream fis = new FileInputStream(pathToPropertiesFile);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Parameters({ "browserName" })
	@BeforeMethod
	public synchronized void setUp(String browserName) {
		if (browserName.contains("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if (browserName.contains("headless"))
				options.addArguments("headless");
			driver = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}

//		e_driver = new EventFiringWebDriver(driver);
		// Now create object of WebEventLister to register it with EventFiringWebDriver
//		eventListener = new WebEventListener();
//		e_driver.register(eventListener);
//		driver = e_driver;

		drivers.set(driver);

		page = new BasePage(drivers.get());

		drivers.get().manage().deleteAllCookies();
		drivers.get().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		drivers.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		drivers.get().manage().window().maximize();
		drivers.get().get(prop.getProperty("url"));
	}

	@AfterMethod
	public synchronized void tearDown() {
		driver.quit();
	}

}
