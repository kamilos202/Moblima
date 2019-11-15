package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import com.moblima.database.DataBase;
import com.moblima.util.ILogin;
import com.moblima.util.UserInputs;

/**
 * 
 */
public class User implements ILogin
{
	protected String username, password;
	protected int id;
	static Scanner sc = new Scanner(System.in);
	
	public User(String username, String password, int id)
	{
		this.username = username;
		this.password = password;
		this.id = id;
	}
	
	public String getUsername() {return username;}
	
	public UserBoundary getBoundary()
	{
		return new UserBoundary();
	}
	
	
	public String toString()
	{
		return this.username+","+this.password+","+this.id;
	}
}
