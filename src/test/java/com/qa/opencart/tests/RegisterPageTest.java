package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void registrationPageSetup()
	{
		registerPage=loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegistrationData()
	{
		Object data[][] = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);	
		return data;
	}
	
	
	@Test(dataProvider = "getRegistrationData")
	public void userRegisterationTest(String firstName,String lastName,String email,String phone,String password,String subscribe)
	{
		
		Assert.assertTrue(registerPage.accountRegistration(firstName,lastName,email,phone,password,subscribe));
		
		
	}

}
