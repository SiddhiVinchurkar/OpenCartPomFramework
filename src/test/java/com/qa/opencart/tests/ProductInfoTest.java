package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class ProductInfoTest extends BaseTest {
	
	@BeforeClass
	public void ProductsPageSetup()
	{
		accountsPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	//method of this class, not a test case
	public void goToProductInfoPage(String productName)
	{
		accountsPage.dosearch(productName);
		productInfoPage=accountsPage.selectProductFromResults(productName);
	}
	
	@Test
	@Description("Verify Product Info Page Title Test For iMac")
	@Severity(SeverityLevel.MINOR)
	public void productInfoPageTitleTest_iMac()
	{
		accountsPage.dosearch("iMac");
		productInfoPage=accountsPage.selectProductFromResults("iMac");
		String title=productInfoPage.getProductInfoPageTitle("iMac");
		Assert.assertEquals("iMac", title);
	}
	
	@Test
	@Description("Verify Product Info Page Title Test for MacBookAir")
	@Severity(SeverityLevel.MINOR)
	public void productInfoPageTitleTest_MacBookAir()
	{
		accountsPage.dosearch("MacBook Air");
		productInfoPage=accountsPage.selectProductFromResults("MacBook Air");
		String title=productInfoPage.getProductInfoPageTitle("MacBook Air");
		Assert.assertEquals("MacBook Air", title);
	}

	@Test
	@Description("Verify Product Image Test")
	@Severity(SeverityLevel.NORMAL)
	public void verifyProductImageTest()
	{
		String productName="iMac";
		accountsPage.dosearch("iMac");
		productInfoPage=accountsPage.selectProductFromResults("iMac");
		Assert.assertTrue(productInfoPage.getProductImages() == 3);
	}
	
	@Test
	@Description("Verify Product Info Test")
	@Severity(SeverityLevel.CRITICAL)
	public void verifyProductInfoTest()
	{
		String productName="iMac";
		goToProductInfoPage(productName);
		Map<String,String> productInfoMap=productInfoPage.getProductInformation();
		
		productInfoMap.forEach((k,v) -> System.out.println(k+" : "+v));
		
//		 Assert.assertEquals(productInfoMap.get("Name"), productName);   //if this assertion is failed all the following will fail as well
//		 Assert.assertEquals(productInfoMap.get("Brand"), "Apple");
//		 Assert.assertEquals(productInfoMap.get("Price"), "$100.00");
//		 Assert.assertEquals(productInfoMap.get("Product Code"), "Product 14");
		
		/*
		 * Soft Assertions are the type of assertions that do not throw an exception when an assertion fails and 
		 * would continue with the next step after assert statement
		 */
		 
		 SoftAssert softAssert=new SoftAssert();
		 softAssert.assertEquals(productInfoMap.get("Name"), productName);   
		 softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		 softAssert.assertEquals(productInfoMap.get("Price"), "$100.00");
		 softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 14");
		 
		 softAssert.assertAll();  //AssertAll : if at least one fails you get a detailed result of all that went wrong (and right for that matter).
		 
		
	}
}
