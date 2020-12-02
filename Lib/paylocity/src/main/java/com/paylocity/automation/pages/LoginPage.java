package com.paylocity.automation.pages;

import org.openqa.selenium.By;

import com.google.common.base.CharMatcher;
import com.google.common.io.BaseEncoding;
import com.paylocity.automation.utils.Constants;
import com.paylocity.automation.utils.Processer;

public class LoginPage extends Processer {
	private String loginId = "TestUser39";
	static BenefitsDashboardPage benefitsDashboardPage = new BenefitsDashboardPage();
	static boolean login = true;

	public String getLoginId() {
		return loginId;
	}

	public String getPwd() {
		return new String(BaseEncoding.base64().decode(CharMatcher.WHITESPACE.removeFrom("Q0tMKTR9eWxdVmgk")));
	}

	public By tbx_UserName = By.id("Username");
	public By tbx_PassWord = By.name("Password");
	public By btn_Login = By.xpath("//button[.='Log In']");

	public BenefitsDashboardPage login() {
		if (login) {
			navigateToURL(Constants.BASE_URL);
			typeText(tbx_UserName, getLoginId(), "Username");
			typeText(tbx_PassWord, getPwd(), "Password");
			click(btn_Login, "Log In");
			login=false;
		} else {
			reportInfo("Employer Logged In Already");
		}
		return benefitsDashboardPage;

	}

}
