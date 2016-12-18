package org.com.demo.pagevalidation;

import org.com.demo.util.Test_Util;
import org.testng.Assert;

public class HomePageValidation extends Test_Util
{
	public static final String HomePage_Text = "Home";
	public static final String AboutUs_Text = "About us";
	public static final String Services_Text = "Services";
	public static final String Demo_Text = "Demo";
	public static final String Tab2_Text = "Content 2 Title";
	public static final String Tab3_Text = "Content 3 Title";
	public static final String Tab4_Text = "Content 4 Title";
	public static final String Tab5_Text = "Content 5 Title";

	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Home page assertion                                          *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	public static void homePageAssert(String id)
	{
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, HomePage_Text);
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Home AboutUS page assertion                                  *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	public static void AboutUsPageAssert(String id)
	{
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, AboutUs_Text);
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Home Service page assertion                                  *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	public static void ServicesPageAssert(String id)
	{
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, Services_Text);
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Home Demo page assertion                                     *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	public static void DemoPageAssert(String id)
	{
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, Demo_Text);
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Home page Tab2 assertion                                     *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	public static void Tab2Assert(String id)
	{
		
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, Tab2_Text);
	}
	
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Home page Tab3 assertion                                     *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	public static void Tab3Assert(String id)
	{
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, Tab3_Text);
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Home page Tab4 assertion                                     *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	public static void Tab4Assert(String id)
	{
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, Tab4_Text);
	}
	
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Home page Tab5 assertion                                     *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	public static void Tab5Assert(String id)
	{
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, Tab5_Text);
	}

}
