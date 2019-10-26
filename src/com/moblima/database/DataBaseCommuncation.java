package com.moblima.database;

import java.util.List;


public class DataBaseCommuncation implements IDataBase
{
	
	
	public void writeToDataBase(String[] lines)
	{
		
	}
	
	public static String retrievePasswordFromDatabase(String username)
	{
		boolean userExists = false;
		String password = "";
		List<String> users = IDataBase.readFromDataBase("users.txt");
		for(int i = 0;i<users.size();i++)
		{
			if(users.get(i).split)
		}
		return userExists? password : null;
	}
	
}
