package com.w2a.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankmgrloginTest extends TestBase{
	@Test
	public void loginAsBankManager() throws InterruptedException {
//		System.setProperty(“org.uncommons.reportng.escape-output”,”false”);
		
		verifyEquals("abc", "xyz");
		Thread.sleep(3000);
		log.debug("log inside login test!!");
//		driver.findElement(By.xpath(OR.getProperty("bankmgrLoginbtn"))).click();
		click("bankmgrLoginbtn_Xpath");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "login not successful");
		Thread.sleep(3000);
		log.debug("login successfully executed!!");
		
//		Assert.fail("Login not successful");
		
	}
}
