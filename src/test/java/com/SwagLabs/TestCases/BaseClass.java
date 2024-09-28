package com.SwagLabs.TestCases;
import java.io.File;

import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.SwagLabs.Utilities.ReadConfig;
import freemarker.log.Logger;

public class BaseClass {

    ReadConfig readconfig = new ReadConfig();
    public String baseURL = readconfig.getUrl();
    public static WebDriver driver;
    public static Logger logger;
    @Parameters({"browser"})
    @BeforeClass
    public void setup(@Optional("chrome") String br) {
        // Initialize the logger
        logger = Logger.getLogger("OrangeHrmProject");
        PropertyConfigurator.configure("log4j.properties");

        // Setup browser based on parameter
        if (br.equals("chrome")) {
            System.setProperty("WebDriver.chrome.driver", readconfig.getChromePath());
            driver = new ChromeDriver();
        } else if (br.equals("edge")) {
            System.setProperty("WebDriver.edge.driver", readconfig.getEdgePath());
            driver = new EdgeDriver();
        } else if (br.equals("firefox")) {
            System.setProperty("WebDriver.gecko.driver", readconfig.getFirefoxPath());
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    public void captureScreenshot(WebDriver driver, String tname) throws IOException 
    {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(System.getProperty("user.dir") + "//Screenshots//" + tname + ".png");
        FileUtils.copyFile(sourceFile, targetFile);
        System.out.println("Screenshot taken");
    }

    @AfterClass
    public void tearDown() 
    {
        if (driver != null) 
        {
            driver.quit();
        }
    }
}
