package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utlitites.ExcelReader;
import com.w2a.utlitites.testUtil;

public class AddCustomerTest extends TestBase {
	@Test(dataProviderClass = testUtil.class , dataProvider="dp")
	public void addCustomerTest(Hashtable<String, String> data)
			throws InterruptedException {
		if(!data.get("Runmode").equalsIgnoreCase("Y")) {
			throw new SkipException("Skipping test data because runmode no in testdata");
		}
		//		driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
		click("addCustBtn_CSS");

		type("firstname_CSS", data.get("firstName"));
		type("lastname_CSS", data.get("lastName"));
		type("postalcode_CSS", data.get("postalCode"));
//		driver.findElement(By.cssSelector(OR.getProperty("firstname"))).sendKeys(firstName);
//		driver.findElement(By.cssSelector(OR.getProperty("lastname"))).sendKeys(lastName);
//		driver.findElement(By.cssSelector(OR.getProperty("postalcode"))).sendKeys(postalCode);
//		driver.findElement(By.cssSelector(OR.getProperty("addbtn"))).click();
		click("addbtn_CSS");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alertText")));
		alert.accept();
//		Thread.sleep(2000);
	}
 
	
}
