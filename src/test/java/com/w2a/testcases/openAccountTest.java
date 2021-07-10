package com.w2a.testcases;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utlitites.ExcelReader;
import com.w2a.utlitites.testUtil;
public class openAccountTest extends TestBase {

	@Test(dataProviderClass = testUtil.class , dataProvider="dp")
	public void openAccTest(Hashtable<String, String> data)
			throws InterruptedException {
		click("openaccount_CSS");
		select("customername_CSS",data.get("customer"));
		select("currency_CSS",data.get("currency"));
		click("processbtn_CSS");
		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		//Assert.assertTrue(alert.getText().contains(alertText));
		alert.accept();
	}
 
	
}
