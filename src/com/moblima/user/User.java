package com.moblima.user;

import java.util.Scanner;

import com.moblima.database.DataBaseCommunication;
import com.moblima.util.ILogin;

public class User implements ILogin
{
	private String username;
	private int id;
	private String password;
	
	public User()
	{
	}
	
	public void login()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = sc.next();
		System.out.println("Enter password: ");
		String password = sc.next();
		sc.close();
		if(verifyLogin(username,password))
		{
			this.username = username;
			this.password = password;
			System.out.println("Login Succesful");
		}
	}
	
	
	public boolean verifyLogin(String username,String password) 
	{
		String correctPassword = DataBaseCommunication.retrievePasswordFromDatabase(username);
		if(correctPassword == null)
		{
			System.out.println("User does not exist in system");
			return false;
		}
		else if(correctPassword.equals(password)) return true;
		else 
		{
			System.out.println("Incorrect password");
			return false;
		}
	}
}
