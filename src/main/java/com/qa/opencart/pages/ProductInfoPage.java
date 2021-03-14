package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	private AccountsPage accountsPage;
	
	
	private By productNameHeader=By.cssSelector("#content h1");
	private By productMetaData=By.cssSelector("#content .list-unstyled:nth-of-type(1) li");
	private By productPrice=By.cssSelector("#content .list-unstyled:nth-of-type(2) li");
	private By quantity=By.id("input-quantity");
	private By addToCart=By.id("button-cart");
	private By productImages=By.cssSelector(".thumbnails img");
	
	
	public ProductInfoPage(WebDriver driver)
	{
		this.driver=driver;
		this.elementUtil=new ElementUtil(driver);
	}
	
	public Map<String, String> getProductInformation()
	{
		Map<String,String> productInfoMap = new HashMap<String,String>();
		productInfoMap.put("Name", elementUtil.doGetText(productNameHeader).trim());
		
		List<WebElement> productList=elementUtil.getElements(productMetaData);
		for(WebElement e:productList)
		{
			String meta[]=e.getText().split(":");
			String metaKey=meta[0].trim() ;
			String metaValue=meta[1].trim();
			
//			String metaKey=e.getText().split(":")[0].trim();  
//			String metaValue=e.getText().split(":")[1].trim();
			
			productInfoMap.put(metaKey,metaValue);
			
		}
		
		List<WebElement> productPriceList=elementUtil.getElements(productPrice);
		productInfoMap.put("Price", productPriceList.get(0).getText().trim());
		
		productInfoMap.put(productPriceList.get(1).getText().split(":")[0].trim(), productPriceList.get(1).getText().split(":")[1].trim());

	
		return productInfoMap;
	}
	
	public void selectQuantity(String qty)
	{
		elementUtil.doSendKeys(quantity, qty);
	}
	
	public void addToCart()
	{
		elementUtil.doClick(addToCart);
	}

	public int getProductImages()
	{
		int imageCount=elementUtil.getElements(productImages).size();
		System.out.println("Total product images are : "+imageCount);
		return imageCount;
	}
	
	public String getProductInfoPageTitle(String productName)
	{
		String title=elementUtil.waitforPageTitle(productName, 10);
		System.out.println("Title is : "+title);
		return title;
	}
	
}
