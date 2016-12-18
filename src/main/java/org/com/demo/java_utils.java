package org.com.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.com.demo.util.Test_Util;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

public class java_utils 
{
	public static Properties prop;
	public static String logfilepath;
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to initialize a Logger object                           *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static Logger getLogger(Class classname)
	{
		final Logger logger = Logger.getLogger(classname);
		getProjectProperties(Test_Util.sPropertiesFilePath);
		logfilepath = prop.getProperty("loggerfilepath");
		System.out.println("The value is-"+logfilepath);
		PropertyConfigurator.configure(logfilepath);
		return logger;
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to fetch current time stamp                             *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static String timeStamp()
	{
		String timeStamp,tempStr,tempStr1;
		Date todaysDate = new Date();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		timeStamp = df.format(todaysDate);
		tempStr = timeStamp.replace(" ", "_");
		tempStr1 = tempStr.replace(":", "");
		timeStamp = tempStr1.replace("-", "_");
		return timeStamp;
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to fetch the properties file                            *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static Properties getProjectProperties(String filePath)
	{
		prop = new Properties();
		try
		{
			FileInputStream input = new FileInputStream(new File(filePath));
			prop.load(input);
			return prop;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to fetch the date                                       *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static String returnDate()
	{
	String date;
	SimpleDateFormat simpledateform = new SimpleDateFormat("dd/MMM/yyyy");
	Date newdate = new Date();
	String expDate = simpledateform.format(newdate);
	date = expDate.replace("/", "");
	return date;
	}
	
	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to fetch the time                                       *
	*Version    : 0.1                                                          * 
	****************************************************************************/

	public static String returnTime()
	{
		String tempStr,timestamp=null;
		Date todaysDate = new Date();
		SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
		tempStr = form.format(todaysDate);
		timestamp = tempStr.replace(":", ".");
		return timestamp;
	}

	/***************************************************************************
	*Author     : Suresh Damodaran                                             *
	*Date       : 18-12-2016                                                   *
	*Description: Code to read from object repository database                 *
	*Version    : 0.1                                                          * 
	****************************************************************************/
	
	/**
	 * -- ex: looping through the result set and providing the map with the locators --
	 * <code>
	 * while(rs.next())
	 *			{
	 *				hmap.put(rs.getString("ID"), rs.getString("LOCATOR_TYPE")+"<>"+rs.getString("LOCATOR_VALUE"));
	 *			}
	 * </code>
	 * 
	 * @param query - select statement and map
	 * @return void
	 */
	public static void GetOR(String sQuery,HashMap<String,String> hmap)throws ClassNotFoundException,SQLException
	{
		try
		{
			String driver  = "net.ucanaccess.jdbc.UcanaccessDriver";
			Class.forName(driver);
			String dblocation = "jdbc:ucanaccess://"+System.getProperty("user.dir")+"\\"+Test_Util.dbPath;
			Connection con = DriverManager.getConnection(dblocation, "", "");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sQuery);
			if(rs!=null)
			{
				while(rs.next())
				{
					hmap.put(rs.getString("ID"), rs.getString("LOCATOR_TYPE")+"<>"+rs.getString("LOCATOR_VALUE"));
				}
			}
			rs.close();
			con.close();

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
