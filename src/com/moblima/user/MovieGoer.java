package com.moblima.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieGoer extends User 
{
	
	String username,password;
	Date birthdate;
	int id;
	
	public MovieGoer(String username, String password, int id, String birthdate) throws ParseException
	{
		this.username = username;
		this.password = password;
		this.id = id;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		this.birthdate = formatter.parse(birthdate);
		
		System.out.println("I am a moviegoer");
	}
	
	public void performActions()
	{
		System.out.println("these are the moviegoer actions");
	}

}
