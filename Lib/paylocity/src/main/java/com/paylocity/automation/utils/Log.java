package com.paylocity.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
	// Initialize Log4j logs
	public static Logger Log = LogManager.getLogger();

	
	// This is to print log for the beginning of the test case, as we usually run so
	// many test cases as a test suite
	public static void startTestCase(String sTestCaseName) throws ClassNotFoundException {
		//freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);

		// Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$            " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("****************************************************************************************");
		// Log.info("****************************************************************************************");
	}

	public static void FailedTestCase(String sTestCaseName) throws ClassNotFoundException {
		//freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
		// Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$            " + sTestCaseName + " : Step Failed      $$$$$$$$$$$$$$");
		Log.info("****************************************************************************************");
		// Log.info("****************************************************************************************");
	}

	// This is to print log for the ending of the test case
	public static void endTestCase(String sTestCaseName) {
		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("XXXXXXXXXXXXXXXXXXXXX          " + "-E---N---D-" + "     XXXXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		// Log.info("X");
		// Log.info("X");
	}

	// Need to create these methods, so that they can be called
	public static void info(String message) {
		Log.info("..." + message);
		//System.out.println("..." + message);
		try {
			// Driver.logger.log(LogStatus.INFO, message);
		} catch (Exception e) {
		}
		// Log.info("..."+message);
	}

	public static void warn(String message) {
		Log.warn(message);
	}

	public static void error(String message) {
		try {
			// Driver.logger.log(LogStatus.INFO, message);
		} catch (Exception e) {
		}
		Log.error(message);
	}

	public static void fatal(String message) {
		Log.fatal(message);
	}

	public static void debug(String message) {
		Log.debug(message);
	}
}
