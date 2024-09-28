package com.SwagLabs.Utilities;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.SwagLabs.TestCases.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting extends TestListenerAdapter
{

	public  ExtentSparkReporter sparkReporter;
	public  ExtentReports extent;
	public  ExtentTest logger;
	public void onStart(ITestContext testContext) 
	{
	   String timestamp=new SimpleDateFormat("yyyy.mm.dd.HH.mm.ss").format(new Date());
	   String repName="Test_Report_"+timestamp+".html";
	   sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/"+repName);
	   sparkReporter.config().setDocumentTitle("SwagLabs Test Project"); // Title of the report
       sparkReporter.config().setReportName("Functional Test Report"); // Name of the report
       sparkReporter.config().setTheme(Theme.STANDARD);
       
       extent = new ExtentReports();
       extent.attachReporter(sparkReporter);
       extent.setSystemInfo("Host Name", "localhost");
       extent.setSystemInfo("Environment", "QA");
       extent.setSystemInfo("User", "Srikanth");
       
       // Ensure ScreenshotReports directory exists
       File screenshotDir = new File(System.getProperty("user.dir") + "/Screenshots/");
       if(!screenshotDir.exists())
       {
           screenshotDir.mkdir();
       }
   }

    public void onTestSuccess(ITestResult tr) 
    {
       logger = extent.createTest(tr.getName()); // Create a new entry in the report
       logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN)); // Mark the test as passed
   }

   public void onTestFailure(ITestResult tr) {
       logger = extent.createTest(tr.getName()); // Create a new entry in the report
       logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED)); // Mark the test as failed
       
       // Capture screenshot
       try
       {
           BaseClass baseClass = new BaseClass();
           baseClass.captureScreenshot(Reporting.getDriver(), tr.getName()); // Ensure driver is properly passed
           String screenshotpath = System.getProperty("user.dir") + "/Screenshots/" + tr.getName() + ".png";
           File f = new File(screenshotpath);
           if (f.exists())
           {
               logger.fail("Screenshot is below:" + logger.addScreenCaptureFromPath(screenshotpath));
           }
       }
       catch (IOException e) 
       {
           e.printStackTrace();
           logger.fail("Failed to capture screenshot: " + e.getMessage());
       }
   }

   public void onTestSkipped(ITestResult tr) 
   {
       logger = extent.createTest(tr.getName()); // Create a new entry in the report
       logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE)); // Mark the test as skipped
   }

   public void onFinish(ITestContext testContext) {
	   
       extent.flush(); // Write everything to the report
   }
   
   // Make sure to provide a method to get the WebDriver instance
   public static WebDriver getDriver()
   {
       return BaseClass.driver; // Access the driver instance from BaseClass
   }
}
