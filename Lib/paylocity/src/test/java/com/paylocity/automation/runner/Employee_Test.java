package com.paylocity.automation.runner;

import com.paylocity.automation.utils.Browsers;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions
(
		features = { "./src/main/java/com/paylocity/automation/features/Employee.feature" },
		glue = {"com.paylocity.automation.steps" },
		tags = "@test",
		monochrome = true,
		dryRun = false,
		plugin = { "pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }

)

public class Employee_Test extends Browsers{

}
