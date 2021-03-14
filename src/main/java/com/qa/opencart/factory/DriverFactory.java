package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;


import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * @author Siddhi Vinchurkar
 */

/*
 * This method is used to initialize the webdriver on the basis of browser name
 */

public class DriverFactory {
	
	private static final Logger LOGGER=Logger.getLogger(String.valueOf(DriverFactory.class));
	
	WebDriver driver;
	Properties prop;
	public static String highlight;
	OptionsManager optionManager;
	
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<>();
	
	public WebDriver init_driver(Properties prop)
	{
		
		String browserName=prop.getProperty("browser");
		
		LOGGER.info("Browser name is : "+browserName);
		
		highlight=prop.getProperty("highlight").trim();
		
		optionManager=new OptionsManager(prop);
		
		if(browserName.equals("chrome"))
		{
			LOGGER.info("Setup chrome browser");
			
			WebDriverManager.chromedriver().setup();
			//driver=new ChromeDriver(optionManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionManager.getChromeOptions()));
			
		}
		else if(browserName.equals("firefox"))
		{
			LOGGER.info("Setup firefox browser");
			
			WebDriverManager.firefoxdriver().setup();
		//	driver=new FirefoxDriver(optionManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionManager.getFirefoxOptions()));
	
		}
		else if(browserName.equals("IE"))
		{
			LOGGER.info("Setup IE browser");
			
			WebDriverManager.iedriver().setup();
//			driver=new InternetExplorerDriver();
			tlDriver.set(new InternetExplorerDriver());
		}
		else if(browserName.equals("safari"))
		{
			LOGGER.info("Setup safari browser");
			
			//driver=new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		else
		{
			System.out.println("Invalid Browser Name");
		}
		
		getDriver().manage().window().fullscreen();
		getDriver().manage().deleteAllCookies();
		
		return getDriver();
		
	}
	
	
	public static synchronized WebDriver getDriver()
	{
		return tlDriver.get(); 
	}
	
	/*
	 * This method is used to initialize the properties from config file
	 * @return : returns Properties reference prop
	 */
	
	
	public Properties init_properties()
	{
		prop=new Properties();
		try {
			FileInputStream fis=new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(fis);
		} 
		catch (FileNotFoundException e) 
		{
			LOGGER.error("File Not Found Exception occured while initialzing properties");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			LOGGER.error("IO Exception occured while initialzing properties");
			e.printStackTrace();
		}
		
		return prop;
	}
	
	
	/*
	 * Takes screenShot
	 */
	public String getScreenshot()
	{
		File src=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png" ;
		File destination=new File(path);
		try 
		{
			FileUtils.copyFile(src, destination);
		} 
		catch (IOException e) 
		{
			LOGGER.error("Exception occured while getting the screenshot");
			e.printStackTrace();
		}
		
		return path;
	}
}
