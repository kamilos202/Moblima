package com.moblima.user;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 */
public class MovieGoer extends User {

	Date birthdate;
	MovieGoer current;
	
	UserBoundary boundary;

	/**
	 * 
	 * @param username
	 * @param password
	 * @param id
	 * @param birthdate
	 * @throws ParseException
	 */
	public MovieGoer(String username, String password, int id, String birthdate) throws ParseException {
		super(username,password,id);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		this.birthdate = formatter.parse(birthdate);
		boundary = new MovieGoerBoundary(this);
		System.out.println("I am a moviegoer");
	}

	@Override
	public UserBoundary getBoundary()
	{
		return boundary;
	}
	/**
	 * 
	 */
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public Date getBirthdate()
	{
		return this.birthdate;
	}



}
