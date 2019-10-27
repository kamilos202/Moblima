package com.moblima.database;

import java.io.IOException;
import java.util.List;

//This class contains all the communication between other classes and the database
public class DataBaseCommunication implements IDataBase
{
	
	//Called by other Classes to write to data base
	public static void writeToDataBase(String[] lines, String path)
	{
		try {IDataBase.writeToDataBase(lines, path);} 
		catch (IOException e) {	e.printStackTrace();}
	}
	
	//Called by other classes to append to database
	public static void appendToDataBase(String[] lines, String path) throws IOException
	{
		IDataBase.appendToDataBase(lines, path);
	}
	
	//Called by other classes to read in data from database
	public static List<String> readFile(String path)
	{
		return IDataBase.readFromDataBase(path);
	}
	
	//Called by user to get the data from 1 specific user
	public static String getUserDetails(String username)
	{
		List<String> users = readFile("users.txt");
		for(int i = 0;i<users.size();i++) 
		{
			if(users.get(i).split(";")[0].equals(username)) return users.get(i);
		}
		
		return null;
	}
	
	//Called by User to retrieve the actual users password to verify login 
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
