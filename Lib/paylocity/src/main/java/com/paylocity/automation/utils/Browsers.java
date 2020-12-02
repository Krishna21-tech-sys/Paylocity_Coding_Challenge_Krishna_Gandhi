package com.paylocity.automation.utils;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.cucumber.testng.AbstractTestNGCucumberTests;

public class Browsers extends AbstractTestNGCucumberTests {

	private static WebDriver driver;
	EventFiringWebDriver eventDriver;

	public static WebDriver getDriver() {
		return driver;
	}

	@BeforeSuite(alwaysRun = true)
	public void setUp() {

		String reportsPath = "test-output/extent/Report.html";
		System.setProperty(Constants.EXTENT_KEY, Constants.EXTENT_PATH);
		System.setProperty(Constants.HTML_START_KEY, Constants.HTML_START_VALUE);
		System.setProperty(Constants.HTML_CONFIG_KEY, Constants.HTML_CONFIG_PATH);
		System.setProperty(Constants.HTML_OUT_KEY, reportsPath);
		System.setProperty(Constants.LOG4J_KEY, Constants.LOG4J_PATH);


		System.setProperty(Constants.CHROME_KEY, Constants.CHROME_PATH);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		driver = new ChromeDriver(options);
		eventDriver = new EventFiringWebDriver(driver);
		EventManager manager = new EventManager();
		eventDriver.register(manager);
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}
}
