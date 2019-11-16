package com.moblima.user;

import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.text.ParseException;

import com.moblima.database.DataBase;
import com.moblima.util.ILogin;

public class UserControl implements ILogin
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
	
	private static String getUserInfoFromDataBase(String username)
	{
		List<String> users = DataBase.readFile("users.txt");
		for(int i =0;i<users.size();i++)
		{
			if(users.get(i).split(";")[0].equals(username)) return users.get(i);
		}
		return null;
	}
	
	public static void makeUserAdmin(String username)
	{
		String movieGoerInfo = getUserInfoFromDataBase(username);
		String[] userInfo = movieGoerInfo.split(";");
		DataBase.users.remove(getUserByName(username));
		userInfo[2] = "true";
		String newInfo = "";
		for(int i = 0;i<userInfo.length;i++)
		{
			newInfo += userInfo[i];
			if(i!=userInfo.length-1)
			{
				newInfo += ";";
			}
		}
		DataBase.replaceInDataBase(movieGoerInfo, newInfo, "users.txt");
		DataBase.users.add(getUserFromString(newInfo));
	}
	
	
	public static User getUserByName(String name)
	{
		for(int i =0;i<DataBase.users.size();i++)
		{
			if(DataBase.users.get(i).getUsername().equals(name))
			{
				return DataBase.users.get(i);
			}
		}
		return null;
	}
	
	public static boolean doesUserExist(String name)
	{
		return getUserByName(name) == null ? false : true;
	}
	
	public static void registerUser(String username, String password, Date date)
	{
		int id = DataBase.users.size()+1;
		@SuppressWarnings("deprecation")
		String birthdate = date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900);
		String[] info = {"\n"+username+";"+password+";"+"false"+";"+id+";"+birthdate};
		try {
			DataBase.appendToDataBase(info, "users.txt");
			DataBase.users.add(new MovieGoer(username,password,id,birthdate));
		} catch (Exception e) {
			System.out.println("Registration failed, please contact the product supplier.");
		}
		
	}
	
	public static User login(String username, String password)
	{
		if(getUserInfoFromDataBase(username).split(";")[1].equals(password))
		{
			System.out.println("Login succesfull");
			return getUserByName(username);
		}
		else
		{
			System.out.println("Incorrect password");
			return null;
		}
	}
}
