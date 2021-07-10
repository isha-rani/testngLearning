package com.w2a.utlitites;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.DataProvider;

import com.w2a.base.TestBase; 

public class testUtil extends TestBase{
//	((TakesScreenshot)driver).getScreenshotAs(Out);
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() {
		File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(srcFile, destFile);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
//		screenshotPath=System.getProperty("user.dir")+"\\target\\surefire-reports\\html"+screenshotName;
		screenshotPath=System.getProperty("user.dir")+"\\test-output\\html\\"+screenshotName;
		try {
			FileHandler.copy(screenshotFile, new File(screenshotPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	
	
	
	@DataProvider(name="dp")
	public static Object[][] getData(Method m) {

		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		Hashtable<String, String> table=null;
//		Object[][] data = new Object[rows - 1][cols];
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<String, String>();
			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0]=table;
				//				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;

	}
	

	public static boolean isTestRunnable(String testname, ExcelReader excel) {
		String sheetName="test_suite";
		int rows=excel.getRowCount(sheetName);
		for (int rNum=2;rNum<=rows;rNum++) {
			String testCase=excel.getCellData(sheetName, "TCID", rNum);
			if(testCase.equalsIgnoreCase(testname)) {
				String runmode=excel.getCellData(sheetName, "Runmode", rNum);
				if(runmode.equalsIgnoreCase("Y")) {
					return true;
				}
				else {
					return false;
				}
			}
			
		}
		return false;
		
	}
}
