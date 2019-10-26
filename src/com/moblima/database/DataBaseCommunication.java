package com.moblima.database;

import java.util.List;


public class DataBaseCommunication implements IDataBase
{
	
	
	public void writeToDataBase(String[] lines)
	{
		
	}
	public static List<String> getInformation(String path)
	{
		return IDataBase.readFromDataBase(path);
	}
	
	public static String retrievePasswordFromDatabase(String username)
	{
		boolean userExists = false;
		String password = "";
		List<String> users = IDataBase.readFromDataBase("users.txt");
		for(int i = 0;i<users.size();i++)
		{
			System.out.println(users.get(i).split(";")[0]);
			if(users.get(i).split(";")[0].equals(username))
			{
				password = users.get(i).split(";")[1];
				userExists = true;
				break;
			}
		}
		return userExists? password : null;
	}
	
}
