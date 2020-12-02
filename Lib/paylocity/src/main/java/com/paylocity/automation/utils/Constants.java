package com.paylocity.automation.utils;

public interface Constants {

	static final String BASE_URL = "https://wmxrwq14uc.execute-api.us-east-1.amazonaws.com/Prod/Account/LogIn";
	static final String CHROME_KEY = "webdriver.chrome.driver";
	static final String CHROME_PATH = "./src/main/resources/exe/chromedriver.exe";
	static final String LANDINGPAGE_TITLE = "Employees - Paylocity Benefits Dashboard";
	static final String PROP_PATH = "./src/main/resources/BufferStorage.properties";
	static final String HTML_CONFIG_PATH = "./src/main/resources/Extent-Config.xml";
	static final String HTML_CONFIG_KEY = "extent.reporter.html.config";
	static final String HTML_START_KEY = "extent.reporter.html.start";
	static final String HTML_START_VALUE = "true";
	static final String HTML_OUT_KEY = "extent.reporter.html.out";
	static final String EXTENT_KEY = "extent.properties";
	static final String EXTENT_PATH = "./src/main/resources/extent.properties";
	static final double GROSS_PAY = 2000;
	static final int PAYCHECKS = 26;
	static final double EMP_COST_OF_BENEFITS = 1000;
	static final double DEPENDANT_COST_OF_BENEFITS = 500;
	static final String LOG4J_KEY="log4j.configurationFile";
	static final String LOG4J_PATH="./src/main/resources/log4j.xml";

}
