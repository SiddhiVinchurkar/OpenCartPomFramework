package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class LoginPageTest extends BaseTest {
	
	@Test(priority=1)
	@Description("Login Page Title Test") 
	@Severity(SeverityLevel.MINOR)     
	public void loginPageTitleTest()
	{
		String title=loginPage.getLoginPageTitle();
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}

	
	@Test(priority=2)
	@Description("Verify Forgot Pwd Link")
	@Severity(SeverityLevel.CRITICAL)
	public void forgotPwdLinkTest()
	{	
		Assert.assertTrue(loginPage.isForgotPwdLinkExists());
	}
	
	@Test(priority=3)
	@Description("Checking the Login Functionality")
	@Severity(SeverityLevel.BLOCKER)
	public void LoginTest()
	{
		accountsPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		String title=accountsPage.getAccountsPageTitle();
		Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE);
	}
	
}
