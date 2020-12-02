package com.paylocity.automation.pages;

import java.io.IOException;
import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.paylocity.automation.utils.Constants;
import com.paylocity.automation.utils.Processer;

public class BenefitsDashboardPage extends Processer {

	private By btn_Add_Employee = By.xpath("//button[.='Add Employee']");
	private By tbx_First_Name = By.name("firstName");
	private By tbx_Last_Name = By.name("lastName");
	private By tbx_Dependant = By.name("dependants");
	private By btn_Add = By.xpath("//button[.='Add']");
	private By btn_Update = By.xpath("//button[.='Update']");
	private By btn_Delete = By.id("deleteEmployee");

	private By headers_DashboardTable = By.xpath("//table[@id='employeesTable']//tr/th");
	private String totalBenefitsCost;
	private static DecimalFormat df2 = new DecimalFormat("#.##");

	private By getRecord(String name) {

		return By.xpath("//table[@id='employeesTable']//tbody/tr[contains(.,'" + name.split(" ")[0]
				+ "') and contains(.,'" + name.split(" ")[1] + "')]");
	}

	private By getEditButton(String name) {

		return By.xpath("//table[@id='employeesTable']//tbody/tr[contains(.,'" + name.split(" ")[0]
				+ "') and contains(.,'" + name.split(" ")[1] + "')]//i[contains(@class,'edit')]");
	}

	private By getDeleteButton(String name) {

		return By.xpath("//table[@id='employeesTable']//tbody/tr[contains(.,'" + name.split(" ")[0]
				+ "') and contains(.,'" + name.split(" ")[1] + "')]//i[contains(@class,'times')]");
	}

	private By getColoum(String name, String order) {

		return By.xpath("//table[@id='employeesTable']//tbody/tr[contains(.,'" + name.split(" ")[0]
				+ "') and contains(.,'" + name.split(" ")[1] + "')]/td[" + order + "]");
	}

	public String getColoumValue(String empName, String coloumName) {
		String val = null;
		for (int i = 0; i < getElements(headers_DashboardTable).size(); i++) {
			if (getElements(headers_DashboardTable).get(i).getText().equals(coloumName)) {
				val = getElementText(getColoum(empName, String.valueOf(i + 1)));
				break;

			}

		}
		return val;
	}

	public By getCell(String empName, String coloumName) {
		By selector = null;
		for (int i = 0; i < getElements(headers_DashboardTable).size(); i++) {
			if (getElements(headers_DashboardTable).get(i).getText().equals(coloumName)) {
				selector = getColoum(empName, String.valueOf(i + 1));
				break;

			}

		}
		return selector;
	}

	public void enterEmployeeDeatils(BenefitsDashboardBuilder bdb) {
		if (bdb.getFirstName() != null && bdb.getLastname() != null) {
			typeText(tbx_First_Name, bdb.getFirstName(), "FirstName");
			typeText(tbx_Last_Name, bdb.getLastname(), "Lastname");
		}
		typeText(tbx_Dependant, bdb.getDependents(), "Dependents");
	}

	public void verifyDasboardPage() throws ClassNotFoundException, IOException {
		assertUpdateReport(getPageTitle(), Constants.LANDINGPAGE_TITLE, "Verify Title");
	};

	public void selectAddEmployee() {
		click(btn_Add_Employee, "Add Employee");
	}

	public void selectEditEmployee(String empName) throws InterruptedException {
		waitForElementToBeClickable(getEditButton(empName));
		click(getEditButton(empName), "Edit Employee");
	}

	public void save() {
		click(btn_Add, "Add");
	}

	public void update() {
		click(btn_Update, "Update");
	}

	public void verifyNewEmployeeRecord(String name) throws ClassNotFoundException, IOException {
		assertUpdateReport(getElements(getRecord(name)).size() == 1, "Verify New Record");
		reportInfo("New Record Added");
	};

	public void verifySalary(String empName, String info) throws ClassNotFoundException, IOException {
		assertUpdateReport(getColoumValue(empName, "Salary"),
				String.valueOf(new DecimalFormat("##.00").format(Constants.GROSS_PAY * Constants.PAYCHECKS)), info);
	}

	public void verifyGrossPay(String empName, String info) throws ClassNotFoundException, IOException {
		assertUpdateReport(getColoumValue(empName, "Gross Pay"),
				String.valueOf(new DecimalFormat("##.00").format(Constants.GROSS_PAY)), info);
	}

	public void verifyBenefitsCost(String empName, int dependants, String info)
			throws ClassNotFoundException, IOException {
		double benefitsCostEmp = Constants.EMP_COST_OF_BENEFITS / Constants.PAYCHECKS;
		double benefitsCostDep = dependants * (Constants.DEPENDANT_COST_OF_BENEFITS / Constants.PAYCHECKS);
		totalBenefitsCost = new DecimalFormat("##.##").format(benefitsCostEmp + benefitsCostDep);

		waitForElementTextToBe(getCell(empName, "Benefits Cost"), totalBenefitsCost);
		assertUpdateReport(getColoumValue(empName, "Benefits Cost"), totalBenefitsCost, info);
	}

	public void verifyNetPay(String empName, String info) throws ClassNotFoundException, IOException {
		assertUpdateReport(getColoumValue(empName, "Net Pay"),
				String.valueOf(Constants.GROSS_PAY - Double.valueOf(totalBenefitsCost)), info);
	}

	public void deleteEmployee(String empName) {
		waitForElementToBeClickable(getDeleteButton(empName));
		click(getDeleteButton(empName), "X");
		waitForElementToBeClickable(btn_Delete);
		click(btn_Delete, "Delete Employee");
	}

	public void verifyEmployeeDeletion(String empName, String info) throws ClassNotFoundException, IOException {
		mediumWait();
		assertUpdateReport(driver.findElements((getCell(empName, "Id"))).size(), 0, info);
		reportInfo("Employee record deleted sucessfully");
	}

}
