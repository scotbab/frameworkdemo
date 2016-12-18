package org.com.demo.pagevalidation;

import org.com.demo.util.Test_Util;
import org.testng.Assert;

public class AboutPageValidation extends Test_Util
{
	public static final String HomePage_Text = "Dashboard";

	public static void homePageAssert(String id)
	{
		String actualText = Test_Util.getTextFromApp(id, Test_Util.OR, log);
		Assert.assertEquals(actualText, HomePage_Text);
	}
}
