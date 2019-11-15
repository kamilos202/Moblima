package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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
	
	
	//overwritten by both admin and moviegoer to distinguish between their performable actions
	public void performActions() throws IOException, ParseException{}
	
	
	
	/**
	 *Create a new set of info in the data base, which then can be used to initiate a new instance of user during login 
	 * @throws IOException
	 */
	public static void registerUser() throws IOException
	{
		//Input user details
		System.out.println("Choose a username: ");
		String username = UserInputs.getValidStringInput();
		System.out.println("Choose a password of 8 characters or more: ");
		String password = UserInputs.getValidStringInput();
		System.out.println("Please enter your birthday (dd): ");
		int day = UserInputs.getValidIntegerInput();
		System.out.println("Please enter your birthday (mm): ");
		int month = UserInputs.getValidIntegerInput();
		System.out.println("Please enter your birthday (yyyy): ");
		int year = UserInputs.getValidIntegerInput();
		
		//Calculate user id of new user based on already existing users
		List<String> currentUsers = DataBase.readFile("users.txt");
		int id = currentUsers.size();
		
		//format birthday and all other details together
		String birthdate = day+"/"+month+"/"+year;
		String[] details = {"\n"+username+";"+password+";"+"false"+";"+id+";"+birthdate};
		
		//check for valid inputs:
		//Username is not taken
		//Password is atleast 8 characters
		//Birthdate is a valid date
		boolean[] valid = {isUsernameValid(currentUsers,username),isPasswordValid(password),UserInputs.isDateValid(birthdate)};
		if(!valid[0])
		{
			System.out.println("Registration failed: ");
			System.out.println("Username already taken");
		}
		else if(!valid[1])
		{
			System.out.println("Registration failed: ");
			System.out.println("Choose a password of atleast 8 characters please");
		}
		else if(!valid[2])
		{
			System.out.println("Registration failed: ");
			System.out.println("Please enter a valid birthdate in the format dd/mm/yyyy");
		}
		//if all inputs are valid the information is saved to database
		else
		{
			DataBase.appendToDataBase(details, "users.txt");
			try {
				DataBase.users.add(new MovieGoer(username,password,id,birthdate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Registration succesful!");
		}
		
	}
	
	public Date getBirthDate(){
		return null;
	}
	
	private static boolean isUsernameValid(List<String> users,String username)
	{
		for(int i = 0;i<users.size();i++)
		{
			if(users.get(i).split(";")[0].equals(username)) return false;
		}
		return true;
	}
	
	private static boolean isPasswordValid(String password)
	{
		return password.length() >= 8 ? true : false;
	}
	
	
	
	public static User getUserByName(String name)
	{
		for(int i =0;i<DataBase.users.size();i++)
		{
			System.out.println(DataBase.users.get(i).getUsername());
			if(DataBase.users.get(i).getUsername().equals(name)) return DataBase.users.get(i);
		}
		System.out.println("failed to retrieve user: " + name);
		return null;
	}
	
	
	//Creates a new instance of either Admin or Moviegoer, depending on the login details supplied by extracting data from the database
	public static User getUser() throws NumberFormatException, ParseException
	{
		//Ask user for login details
		System.out.println("Enter username: ");
		String username = UserInputs.getValidStringInput();
		System.out.println("Enter password: ");
		String password = UserInputs.getValidStringInput();
		
		//Try to login using details provided
		String userDetailsRaw = getLoginDetails(username,password);
		if(userDetailsRaw == null) return null; //Login failed, either user does not exist or wrong password is entered
		else
		{
			//format the data from database back to usable format
			//String[] userDetails = userDetailsRaw.split(";");
			
			//UserDetails[2] contains either true (user is an admin) or false (user is a moviegoer)
			return getUserByName(username);
			//if(Boolean.parseBoolean(userDetails[2])) return new Admin(username,password,Integer.parseInt(userDetails[3]));
			//else return new MovieGoer(username,password,Integer.parseInt(userDetails[3]),userDetails[4]);
		}
		
	}
	
	//Try to log in, if succesfull provide userinformation back to the method that called this.
	private static String getLoginDetails(String username, String password)
	{
		
		if(verifyLogin(username,password))
		{
			return DataBase.getUserDetails(username);
		}
		else 
		{
			return null; //login failed --> no user information can be provided
		}
		
	}
	
	private static boolean verifyLogin(String username,String password) 
	{
		String correctPassword = DataBase.retrievePasswordFromDatabase(username); //Search for the users password
		if(correctPassword == null)
		{
			System.out.println("User does not exist in system");
			return false;
		}
		//Compare password with the user's input.
		else if(correctPassword.equals(password)) return true;
		else 
		{
			System.out.println("Incorrect password");
			return false;
		}
	}
	
	public String toString()
	{
		return this.username+","+this.password+","+this.id;
	}
}
