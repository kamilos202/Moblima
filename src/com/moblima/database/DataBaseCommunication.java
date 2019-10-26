package com.moblima.database;

import java.util.ArrayList;
import java.util.List;


public class DataBaseCommunication implements IDataBase
{
	
	
	public void writeToDataBase(String[] lines)
	{
		;
	}
	
	public static String retrievePasswordFromDatabase(String username)
	{
		boolean userExists = false;
		String password = "";
		List<String> users = IDataBase.readFromDataBase("users.txt");
		for(int i = 0;i<users.size();i++)
		{
			if(users.get(i).split(";")[0]==username)
			{
				password = users.get(i).split(";")[1];
				break;
			}
		}
		return userExists? password : null;
	}
	public static List<String> getMovies(){
		return IDataBase.readFromDataBase("movies.txt");
	}
	
}
