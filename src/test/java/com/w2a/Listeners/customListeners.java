package com.w2a.Listeners;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.apache.xmlbeans.impl.tool.XSTCTester.TestCase;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.w2a.base.TestBase;
import com.w2a.utlitites.extentManager;
import com.w2a.utlitites.testUtil;

public class customListeners extends TestBase implements ITestListener {
	static Date d = new Date();
	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	private static ExtentReports extent = extentManager.createInstance(System.getProperty("user.dir")+"\\reports\\"+fileName);
	
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

		ExtentTest test = extent.createTest(result.getTestClass().getName()+"     @TestCase : "+result.getMethod().getMethodName());
        testReport.set(test);
        //runmodes - Y 
        if(!testUtil.isTestRunnable(result.getMethod().getMethodName(), excel)) {
        	throw new SkipException("skipping  test case due to no runmode");
        }

		}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " PASSED"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport.get().pass(m);
		
		}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.setProperty("org.uncommons.reportng.escape-output","false");
		testUtil.captureScreenshot();
//		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+testUtil.screenshotName+">Screenshot</a>");
//		System.getProperty("user.dir")+"\\target\\surefire-reports\\html"+screenshotName
		String excepionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		testReport.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
				+ "</font>" + "</b >" + "</summary>" +excepionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
		
	/*	try {

			ExtentManager.captureScreenshot();
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName)
							.build());
		} catch (IOException e) {

		}*/
		
		String failureLogg="TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		testReport.get().log(Status.FAIL, m);
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"Test Case:- "+ methodName+ " Skipped"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testReport.get().skip(m);
			}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
			}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
			}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
			}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		if (extent != null) {

			extent.flush();
		}
			}

}
