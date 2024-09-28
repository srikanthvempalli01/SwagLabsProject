package com.SwagLabs.TestCases;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.SwagLabs.Utilities.XLUtils;
import com.SwagLabs.PageObjects.LoginPage;
public class TC_TestCaseLogin_001 extends BaseClass
{
	@Test(dataProvider="loginData")
	public void LoginTest(String username,String password) throws InterruptedException
	{
		driver.get(baseURL);
		LoginPage lp=new LoginPage(driver);
		lp.enterUsername(username);
		logger.info("enter username");
		Thread.sleep(5000);
		lp.enterPassword(password);
		logger.info("enter password");
		Thread.sleep(5000);
		lp.clickLogin();
		Thread.sleep(5000);
		if(driver.getPageSource().contains("Products"))
		{
			Assert.assertTrue(true);
			logger.info("login successfully.....");
			lp.clickOpenMenu();
			lp.clickLogout();
			logger.info("logout successfully");
			
		}
		else
		{
			Assert.assertTrue(false);
			logger.info("login failed");
		}
	}
	@DataProvider(name="loginData")
	public String[][] getData() throws IOException
	{
		
	    String path=System.getProperty("user.dir")+"/src/test/java/com/Swaglabs/testData/LoginData.xlsx";
		int rowCount=XLUtils.getRowCount(path,"Sheet1");
		int colCount=XLUtils.getCellCount(path,"Sheet1",1);
		String loginData[][]=new String[rowCount][colCount];
		for(int i=1;i<=rowCount;i++)
		{
			for(int j=0;j<colCount;j++)
			{
				loginData[i-1][j]=XLUtils.getCellData(path,"Sheet1", i, j);
			}
		}
		return loginData;  
	}
}
