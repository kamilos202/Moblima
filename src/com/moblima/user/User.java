package com.moblima.user;
import java.util.Date;
import java.util.Scanner;
/**
 * User class is the super class for MovieGoer class and Admin
 */
public class User
{
	protected String username, password;
	protected int id;
	static Scanner sc = new Scanner(System.in);
	/**
	 * This is the contructor for the user.
	 * @param username name of the user 
	 * @param password password of the account
	 * @param id unique number of the user (movie goer or admin)
	 */
	public User(String username, String password, int id)
	{
		this.username = username;
		this.password = password;
		this.id = id;
	}
	
	public Date getBirthdate(){return null;}
	public String getUsername() {return username;}
	public UserBoundary getBoundary()
	{
		return new UserBoundary();
	}
	/**
	 * Returns the String of the user's details
	 */
	public String toString()
	{
		return this.username+","+this.password+","+this.id;
	}
}
