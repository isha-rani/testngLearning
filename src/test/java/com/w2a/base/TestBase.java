package com.w2a.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Test;
import com.w2a.utlitites.ExcelReader;
import com.w2a.utlitites.testUtil;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestBase {
	public static WebDriver driver; 
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
//	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static Logger log = Logger.getLogger(TestBase.class);
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public static ExtentTest test;
	
	@SuppressWarnings("deprecation") 
	@BeforeSuite
	public void setUp() throws IOException {

		PropertyConfigurator.configure(".\\src\\test\\resources\\properties\\log4j.properties");
		log.info("Logs started");
		if (driver == null) {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
			config.load(fis);

			log.debug("Config file loaded!!");
			
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			OR.load(fis);
			log.debug("OR file loaded!!");
			
			if (config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executable\\gecko.exe"); // if firefox
																											// verison
																											// is
																											// greater
																											// than 3
				driver = new FirefoxDriver();
			} else if (config.getProperty("browser").equals("chrome")) {
//				System.setProperty("webdriver.chrome.driver",
//						System.getProperty("user.dir") + "\\src\\test\\resources\\executable\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else if (config.getProperty("browser").equals("ie")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executable\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				log.debug("Chrome Launched!!!");
				
			}
			driver.get(config.getProperty("testsiteurl"));
			log.debug("url is"+config.getProperty("testsiteurl"));
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		}

	}

	public void click(String locator) {
		if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_Xpath")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
			}
		else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
//		test.log(Status.INFO, "Clicking on "+locator);
		
	}
	public void type(String locator, String value) {
		if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}
		else if(locator.endsWith("_Xpath")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
//		test.log(Status.INFO, "Typing in "+locator+"entered value as "+value);
}
	
	static WebElement dropdown;
	public void select(String locator,String value) {
		if(locator.endsWith("_CSS")) {
			    dropdown=driver.findElement(By.cssSelector(OR.getProperty(locator)));
			}
			else if(locator.endsWith("_Xpath")) {
			    dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));
			}else if(locator.endsWith("_ID")) {
				dropdown=driver.findElement(By.id(OR.getProperty(locator)));
			}
		
		Select select=new Select(dropdown);
		select.selectByVisibleText(value);
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		}catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public static void verifyEquals(String expected, String actual) {
		try {
			Assert.assertEquals(actual, expected);
		}catch(Throwable t){
			testUtil.captureScreenshot();
			//ReportNG
			Reporter.log("<br>"+"Verification failure:"+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+testUtil.screenshotName+">Screenshot</a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			//extent reports
			//test.log(Status.FAIL, "verification failed with exception "+t.getMessage());
			
		}
	}
	
	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		log.debug("test execution completed");
	}
}
