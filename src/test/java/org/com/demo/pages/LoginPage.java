package org.com.demo.pages;

import org.com.demo.util.Test_Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Sample page
 */
public class LoginPage extends Test_Util 
{

	public static void userLogin(String id1,String id2,String id3,String uname,String password)
	{
		Test_Util.clearAndEnterValues(id1, Test_Util.OR, uname, log);
		Test_Util.clearAndEnterValues(id2, Test_Util.OR, password, log);
		Test_Util.clickHere(id3, Test_Util.OR, log);
	}
}
