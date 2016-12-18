package org.com.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.apache.log4j.Logger;
import org.com.demo.pages.LoginPage;
import org.com.demo.pagevalidation.HomePageValidation;
import org.com.demo.util.Test_Util;

public class DemoQaAppTest extends Test_Util
{
	@BeforeClass
	public static void startApp() throws Exception
	{
		log = java_utils.getLogger(DemoQaAppTest.class);
		String or_query = "SELECT * FROM Object_Repository";
		java_utils.GetOR(or_query,OR);
		Test_Util.startApplication();
		Test_Util.clearCookies(log);
	}

  @Test
  public void testHomePage() 
  {
	  Test_Util.MethodName="HomePageValidation";
	  Test_Util.TC_Key="TC001";
	  HomePageValidation.homePageAssert("2");
  }
  
  @Test(dependsOnMethods="testHomePage",alwaysRun=true)
  public void testHomeAboutUs() 
  {
	  Test_Util.MethodName="HomePageAboutUsValidation";
	  Test_Util.TC_Key="TC002";
	  Test_Util.clickHere("1", Test_Util.OR, log);
	  HomePageValidation.AboutUsPageAssert("1");
  }
  
  @Test(dependsOnMethods="testHomeAboutUs",alwaysRun=true)
  public void testHomeServices() 
  {
	  Test_Util.MethodName="HomePageServicesValidation";
	  Test_Util.TC_Key="TC003";
	  Test_Util.clickHere("3", Test_Util.OR, log);
	  HomePageValidation.ServicesPageAssert("3");
  }
  
  @Test(dependsOnMethods="testHomeServices",alwaysRun=true)
  public void testHomeDemo() 
  {
	  Test_Util.MethodName="HomePageDemoValidation";
	  Test_Util.TC_Key="TC004";
	  Test_Util.clickHere("4", Test_Util.OR, log);
	  HomePageValidation.DemoPageAssert("4");
  }
  
  @Test(dependsOnMethods="testHomeDemo")
  public void testHomeTabs()
  {
	  Test_Util.MethodName="HomePageTabValidation";
	  Test_Util.TC_Key="TC005";
	  Test_Util.clickHere("14", Test_Util.OR, log);
	  //Test_Util.browserFunctions("SCROLLDOWN");
	  Test_Util.clickHere("6", Test_Util.OR, log);
	  HomePageValidation.Tab2Assert("10");
	  
	  Test_Util.clickHere("7", Test_Util.OR, log);
	  HomePageValidation.Tab3Assert("11");
	  
	  Test_Util.clickHere("8", Test_Util.OR, log);
	  HomePageValidation.Tab4Assert("12");
	  
	  Test_Util.clickHere("9", Test_Util.OR, log);
	  HomePageValidation.Tab5Assert("13");
  }

}
