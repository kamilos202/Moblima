package com.moblima.user;

import java.text.ParseException;

public class UserControl 
{
	public static User getUserFromString(String userString)
	{
		String[] userInfo = userString.split(";");
		System.out.println(userInfo[0]);
		if(Boolean.parseBoolean(userInfo[2]))
		{
			return(new Admin(userInfo[0],userInfo[1],Integer.parseInt(userInfo[3])));
		}
		else
		{
			try { //initiated by database,will never fail
				return(new MovieGoer(userInfo[0],userInfo[1],Integer.parseInt(userInfo[3]),userInfo[4]));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
}
