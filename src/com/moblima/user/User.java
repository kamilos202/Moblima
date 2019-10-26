package com.moblima.user;

import com.moblima.util.ILogin;

public class User implements ILogin
{
	private String username;
	private int id;
	private String password;
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public boolean verifyLogin() 
	{
		return false;
	}
}
