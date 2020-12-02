package com.paylocity.automation.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestException;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Processer {

	public static WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public Select select;
	private static int timeout = 10;

	public Processer() {
		driver = Browsers.getDriver();
	}

	public void navigateToURL(String URL) {
		try {
			driver.navigate().to(URL);
		} catch (Exception e) {
			System.out.println("FAILURE: URL did not load: " + URL);
			throw new TestException("URL did not load");
		}
	}

	public String getPageTitle() {
		try {
			return driver.getTitle();
		} catch (Exception e) {
			throw new TestException(String.format("Current page title is: %s", driver.getTitle()));
		}
	}

	public String getCurrentURL() {
		try {
			return driver.getCurrentUrl();
		} catch (Exception e) {
			throw new TestException(String.format("Current URL is: %s", driver.getCurrentUrl()));
		}
	}

	public WebElement getElement(By selector) {
		try {
			return driver.findElement(selector);
		} catch (Exception e) {
			System.out.println(String.format("Element %s does not exist - proceeding", selector));
		}
		return null;
	}

	public String getElementText(By selector) {
		waitUntilElementIsDisplayedOnScreen(selector);
		try {
			return StringUtils.trim(driver.findElement(selector).getText());
		} catch (Exception e) {
			System.out.println(String.format("Element %s does not exist - proceeding", selector));
		}
		return null;
	}

	public List<WebElement> getElements(By Selector) {
		waitForElementToBeVisible(Selector);
		try {
			return driver.findElements(Selector);
		} catch (Exception e) {
			throw new NoSuchElementException(
					String.format("The following element did not display: [%s] ", Selector.toString()));
		}
	}

	public List<String> getListOfElementTexts(By selector) {
		List<String> elementList = new ArrayList<String>();
		List<WebElement> elements = getElements(selector);

		for (WebElement element : elements) {
			if (element == null) {
				throw new TestException("Some elements in the list do not exist");
			}
			if (element.isDisplayed()) {
				elementList.add(element.getText().trim());
			}
		}
		return elementList;
	}

	public void click(By selector, String elementName) {
		WebElement element = getElement(selector);
		waitForElementToBeClickable(selector);
		try {
			element.click();
			ExtentCucumberAdapter.addTestStepLog("Clicked on : " + elementName);
			Log.info("Clicked on : " + elementName);
		} catch (Exception e) {
			throw new TestException(String.format("The following element is not clickable: [%s]", selector));
		}
	}

	public void scrollToThenClick(By selector) {
		WebElement element = driver.findElement(selector);
		actions = new Actions(driver);
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			actions.moveToElement(element).perform();
			actions.click(element).perform();
		} catch (Exception e) {
			throw new TestException(String.format("The following element is not clickable: [%s]", element.toString()));
		}
	}

	public void typeText(By selector, String value, String elementName) {
		WebElement element = getElement(selector);
		clearField(element);
		try {
			if (element.isEnabled()) {
				ExtentCucumberAdapter.addTestStepLog(elementName + " is editable");
				Log.info(elementName + " is editable");
				element.sendKeys(value);
			}
			ExtentCucumberAdapter.addTestStepLog("Entered value in : " + elementName);
			Log.info("Entered value in : " + elementName);
		} catch (Exception e) {
			throw new TestException(
					String.format("Error in sending [%s] to the following element: [%s]", value, selector.toString()));
		}
	}

	public void clearField(WebElement element) {
		try {
			element.clear();
			waitForElementTextToBeEmpty(element);
		} catch (Exception e) {
			System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
		}
	}

	public void waitForElementToDisplay(By Selector) {
		WebElement element = getElement(Selector);
		while (!element.isDisplayed()) {
			System.out.println("Waiting for element to display: " + Selector);
			sleep(200);
		}
	}

	public void waitForElementTextToBeEmpty(WebElement element) {
		String text;
		try {
			text = element.getText();
			int maxRetries = 10;
			int retry = 0;
			while ((text.length() >= 1) || (retry < maxRetries)) {
				retry++;
				text = element.getText();
			}
		} catch (Exception e) {
			System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
		}

	}

	public void waitForElementTextToBe(By selector, String expectedText) {
		try {
			wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.textToBe(selector, expectedText));
		} catch (Exception e) {
			throw new NoSuchElementException(String.format("The following element was not visible: %s", selector));
		}
	}

	public void waitForElementToBeVisible(By selector) {
		try {
			wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(selector));
		} catch (Exception e) {
			throw new NoSuchElementException(String.format("The following element was not visible: %s", selector));
		}
	}

	public void waitUntilElementIsDisplayedOnScreen(By selector) {
		try {
			wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
		} catch (Exception e) {
			throw new NoSuchElementException(String.format("The following element was not visible: %s ", selector));
		}
	}

	public void waitForElementToBeClickable(By selector) {
		try {
			wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(selector));
		} catch (Exception e) {
			throw new TestException("The following element is not clickable: " + selector);
		}
	}

	public void sleep(final long millis) {
		System.out.println((String.format("sleeping %d ms", millis)));
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void mediumWait() {
		System.out.println((String.format("sleeping %d ms", 2)));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void assertUpdateReport(String actual, String expacted, String step)
			throws ClassNotFoundException, IOException {
		try {
			Assert.assertEquals(actual, expacted, "Failed to : " + step);
			ExtentCucumberAdapter.addTestStepLog(
					step + " " + "<font color=green style=font-weight:bold;>" + "Actual : " + "</font>" + "<font color="
							+ "blue>" + actual + "</font>" + "<font color=green style=font-weight:bold;>"
							+ "  Expected : " + "</font>" + "<font color=" + "blue>" + expacted + "</font>");

		} catch (AssertionError e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			ExtentCucumberAdapter.addTestStepLog(step + " : is Failed");
			ExtentCucumberAdapter.addTestStepLog(" " + errors);
			ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(takeScreenshotAtEndOfTest(driver, step));
			throw e;
		}

	}

	public void assertUpdateReport(int actual, int expacted, String step) throws ClassNotFoundException, IOException {
		try {
			Assert.assertEquals(actual, expacted, "Failed to : " + step);
			ExtentCucumberAdapter.addTestStepLog(
					step + " " + "<font color=green style=font-weight:bold;>" + "Actual : " + "</font>" + "<font color="
							+ "blue>" + actual + "</font>" + "<font color=green style=font-weight:bold;>"
							+ "  Expected : " + "</font>" + "<font color=" + "blue>" + expacted + "</font>");

		} catch (AssertionError e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			ExtentCucumberAdapter.addTestStepLog(step + " : is Failed");
			ExtentCucumberAdapter.addTestStepLog(" " + errors);
			ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(takeScreenshotAtEndOfTest(driver, step));
			throw e;
		}

	}

	public void reportInfo(String info) {
		ExtentCucumberAdapter.addTestStepLog("<font color=" + "blue>" + "Info Captured : " + "</font>" + info);
	}

	public void assertUpdateReport(boolean flg, String step) throws ClassNotFoundException, IOException {
		try {
			Assert.assertTrue(flg, "Failed to : " + step);
			ExtentCucumberAdapter.addTestStepLog("<font color=" + "green>" + "Pass : " + "</font>" + step);

		} catch (AssertionError e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			ExtentCucumberAdapter.addTestStepLog(step + " : is Failed");
			ExtentCucumberAdapter.addTestStepLog(" " + errors);
			ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(takeScreenshotAtEndOfTest(driver, step));
			throw e;
		}

	}

	public void highlightElement(WebDriver driver, By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='6px groove yellow'",
				driver.findElement(locator));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", driver.findElement(locator));
	}

	public static String takeScreenshotAtEndOfTest(WebDriver driver, String screenshotName)
			throws IOException, ClassNotFoundException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/target/screenshots/" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
}
