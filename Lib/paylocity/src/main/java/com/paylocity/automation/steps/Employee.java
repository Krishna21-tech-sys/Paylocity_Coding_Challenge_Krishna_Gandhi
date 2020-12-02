package com.paylocity.automation.steps;

import java.io.IOException;

import com.paylocity.automation.pages.BenefitsDashboardBuilder;
import com.paylocity.automation.pages.BenefitsDashboardPage;
import com.paylocity.automation.pages.LoginPage;
import com.paylocity.automation.utils.Buffer;
import com.paylocity.automation.utils.Log;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Employee {
	LoginPage loginPage = new LoginPage();
	BenefitsDashboardPage benefitsDashboardPage;
	int dependants;
	BenefitsDashboardBuilder bdb = new BenefitsDashboardBuilder();

	// Common
	@Given("an Employer")
	public void an_employer() throws ClassNotFoundException {
		Log.startTestCase("Login");
		benefitsDashboardPage = loginPage.login();
		Log.endTestCase("Login");

	}

	@Given("I am on the Benefits Dashboard page")
	public void i_am_on_the_benefits_dashboard_page() throws ClassNotFoundException, IOException {
		Log.startTestCase("Verify Benefits Dashboard");
		benefitsDashboardPage.verifyDasboardPage();
		Log.endTestCase("Verify Benefits Dashboard");
	}

	// Add Employee

	@When("I select Add Employee")
	public void i_select_add_employee() throws ClassNotFoundException {
		Log.startTestCase("Add Employee");
		benefitsDashboardPage.selectAddEmployee();
		Log.endTestCase("Add Employee");
	}

	@Then("I should be able to enter employee details {string},{string}")
	public void i_should_be_able_to_enter_employee_details(String name, String dependents)
			throws IOException, ClassNotFoundException {
		Log.startTestCase("Enter Employee Details");
		this.dependants = Integer.valueOf(dependents);

		Buffer.writeToBuffer("EmpName", name);
		bdb.setFirstName(name.split(" ")[0]);
		bdb.setLastname(name.split(" ")[1]);
		bdb.setDependents(dependents);
		benefitsDashboardPage.enterEmployeeDeatils(bdb);
		Log.endTestCase("Enter Employee Details");
	}

	@Then("the employee should save")
	public void the_employee_should_save() throws ClassNotFoundException {
		Log.startTestCase("Save Record");
		benefitsDashboardPage.save();
		Log.endTestCase("Save Record");
	}

	@Then("I should see the employee in the table")
	public void i_should_see_the_employee_in_the_table() throws ClassNotFoundException, IOException {
		Log.startTestCase("Verify New Record");
		benefitsDashboardPage.verifyNewEmployeeRecord(Buffer.readFromBuffer("EmpName"));
		Log.endTestCase("Verify New Record");
	}

	@Then("the benefit cost calculations are correct")
	public void the_benefit_cost_calculations_are_correct() throws ClassNotFoundException, IOException {
		Log.startTestCase("Verify Benefit Cost Calculations");
		benefitsDashboardPage.verifySalary(Buffer.readFromBuffer("EmpName"), "Verify Salary after add employee");
		benefitsDashboardPage.verifyGrossPay(Buffer.readFromBuffer("EmpName"), "Verify Gross Pay after add employee");
		benefitsDashboardPage.verifyBenefitsCost(Buffer.readFromBuffer("EmpName"), dependants,
				"Verify Benefits Cost after add employee");
		benefitsDashboardPage.verifyNetPay(Buffer.readFromBuffer("EmpName"), "Verify Net Pay after add employee");
		Log.endTestCase("Verify Benefit Cost Calculations");
	}

	// Edit Employee

	@When("I select the Action Edit")
	public void i_select_the_action_edit() throws InterruptedException, IOException, ClassNotFoundException {
		Log.startTestCase("Edit Employee");
		benefitsDashboardPage.selectEditEmployee(Buffer.readFromBuffer("EmpName"));
		Log.endTestCase("Edit Employee");
	}

	@Then("I can edit employee details {string}")
	public void i_can_edit_employee_details(String dependents) throws IOException, ClassNotFoundException {
		Log.startTestCase("Edit Employee Details");
		this.dependants = Integer.valueOf(dependents);
		bdb.setDependents(dependents);
		benefitsDashboardPage.enterEmployeeDeatils(bdb);
		Log.endTestCase("Edit Employee Details");
	}

	@Then("the data should change in the table")
	public void the_data_should_change_in_the_table() throws ClassNotFoundException, IOException {
		Log.startTestCase("Verify Benefit Cost Calculations After Edit");
		benefitsDashboardPage.update();
		benefitsDashboardPage.verifySalary(Buffer.readFromBuffer("EmpName"), "Verify Salary after update employee");
		benefitsDashboardPage.verifyGrossPay(Buffer.readFromBuffer("EmpName"),
				"Verify Gross Pay after update employee");
		benefitsDashboardPage.verifyBenefitsCost(Buffer.readFromBuffer("EmpName"), dependants,
				"Verify Benefits Cost after update employee");
		benefitsDashboardPage.verifyNetPay(Buffer.readFromBuffer("EmpName"), "Verify Net Pay after update employee");
		Log.endTestCase("Verify Benefit Cost Calculations After Edit");
	}

	// Delete Employee
	@When("I click the Action X")
	public void i_click_the_action_x() throws IOException, ClassNotFoundException {
		Log.startTestCase("Delete Employee");
		benefitsDashboardPage.deleteEmployee(Buffer.readFromBuffer("EmpName"));
		Log.endTestCase("Delete Employee");
	}

	@Then("the employee should be deleted")
	public void the_employee_should_be_deleted() throws ClassNotFoundException, IOException {
		Log.startTestCase("Verify Delete Employee");
		benefitsDashboardPage.verifyEmployeeDeletion(Buffer.readFromBuffer("EmpName"),
				"Verify Employee Record Count After Deletion ");
		Log.endTestCase("Verify Delete Employee");
	}

}
