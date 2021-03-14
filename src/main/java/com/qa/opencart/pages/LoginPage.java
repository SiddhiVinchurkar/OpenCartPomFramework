package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private By username=By.id("input-email");
	private By password=By.id("input-password");
	private By login=By.cssSelector("input[value='Login']");
	private By ForgotPwdLink=By.cssSelector("div.form-group a"); 
	private By RegisterLink=By.linkText("Register");
	
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		elementUtil=new ElementUtil(driver);
	}
	
	@Step("Get Login Page Title") 
	public String getLoginPageTitle()
	{
		return elementUtil.waitforPageTitle(Constants.LOGIN_PAGE_TITLE, 10);	
	}
	
	@Step("Verify Forgot Pwd Link Exist")
	public boolean isForgotPwdLinkExists()
	{
		return elementUtil.doIsDisplayed(ForgotPwdLink);
	}
	
	@Step("Login with username: {0} and password : {1}") 
	public AccountsPage doLogin(String usrnm,String pwd)
	{
		System.out.println("Login with : "+usrnm+" and password "+pwd);
		
		elementUtil.doSendKeys(username, usrnm);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(login);
			
		return new AccountsPage(driver);
	}
	
	public RegisterPage navigateToRegisterPage()
	{
		System.out.println("Navigate to register page");
		elementUtil.doClick(RegisterLink);
		return new RegisterPage(driver);
	}
	
}
