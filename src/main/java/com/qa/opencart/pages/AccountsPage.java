package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	WebDriver driver;
	private ElementUtil elementUtil;
	
	private By header =By.cssSelector("div#logo a");
	private By sectionHeader =By.cssSelector("div#content h2");
	private By searchText=By.cssSelector("div#search input[name='search']");
	private By searchButton=By.cssSelector("div#search button[type='button']");
	private By searchResult=By.cssSelector("div.product-layout div.product-thumb ");
	private By resultItem=By.cssSelector("div.product-thumb h4 a");
	

	
	public AccountsPage(WebDriver driver)
	{
		this.driver=driver;
		this.elementUtil=new ElementUtil(driver);
	}
	
	public String getAccountsPageTitle()
	{
		return elementUtil.waitforPageTitle(Constants.ACCOUNTS_PAGE_TITLE, 10);
	}
	
	public String getHeaderValue()
	{
		if(elementUtil.doIsDisplayed(header))
		{
			return elementUtil.doGetText(header);
		}
		return null;
	}
	
	public int getAccountSectionsCount()
	{
		return elementUtil.getElements(sectionHeader).size();
	}
	
	public List<String> getAccountsSectionsList()
	{
		
		List<String> accountsList=new ArrayList<>();
		List<WebElement> AccSectionList=elementUtil.getElements(sectionHeader);
		
		for(WebElement e:AccSectionList)
		{
			String text=e.getText();
			accountsList.add(text);
		}
		
		return accountsList;
	}
	
	
	public boolean dosearch(String productName)
	{
		elementUtil.doSendKeys(searchText, productName);
		elementUtil.doClick(searchButton);
		if(elementUtil.getElements(searchResult).size()>0)
		{
			return true;
		}
		return false;   	
	}
	
	public ProductInfoPage selectProductFromResults(String productName)
	{
		List<WebElement> resultItemsList=elementUtil.getElements(resultItem);
		System.out.println("Total number of items displayed"+resultItemsList.size());
		
		for(WebElement e:resultItemsList)
		{
			if(e.getText().equals(productName))
			{
				e.click();
				break;
			}
		}
		
		return new ProductInfoPage(driver);
	}
	
}
