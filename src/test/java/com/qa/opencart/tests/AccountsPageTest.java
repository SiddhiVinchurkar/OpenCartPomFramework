package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ErrorMessageClass;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass()
	public void accountsPageSetup()
	{
		accountsPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}	
	
	@Test(priority=1)
	@Description("Verify Accounts Page Title Test")
	@Severity(SeverityLevel.MINOR)
	public void accountsPageTitleTest()
	{
		String title=accountsPage.getAccountsPageTitle();
		Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE,ErrorMessageClass.TITLE_NOT_MATCHED);
	}
	
	@Test(priority=2)
	@Description("Verify Accounts Page Header Test")
	@Severity(SeverityLevel.NORMAL)
	public void verifyAccountsPageHeaderTest()
	{
		String header=accountsPage.getHeaderValue();
		Assert.assertEquals(header, Constants.ACCOUNTS_PAGE_HEADER,ErrorMessageClass.HEADER_NOT_MATCHED);
	}
	
	@Test(priority=3)
	@Description("Verify Accounts Page Section Count Test")
	@Severity(SeverityLevel.NORMAL)
	public void verifyAccPageSectionCount()
	{
	   Assert.assertTrue(accountsPage.getAccountSectionsCount() == Constants.ACCOUNTS_PAGE_SECTION_COUNT);
	}
	
	@Test(priority=4)
	@Description("Verify Accounts Page Section List Test")
	@Severity(SeverityLevel.NORMAL)
	public void verifyAccPageSectionListTest()
	{
		List<String> accSectionlist=accountsPage.getAccountsSectionsList();
		Assert.assertEquals(accSectionlist, Constants.getAccountsSectionList());
		
	}
	
/*	
	@Test(priority=5)
	public void searchTest_iMac()
	{
		Assert.assertTrue(accountsPage.dosearch("iMac"));
	}
	
	@Test(priority=6)
	public void searchTest_MacBook()
	{
		Assert.assertTrue(accountsPage.dosearch("MacBook Air"));
	}
*/
	
	@DataProvider
	public Object[][] searchData()
	{
		return new Object[][] {
								{"iMac"},{"MacBook Air"},{"MacBook"}
							  };
	}
	
	@Test(priority=6,dataProvider="searchData")
	@Description("Searching for given Test Data ")
	@Severity(SeverityLevel.CRITICAL)
	public void searchTest(String productName) 
	{
		Assert.assertTrue(accountsPage.dosearch(productName));
	}
	
	
	@Test(priority=7)
	@Description("Verify Product Results Test")
	@Severity(SeverityLevel.CRITICAL)
	public void verfiyProductResultsTest()
	{
		accountsPage.dosearch("iMac");
		productInfoPage=accountsPage.selectProductFromResults("iMac");
		String title=productInfoPage.getProductInfoPageTitle("iMac");
		Assert.assertEquals(title,"iMac");
	}
	
	
	
	
	
}

