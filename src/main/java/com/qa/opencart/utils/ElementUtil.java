package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
		
		public ElementUtil(WebDriver driver) // whenever object of ElementUtil is created,we need to pass the webdriver
		{                                    // and the passed webdriver is initiliazed to the private webdriver of this class
			this.driver=driver;
			jsUtil=new JavaScriptUtil(this.driver);
		}
		
		public  WebElement getElement(By locator)
		{
			WebElement element = driver.findElement(locator);
			
			//to highlight the element
			if(DriverFactory.highlight.equals("true")) {
				jsUtil.flash(element);
			}
			
			return element;
		}

		public  void doSendKeys(By locator,String value)
		{
			WebElement element=getElement(locator);
			element.clear();
			element.sendKeys(value);
		}

		public  void doClick(By locator)
		{
			getElement(locator).click();
		}
		
		
		//1.12.2020
		
		public List<WebElement> getElements(By locator)
		{
			return driver.findElements(locator);
		}
		
		// Drop Down With Select Class
		
		public  void doSelectDropDownByVisibleText(By locator,String text)
		{
			Select sc=new Select(getElement(locator));
			sc.selectByVisibleText(text);		
		}

		
		public void doSelectDropDownByIndex(By locator,int index)
		{
			Select sc=new Select(getElement(locator));
			sc.selectByIndex(index);		
		}
		
		public  void doSelectDropDownByValue(By locator,String value)
		{
			Select sc=new Select(getElement(locator));
			sc.selectByValue(value);		
		}
		
		
		// Drop Down Without Select Class
		
		public void dropDownWithoutSelect(By locator,String value)
		{
			List<WebElement> List=getElements(locator);
			
			System.out.println("Total list is  : "+List.size());
			
			for(WebElement e:List)
			{
				String indus=e.getText();
				if(indus.equals(value))
				{
					e.click();
					break;
				}
			}
		}
		
		//2.12.2020
		//select a particular value from google search
		public void selectFromSuggestionList(By suggestionList,String value)
		{
			List<WebElement> resultList=getElements(suggestionList);
			
			System.out.println("Total suggestions are :"+resultList.size());
			
			for(WebElement e: resultList)
			{
				String text=e.getText();
				System.out.println(text);
				
				if(text.equals(value))
				{
					e.click();
					break;
				}
			}
		}
		
		//3.12.2020
		//Generic Drop down select with all in one function
		
		public void DropDownGenericSelect(By locator,String type,String value)
		{
			//applicable for only select tag dropdowns
			
			Select sc=new Select(getElement(locator));
			
			switch(type)
			{
			case "index":
				sc.selectByIndex(Integer.parseInt(value));
				break;
				
			case "value":
				sc.selectByValue(value);
				break;
				
			case "visibletext":
				sc.selectByVisibleText(value);
				break;
				
			default:
				System.out.println("Please pass the correct option...");
				break;
			}
	}
		
		//4.12.2020
		//Action Utils
		
		public List<String> getRightClickMenuList(By RightClickLocator,By Listlocator)
		{
			List<String> MenuList=new ArrayList<>();
			
			 Actions act=new Actions(driver);
			 WebElement RightClickButton=getElement(RightClickLocator);
		     act.contextClick(RightClickButton).perform();
			
			List<WebElement>list=getElements(Listlocator);
			
			System.out.println("Total items are : "+list.size());
			
			for(WebElement e:list)
	    	{
				String value=e.getText();
	    		MenuList.add(value);
	    	}
			
			return MenuList;
		}
		
		public void doRightClick(By RightClickLocator,By Listlocator,String value)
		{
			 Actions act=new Actions(driver);
			 WebElement RightClickButton=getElement(RightClickLocator);
		     act.contextClick(RightClickButton).perform();
			
			List<WebElement>list=getElements(Listlocator);
			
			System.out.println("Total items are : "+list.size());
			
			for(WebElement e:list)
	    	{
				String text=e.getText();
	    		if(text.equals(value))
	    		{
	    			e.click();
	    			break;
	    		}
	    	}
			
		}
		
		//8.12.2020
		
		/* click() method of Actions class clicks in the middle of the WebElement.
		 * This is similar to  Actions.moveToElement(element).click();
		 * With normal sendkeys and click it will directly click on the element
		 */
		
		public void doActionsCick(By locator)
		{
			Actions act=new Actions(driver);
			act.click(getElement(locator)).perform();
		//	act.moveToElement(driver.findElement(locator)).click().build().perform();
			//both are same
			
		}
		
		public void doActionsSendKeys(By locator,String value)
		{	
			Actions act=new Actions(driver);
			act.sendKeys(getElement(locator),value).perform();
		}

		
		public void alertAcceptAndDismiss(By locator,int input)
		{
			Alert al=driver.switchTo().alert();
			
			switch(input)
			{
			case 1:	al.accept();
				break;
			case 2:al.dismiss();
				break;
			}
			
			driver.switchTo().defaultContent(); 
			
		}
		
		//9.12.2020
		//JqueryDropDown with var-args for single/multi/All selection
		
		public void selectFromJqueryDropdown(By locator,String... value)
		{
		

			List<WebElement> list=getElements(locator);
			
			System.out.println("Total size :"+ list.size());
			
			//single and multi selection
			if(!(value[0].equalsIgnoreCase("ALL")))
			{
				for(int i=0;i<list.size();i++)
				{
					String choice=list.get(i).getText();
					System.out.println(choice);
					
					for(int j=0;j<value.length;j++)
					{
						if(choice.equals(value[j]))
						{
							list.get(i).click();
							break;
						}
					}
				}
			}
			else
			{
				//select all choices
				//if try and catch is not written exception is thrown for xpaths which are there in the dom but not visible on the page 
				try
				{
					for(WebElement e:list)
					{
						e.click();
						Thread.sleep(100);
					}	
				}
				catch(Exception e)
				{
					
				}
			}
		}
		
		
		//15.12.2020
		//Explicit wait
		
		//passed title should be a part of title
		public String waitforPageTitle(String title,int timeout)
		{
			WebDriverWait w1=new WebDriverWait(driver,timeout); 
			w1.until(ExpectedConditions.titleContains(title)); //returns boolean
			return driver.getTitle();	
		}
		
		//compare the exact title
		public String waitforExactPageTitle(String title,int timeout)
		{
			WebDriverWait w1=new WebDriverWait(driver,timeout); 
			w1.until(ExpectedConditions.titleIs(title)); //returns boolean
			return driver.getTitle();
		}
		
		//passed url should be a part of title
		public String waitforPageUrl(String url,int timeout)
		{
			WebDriverWait w1=new WebDriverWait(driver,timeout); 
			w1.until(ExpectedConditions.urlContains(url)); //returns boolean
		   // urlToBe(url) and urlMatches(regex) methods are also available 
			return driver.getCurrentUrl();
		}

		
		//17.12.2020
		//Filter streams using functional programming
		public List<String> FilterStream(List<WebElement> list,String text)
		{
			return   list.stream()    					         // stream is created from the list
					.filter(ele->!ele.getText().isEmpty())              // filter out null elements
					.filter(ele->ele.getText().contains(text))      // keep only the texts which contains "Amazon" text from the above list
					.map(ele->ele.getText().trim())                    // map is used to get the text and then trim the whitespaces from the above list
					.collect(Collectors.toList()); 
			
		}
		
		
		//WebDriverWait for Alert
		
		public Alert isAlertPresent(int timeout)
		{
			WebDriverWait wt=new WebDriverWait(driver,timeout);
			return wt.until(ExpectedConditions.alertIsPresent());
		}
		
		public String getAlertText(int timeout)
		{
			return isAlertPresent(timeout).getText();
		}
		
		public void doAcceptAlert(int timeout)
		{
			 isAlertPresent(timeout).accept();
		}

		
		public void doDismissAlert(int timeout)
		{
			 isAlertPresent(timeout).dismiss();
		}
		
		
		//18.12.2020
		//WebDriverWait For WebElement
		
		/* presenceOfElementLocated:
		 * An expectation for checking that an element is present on the DOM of a page.
		 */
		
		public WebElement waitForElementPresent(By locator,int timeout)
		{
			WebDriverWait wt=new WebDriverWait(driver,timeout);
			return wt.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		/* presenceOfElementLocated:
		 * An expectation for checking that an element is present on the DOM of a page. 
		 * This does not necessarily mean that the element is visible.
		 */
		
		//with polling time
		
		public WebElement waitForElementPresent(By locator,int timeout,int pollingTime)
		{
			WebDriverWait wt=new WebDriverWait(driver,timeout,pollingTime);
			return wt.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		/* visibilityOf:
		 * An expectation for checking that an element, known to be present on the DOM of a page, is visible. 
		 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0
		 */
		
		//It should not be hidden	
		public WebElement waitForElementVisible(By locator,int timeout)
		{
			WebDriverWait wt=new WebDriverWait(driver,timeout);
			return wt.until(ExpectedConditions.visibilityOf(getElement(locator)));
			//here visibilityOf takes WebElement as input
		}
		
		/* visibilityOfAllElementsLocatedBy :
		 * An expectation for checking that all elements present on the web page that match the locator are visible. 
		 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0
		 */
		
		public List<WebElement> waitForAllElementsVisible(By locator,int timeout)
		{
			WebDriverWait wt=new WebDriverWait(driver,timeout);
			return wt.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		
		public void getPageElementsText(By locator,int timeout)
		{
			waitForAllElementsVisible(locator,timeout).stream().forEach(ele->System.out.println(ele.getText()));
		}
		
		public int getPageElementCount(By locator,int timeout)
		{
			return waitForAllElementsVisible(locator,timeout).size();
		}
		
		/* elementToBeClickable​:
		 * An expectation for checking an element is visible and enabled such that you can click it.
		 */
		
		public void waitForElementToBeClickableWithActions(By locator,int timeout)
		{
			WebDriverWait wt=new WebDriverWait(driver,10);
		   WebElement element= wt.until(ExpectedConditions.elementToBeClickable(locator));
		    
		    Actions act=new Actions(driver);
		    act.moveToElement(element).click().build().perform();
			
		}
		
		//Element is visible and enabled both
		public void waitForElementToBeClickable(By locator,int timeout)
		{
			WebDriverWait wt=new WebDriverWait(driver,10);
		    WebElement element= wt.until(ExpectedConditions.elementToBeClickable(locator));
	        element.click();
		}
		
		//21.12.2020
		
		//Custom method to provide dynamic wait to find the WebElement
		
		public WebElement retryingElement(By locator)
		{
			WebElement element=null;
			int attempts=0;
			
			//number of attempts should be generic here, if it passed as an paramter, different waits can be appiled while calling this method
			//someone can also pass 100 as an parameter and waiting for this long is wrong.Hence it is declared inside the method
			
			while(attempts<30)   //here 30 represents 30sec
			{
				try
				{
					
				element=driver.findElement(locator);
				break;
				
				}
				catch(NoSuchElementException e)
				{
					try {
						Thread.sleep(500);    // Thread.sleep is added so that after checking if element is present and until we increment the counter there should be some sleep otherwise it would execute very fast as 30 is actually an int and not a unit of time 
					} catch (InterruptedException e1) {
					
					}  
					
				}
				catch(StaleElementReferenceException e2)
				{
					try {
						Thread.sleep(500);  //500ms acts as polling time until next request is send to check the element. Here Thread.sleep ,static wait is acting as an dynamic wait beacause of while loop
					} catch (InterruptedException e3) {
						
					}
				}
				
			System.out.println("Element is not found in "+(attempts+1)+" sec");		
			//If element is not found, it will do 30 attempts and time used would be 15secs (30*0.5=15sec)
			
			attempts++;	
			}
			
			return element;
		
		//Import of NoSuchElementException should be from org.openqa.selenium.NoSuchElementException and not from java.util.NoSuchElementException		
		//Attempts*Polling time=Total Time
		}
		
		
		
		public WebElement WaitForFluentWait(By locator,int timeout,int pollingTime)
		{
			Wait<WebDriver> wt=new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofSeconds(pollingTime)) 
					.ignoring(NoSuchElementException.class)   
					.ignoring(StaleElementReferenceException.class);


			return wt.until(ExpectedConditions.presenceOfElementLocated(locator));

		}

		/* 
		 * If Fluent wait is placed in getElement method then it would be called each time
		 * and would act like a global wait which is not recomended
		 */
		
		
		
		//-------------------------------------
		
		public boolean doIsDisplayed(By locator) {
			return getElement(locator).isDisplayed();
		}
		
		
		public String doGetText(By locator) {
			return getElement(locator).getText();
		}
		
		

	}



