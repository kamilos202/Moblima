package com.moblima.user;

import com.moblima.util.ILogin;

<<<<<<< HEAD
    
=======
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
>>>>>>> 7ff74cf90bc958af96e8c91e0ba070e5cdcd5349
}
