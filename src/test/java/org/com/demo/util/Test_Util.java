package org.com.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.com.demo.java_utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Test_Util extends java_utils
{
	protected static WebDriver driver;
	public static String TC_Key,MethodName;
	public static String appName;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static HashMap<String,String> OR = new HashMap<String, String>();
	public static HashMap<String,String> dataHmap = new HashMap<String, String>();
	static File reportConfigFile = new File(System.getProperty("user.dir")+"//extent.config.xml");
	public static String sDataPath,sPrintMsg,dbPath,chromeDriverPath,IEDriverPath,FirefoxPath,URL,browsername;
	public static int iObjExistsFalse=0,bValidation;
	public static String sPropertiesFilePath = System.getProperty("user.dir")+"//src//test//resources//config.properties";
	public static Logger log;
	
	/***************************************************************
	*Author     : Suresh Damodaran                                 *
	*Date       : 18-12-2016                                       *
	*Description: Initializes the property and the report objects  *
	*Version    : 0.1                                              * 
	****************************************************************/
	@BeforeSuite
	public static void startMethod()
	{
		getProjectProperties(sPropertiesFilePath);
		chromeDriverPath = prop.getProperty("chromeDriverPath");
		IEDriverPath = prop.getProperty("IEDriverPath");
		FirefoxPath = prop.getProperty("FirefoxPath");
		dbPath = prop.getProperty("dbPath");
		URL = prop.getProperty("url");
		sDataPath = prop.getProperty("dataPath");
		browsername = prop.getProperty("browsername");
		String report = System.getProperty("user.dir")+"\\output\\htmltemplate"+Test_Util.timeStamp()+".html";
		Test_Util.startPrepareReport(report);
		Test_Util.appName = "DemoApp";
	}
	
	/***************************************************************
	*Author     : Suresh Damodaran                                 *
	*Date       : 18-12-2016                                       *
	*Description: Cleans the webdriver objects as well the report  *
	*Version    : 0.1                                              * 
	****************************************************************/

	@AfterSuite
	public static void tearDown()
	{
		driver.quit();
		Test_Util.finishPrepareReport(Test_Util.test);
	}
	
	/***************************************************************
	*Author     : Suresh Damodaran                                 *
	*Date       : 18-12-2016                                       *
	*Description: Close and flushes the extent report object       *
	*Version    : 0.1                                              * 
	****************************************************************/

	public static void finishPrepareReport(ExtentTest test)
	{
		extent.flush();
		extent.close();
	}
	
	/***************************************************************
	*Author     : Suresh Damodaran                                 *
	*Date       : 18-12-2016                                       *
	*Description: Initializes the extent report objects            *
	*Version    : 0.1                                              * 
	****************************************************************/

	public static void startPrepareReport(String reportPath)
	{
		extent = new ExtentReports(reportPath,false);
		Map<String,String> sysInfo = new HashMap<String,String>();
		sysInfo.put("Selenium version", "3.0.1");
		sysInfo.put("Environment", "SIT");
		extent.addSystemInfo(sysInfo);
	}
	
	/***************************************************************
	*Author     : Suresh Damodaran                                 *
	*Date       : 18-12-2016                                       *
	*Description: Initializes the browser objects                  *
	*Version    : 0.1                                              * 
	****************************************************************/

	public static WebDriver startApplication()
	{
		if(browsername.equalsIgnoreCase("firefox"))	
		{
			System.setProperty("webdriver.gecko.driver", FirefoxPath);
			driver = new FirefoxDriver();
		}
		else if(browsername.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			driver = new ChromeDriver(options);
		}
		else if(browsername.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver", IEDriverPath);
			driver = new InternetExplorerDriver();
		}
		driver.get(URL);
		driver.manage().window().maximize();
		return driver;
	}
	
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: create the report file if not exists and takes a screenshot  *
	*Version    : 0.1                                                          * 
	****************************************************************************/

		public static void captureScreenshotwithReport(String MethodName,String sStatus,ExtentTest test)
	{
		File theDir = new File(System.getProperty("user.dir")+"//output//screenshots//"+Test_Util.TC_Key+"//"+returnDate());
		if(!theDir.exists())
		{
			theDir.mkdir();
		}
		try
		{
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			if(sStatus.equalsIgnoreCase("PASS"))
			{
				String path = theDir+"\\"+Test_Util.TC_Key+"_"+returnTime()+"\\"+"Pass_"+MethodName+timeStamp()+".png";
				FileUtils.copyFile(srcFile, new File(path));
				generateHTMlReport("pass", null, test, Test_Util.MethodName, "");
			}
			else if(sStatus.equalsIgnoreCase("FAIL"))
			{
				String path = theDir+"\\"+Test_Util.TC_Key+"_"+returnTime()+"\\"+"Fail"+MethodName+timeStamp()+".png";
				FileUtils.copyFile(srcFile, new File(path));
				generateHTMlReport("fail", new File(path), test, Test_Util.MethodName, "");
			}
			else if(sStatus.equalsIgnoreCase("SKIP"))
			{
				String path = theDir+"\\"+Test_Util.TC_Key+"_"+returnTime()+"\\"+"Skip_"+MethodName+timeStamp()+".png";
				FileUtils.copyFile(srcFile, new File(path));
				generateHTMlReport("skip", new File(path), test, Test_Util.MethodName, "");
			}
			Reporter.log("Screenshot taken and pasted");
		}
		catch(Exception e)
		{
			Reporter.log("Error occurred while pasting the screenshot");
		}
	}

		/***************************************************************************
		*Author     : Suresh Damodaran                                             *
		*Date       : 18-12-2016                                                   *
		*Description: Integrate new data to existing report                        *
		*Version    : 0.1                                                          * 
		****************************************************************************/
	public static ExtentTest integrateToReport(String functionality)
	{
		ExtentTest test = extent.startTest(functionality);
		test.assignCategory(Test_Util.appName+"_Regression");
		extent.loadConfig(reportConfigFile);
		return test;
	}

	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Attach screenshot to existing extent report                  *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static void generateHTMlReport(String status,File snapshot,ExtentTest test,String step,String step_descr)
	{
		if(status.equalsIgnoreCase("pass"))
		{
			test.log(LogStatus.PASS, step,step_descr);
		}
		else if(status.equalsIgnoreCase("fail"))
		{
			test.log(LogStatus.FAIL, step, "snapshot: "+test.addScreenCapture(snapshot.getAbsolutePath()));
		}
		else if(status.equalsIgnoreCase("skip"))
		{
			test.log(LogStatus.SKIP, step,"snapshot: "+test.addScreenCapture(snapshot.getAbsolutePath()));
		}
	}
	
	/*****************************************************************************************
	*Author     : Suresh Damodaran                                                           *
	*Date       : 18-12-2016                                                                 *
	*Description: Integrate new data to existing report when testcase count greater than one *
	*Version    : 0.1                                                                        * 
	******************************************************************************************/

	public static ExtentTest GenerateReportForTest(String testcasename,String stepName,String status,int count,ExtentTest logger)
	{
		if(count>1)
		{
			Test_Util.captureScreenshotwithReport(stepName, status, logger);
		}
		else
		{
			logger = Test_Util.integrateToReport(testcasename);
			Test_Util.captureScreenshotwithReport(stepName, status, logger);
		}
		return logger;
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to end the extent test                                  *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static void endTest(ExtentTest test)
	{
		extent.endTest(test);
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Logging information to log to the extent report              *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	@AfterMethod
	public void afterMethod(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			test = Test_Util.GenerateReportForTest(Test_Util.TC_Key, Test_Util.MethodName, "fail", 1, Test_Util.test);
			Test_Util.endTest(test);
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			test = Test_Util.GenerateReportForTest(Test_Util.TC_Key, Test_Util.MethodName, "pass", 1, Test_Util.test);
			Test_Util.endTest(test);
		} 
		else if(result.getStatus()==ITestResult.SKIP)
		{
		test = Test_Util.GenerateReportForTest(Test_Util.TC_Key, Test_Util.MethodName, "skip", 1, Test_Util.test);
		Test_Util.endTest(test);
		}
		//driver.close();
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: fetch the existing browser object                            *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static WebDriver getBrowser()
	{
		return driver;
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: code to fetch the web element                                *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static WebElement getElement(String ObjectID,HashMap<String,String> hMap,Logger log)
	{
		WebElement element=null;
		String e1 = hMap.get(ObjectID);
		String []e1Array = e1.split("<>");
		String LocatorType = e1Array[0];
		String LocatorValue = e1Array[1];
		
		try
		{
			if(LocatorType.equalsIgnoreCase("xpath"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("id"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("name"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("linktext"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("partiallinktext"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("css"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LocatorValue)));
			}
			log.info("The web element located successfully in "+driver.getTitle());
			return element;
		}
		catch(Exception e)
		{
			log.error("The web element is not located successfully in "+driver.getTitle());
			return null;
		}
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: code to clear and enter values to a text field               *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static void clearAndEnterValues(String ObjectID,HashMap<String,String> hMap,String value,Logger log)
	{
		getElement(ObjectID, hMap,log).clear();
		getElement(ObjectID, hMap,log).sendKeys(value);
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to click a web element                                  *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static void clickHere(String ObjectID,HashMap<String,String> hMap,Logger log)
	{
		getElement(ObjectID, hMap,log).click();
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to fetch a text from the web application                *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static String getTextFromApp(String ObjectID,HashMap<String,String> hMap,Logger log)
	{
		String actText = getElement(ObjectID, hMap,log).getText().trim();
		return actText;
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to check if a web element exists or not                 *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static boolean elementExists(String ObjectID,HashMap<String,String> hMap,Logger log)
	{
		int elementSize=0;
		String e1 = hMap.get(ObjectID);
		String []e1Array = e1.split("<>");
		String LocatorType = e1Array[0];
		String LocatorValue = e1Array[1];
		try
		{
			if(LocatorType.equalsIgnoreCase("xpath"))
			{
				elementSize = driver.findElements(By.xpath(LocatorValue)).size();
			}
			else if(LocatorType.equalsIgnoreCase("id"))
			{
				elementSize = driver.findElements(By.id(LocatorValue)).size();
			}
			else if(LocatorType.equalsIgnoreCase("name"))
			{
				elementSize = driver.findElements(By.name(LocatorValue)).size();
			}
			else if(LocatorType.equalsIgnoreCase("linktext"))
			{
				elementSize = driver.findElements(By.linkText(LocatorValue)).size();
			}
			else if(LocatorType.equalsIgnoreCase("partiallinktext"))
			{
				elementSize = driver.findElements(By.partialLinkText(LocatorValue)).size();
			}
			else if(LocatorType.equalsIgnoreCase("css"))
			{
				elementSize = driver.findElements(By.cssSelector(LocatorValue)).size();
			}
			if(elementSize!=0)
			{
				WebElement element = getElement(ObjectID, hMap,log);
				if(element.isDisplayed())
				{
					log.info("The element is displyed successfully in "+driver.getTitle());
					return true;
				}
				else
				{
					log.error("The element is not displyed successfully in "+driver.getTitle());
					return false;
				}
			}
			else
			{
				log.error("The element is not displyed successfully in "+driver.getTitle());
				return false;
			}
		}
		catch(Exception e)
		{
			return false;			
		}
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to fetch a list of web elements                         *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static List<WebElement> getElements(String ObjectID,HashMap<String,String> hMap,Logger log)
	{
		List<WebElement> element = null;
		String e1 = hMap.get(ObjectID);
		String []e1Array = e1.split("<>");
		String LocatorType = e1Array[0];
		String LocatorValue = e1Array[1];
		try
		{
			if(LocatorType.equalsIgnoreCase("xpath"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("id"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("name"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("linktext"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("partiallinktext"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.partialLinkText(LocatorValue)));
			}
			else if(LocatorType.equalsIgnoreCase("css"))
			{
				element = new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(LocatorValue)));
			}
			log.info("Success in getting the webelements list from "+driver.getTitle());
			return element;		
		}
		catch(Exception e)
		{
			log.error("Failure in getting the webelements list from "+driver.getTitle());
			return null;			
		}
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to clear browser cookies                                *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static void clearCookies(Logger log)
	{
		try
		{
			driver.manage().deleteAllCookies();
			log.info("Cleared all cookies successfully");
		}
		catch(Exception e)
		{
			log.error("Failure in clearing the cookies");
		}
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to perform browser actions                              *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static boolean browserFunctions(String bf)
	{
		JavascriptExecutor jse;
		if(bf.equalsIgnoreCase("REFRESH"))
		{
			try
			{
				driver.navigate().refresh();
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else if(bf.equalsIgnoreCase("GOBACK"))
		{
			try
			{
				driver.navigate().back();
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else if(bf.equalsIgnoreCase("GOFORWARD"))
		{
			try
			{
				driver.navigate().forward();
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else if(bf.equalsIgnoreCase("SCROLLDOWN"))
		{
			try
			{
				jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,300)", "");
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else if(bf.equalsIgnoreCase("SCROLLUP"))
		{
			try
			{
				jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,-300)", "");
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else if(bf.equalsIgnoreCase("SCROLLRIGHT"))
		{
			try
			{
				jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(300,0)", "");
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else if(bf.equalsIgnoreCase("SCROLLLeft"))
		{
			try
			{
				jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(-300,0)", "");
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			log.info("Please provide a suitable browser function");
			return false;
		}
	}
}
